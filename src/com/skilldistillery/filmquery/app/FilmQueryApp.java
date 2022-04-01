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
			getFilmsByKeyword(input);
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
	
	private List<Film> getFilmsByKeyword(Scanner input) {
		
		System.out.println("----- FIND FILM BY KEYWORD -----");
		System.out.println("----- Will search in film's title or description -----");
		System.out.print("Keyword: ");
		String keyword = input.next();
		keyword += input.nextLine();
		keyword = keyword.trim();
		List<Film> list = db.findFilmByKeyword(keyword);
		
		//no matches found
		if (list.size() == 0) {
			System.out.println("--- No films found.  Try again?");
			System.out.println("--- 1. Yes");
			System.out.println("--- 2. No");
			int choice = getIntWithinRange(input, 1, 2);
			if (choice == 1) {
				getFilmsByKeyword(input);
			} else {
				doMainMenu(input);
			}
		}
		//matches found, print short details
		//if too many results, trim result size
		if (list.size() > SETTINGS.MAX_RESULTS) {
			System.out.println("--- " + list.size() + " films found. Can only show " + SETTINGS.MAX_RESULTS + " at a time.");
			System.out.println("--- Choose up to " + SETTINGS.MAX_RESULTS 
					+ " films starting at result # (1-" + list.size() + ")");
			int choice = getIntWithinRange(input, 1, list.size());
			choice -= 1;
			if (choice + SETTINGS.MAX_RESULTS > list.size() - 1) {
				list = list.subList(choice - 1, list.size() - 1);
			} else {
				list = list.subList(choice,  choice + SETTINGS.MAX_RESULTS);
			}
		}
		//display each film (up to SETTINGS.MAX_RESULTS)
		for (int i = 0; i < list.size(); i++) {
			Film film = list.get(i);
			System.out.println("--- Film " + (i + 1) + " of " + list.size() 
						+ " with keyword \"" + keyword + "\" ---");
			printFilmDetails(film.getShortDetails());
		}
		return list;
	}
	
	private void printFilmDetails(List<String> list) {
		for (String str : list) {
			System.out.println("\t" + str);
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
