package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false"
			+ "&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private String user = "student";
	private String pass = "student";

	static {
		String packageName = "com.mysql.cj.jdbc";
		String className = "Driver";

		try {
			Class.forName(packageName + "." + className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(className + " not found.");
			throw new RuntimeException("Unable to load MYSQL " + className + " class");
		}
	}

	public int[] getMinMaxFilmIds() {
		int min = 0;
		int max = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet filmResult = null;

		try {
			conn = doConnection();
			String sql = "SELECT MIN(id), MAX(id) FROM film;";
			stmt = conn.prepareStatement(sql);
			filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				min = filmResult.getInt(1);
				max = filmResult.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(this.getClass().getSimpleName() + " getMinMaxFilmIds() error1");
		} finally {
			try {
				filmResult.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(this.getClass().getSimpleName() + " getMinMaxFilmIds()  error2");
			}
		}

		return new int[] { min, max };
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet filmResult = null;

		try {
			conn = doConnection();
			String sql = "SELECT f.id, " 		// 1
					+ "f.title, " 			// 2
					+ "f.description, " 		// 3
					+ "f.release_year, " 		// 4
					+ "f.language_id, " 		// 5
					+ "f.rental_duration, " 	// 6
					+ "f.rental_rate, "		// 7
					+ "f.length, " 			// 8
					+ "f.replacement_cost, " 	// 9
					+ "f.rating, " 			// 10
					+ "f.special_features, " 	// 11
					+ "l.name "				// 12
					+ "FROM film f"
					+ " JOIN language l ON f.language_id = l.id"
					+ " WHERE f.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				film = new Film();
				film.setId(filmResult.getInt(1));
				film.setTitle(filmResult.getString(2));
				film.setDescription(filmResult.getString(3));
				film.setReleaseYear(filmResult.getString(4));
				film.setLanguageId(filmResult.getInt(5));
				film.setRentalDuration(filmResult.getInt(6));
				film.setRentalRate(filmResult.getDouble(7));
				film.setLength(filmResult.getInt(8));
				film.setReplacementCost(filmResult.getDouble(9));
				film.setRating(filmResult.getString(10));
				film.setSpecialFeatures(filmResult.getString(11));
				film.setLanguage(filmResult.getString(12));
				film.setActors(findActorsByFilmId(filmId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(this.getClass().getSimpleName() + " findFilmById(int) error1");
		} finally {
			try {
				filmResult.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(this.getClass().getSimpleName() + " findFilmById(int) error2");
			}
		}

		return film;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> list = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet filmResult = null;
		try {
			list = new ArrayList<Film>();
			conn = doConnection();
			String regex = "'%" + keyword + "%'";
			String sql = "SELECT id FROM film WHERE title LIKE " + regex + " OR description LIKE " + regex + ";";
			stmt = conn.prepareStatement(sql);
			filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				list.add(findFilmById(filmResult.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(this.getClass().getSimpleName() + " findFilmByKeyword(String) error1");
		} finally {
			try {
				filmResult.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(this.getClass().getSimpleName() + " findFilmByKeyword(String) error2");
			}
		}

		return list;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet actorResult = null;
		try {
			conn = doConnection();
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(actorResult.getInt(1));
				actor.setFirstName(actorResult.getString(2));
				actor.setLastName(actorResult.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(this.getClass().getSimpleName() + " findActorById(int) error1");
		} finally {
			try {
				actorResult.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(this.getClass().getSimpleName() + " findActorById(int) error2");
			}
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> list = new ArrayList<Actor>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet filmResult = null;

		try {
			conn = doConnection();
			String sql = "SELECT a.id FROM actor a" + " JOIN film_actor fa ON a.id = fa.actor_id"
					+ " JOIN film f ON f.id = fa.film_id" + " WHERE f.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				list.add(findActorById(filmResult.getInt(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(this.getClass().getSimpleName() + " findActorsByFilmId(int) error1");
		} finally {
			try {
				filmResult.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(this.getClass().getSimpleName() + " findActorsByFilmId(int) error2");
			}
		}

		return list;
	}

	@Override
	public Connection doConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(URL, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(this.getClass().getSimpleName() + " doConnection() error");
		}

		// output just in case, if this happens, something is wrong
		if (conn == null) {
			System.err.println("doConnection() error: returning conn = null");
		}
		return conn;
	}

}
