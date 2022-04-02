package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Category;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

public interface DatabaseAccessor {
	public Connection doConnection();
	public Film findFilmById(int filmId);
	public int[] getMinMaxFilmIds();
	public Actor findActorById(int actorId);
	public List<Actor> findActorsByFilmId(int filmId);
	public List<Film> findFilmByKeyword(String keyword);
	public List<Category> findCategoriesByFilmId(int filmId);
	public List<InventoryItem> findInventoryItemsByFilmId(int filmId);
}
