package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		app.test();
		app.launch();
	}

	private void test() throws SQLException {
//		Film film = db.findFilmById(1);
//		System.out.println(film);
//		Actor actor = db.findActorById(1);
//		System.out.println(actor);
//		System.out.println(db.findActorsByFilmId(1));
		System.out.println(db.getMinMaxFilmIds());

	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		startUserInterface(input);
		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("-------------------------------------");
		System.out.println("--- Welcome to Film Query Project ---");
		System.out.println("-------------------------------------");
		System.out.println("----- MAIN MENU -----");
		System.out.println("--- Look up a film by...");
		System.out.println("--- 1. ID");
		System.out.println("--- 2. Search Keyword");
		System.out.println("--- 3. Exit");
		int choice = getIntWithinRange(input, 1, 3);
		switch (choice) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			exit();
		}
	}
	
	private Film getFilmById(Scanner input) {
		Film film = null;
		
		System.out.println("----- FIND FILM BY ID -----");
		int choice = getIntWithinRange(input, 1, 3);
		
		
		return film;
	}

	private int getIntWithinRange(Scanner input, int min, int max) {
		int output = max + 1;
		while (output > max || output < min) {
			System.out.print("--- Your choice: ");
			String str = input.next();

			try {
				output = Integer.parseInt(str);
			} catch (NumberFormatException e) {
			}

			if (output > max || output < min) {
				System.out.println("INVALID CHOICE. MUST BE BETWEEN " + min + "-" + max);
			}
		}
		return output;
	}

	private void exit() {
		System.out.println("--- GOODBYE ---");
	}

}
