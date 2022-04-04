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
	
	private void getFilmById(Scanner input) {
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
		
		System.out.println("--- MORE OPTIONS ---");
		System.out.println("--- 1. View all film details");
		System.out.println("--- 2. Return to main menu");
		choice = getIntWithinRange(input, 1, 2);
		if (choice == 2) {
			doMainMenu(input);
		} else {
			System.out.println("--- VIEWING ALL FILM DETAILS ---");
			printFilmDetails(film.getShortDetails());
			printFilmDetails(film.getAdditionalDetails());
		}
		
		System.out.println("--- MORE OPTIONS ---");
		System.out.println("--- 1. Find another film by ID");
		System.out.println("--- 2. Return to main menu");
		choice = getIntWithinRange(input, 1, 2);
		if (choice == 2) {
			doMainMenu(input);
		} else {
			getFilmById(input);
		}
		
	}
	
	private void getFilmsByKeyword(Scanner input) {
		
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
			System.out.println("--- 2. No, go back to main menu");
			int choice = getIntWithinRange(input, 1, 2);
			if (choice == 1) {
				getFilmsByKeyword(input);
			} else {
				doMainMenu(input);
			}
		}
		//matches found, print short details
		//if too many results, trim result size
		int start = 0;
		if (list.size() > SETTINGS.MAX_RESULTS) {
			System.out.println("--- " + list.size() + " films found. By default, will only show " + SETTINGS.MAX_RESULTS + " at a time. ---");
			System.out.println("--- You can choose how many results to show and starting from result # ---");
			System.out.println("--- How many films to display:");
			int results = getIntWithinRange(input, 1, list.size());
			System.out.println("--- Show up to " + results + " results starting with result # (1-" + list.size() + ")");
			int choice = getIntWithinRange(input, 1, list.size());
			choice -= 1;
//			results -= 1;
			start = choice;
			if (choice + results > list.size() - 1) {
				System.out.println("if");
				list = list.subList(choice, list.size());
				System.out.println(list.size());
			} else {
				System.out.println("else");
				list = list.subList(choice,  choice + results);
				System.out.println(list.size());
			}
		}
		//display each film (up to SETTINGS.MAX_RESULTS)
		for (int i = 0; i < list.size(); i++) {
			Film film = list.get(i);
			System.out.println("--- Matched film " + (start + i + 1) + " of " + (start + list.size()) 
						+ " with keyword \"" + keyword + "\" ---");
			printFilmDetails(film.getShortDetails());
		}
		
		System.out.println("--- MORE OPTIONS ---");
		System.out.println("--- 1. Find another film by keyword");
		System.out.println("--- 2. Return to main menu");
		int choice = getIntWithinRange(input, 1, 2);
		if (choice == 2) {
			doMainMenu(input);
		} else {
			getFilmsByKeyword(input);
		}
	}
	
	private void printFilmDetails(List<String> list) {
		for (String str : list) {
			System.out.println(SETTINGS.FILM_RESULT_OFFSET + str);
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
