package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
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
//		System.out.println(db.getMinMaxFilmIds());
//		System.out.println(db.findFilmByKeyword("A"));
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		startUserInterface(input);
		input.close();
	}
	
	public void startUserInterface(Scanner input) {
		doMainMenu(input);
	}

	private void doMainMenu(Scanner input) {
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
			getFilmById(input);
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
		int[] range = db.getMinMaxFilmIds();
		int choice = getIntWithinRange(input, range[0], range[1]);
		film = db.findFilmById(choice);
		
		//no match found
		if (film == null) {
			System.out.println("--- No film found with ID: " + choice + " ---");
			System.out.println("--- Try again? ---");
			System.out.println("--- 1. Yes");
			System.out.println("--- 2. No, go back to main menu");
			choice = getIntWithinRange(input, 1, 2);
			if (choice == 1) {
				getFilmById(input);
			} else {
				doMainMenu(input);
			}
		}
		
		printFilmDetails(film.getShortDetails());
		
		return film;
	}
	
	private void printFilmDetails(List<String> list) {
		for (String str : list) {
			System.out.println(str);
		}
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
