package com.skilldistillery.filmquery.database;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
  private DatabaseAccessor db;

  @BeforeEach
  void setUp() throws Exception {
    db = new DatabaseAccessorObject();
  }

  @AfterEach
  void tearDown() throws Exception {
    db = null;
  }

  //FILM TESTS
  @Test
  void test_getFilmById_with_invalid_id_returns_null() {
    Film f = db.findFilmById(-42);
    assertNull(f);
  }
  
  @Test
  void test_getFilmById_with_id_1_returns_film_with_title_ACADEMY_DINOSAUR() {
	  Film f = db.findFilmById(1);
	  assertEquals(f.getTitle(), "ACADEMY DINOSAUR");
  }
  
  @Test
  void test_getMinMaxFilmIds_returns_1_1000() {
	  int[] range = db.getMinMaxFilmIds();
	  assertEquals(range[0], 1);
	  assertEquals(range[1], 1000);
  }
  
  //ACTOR TESTS
  //findActorById
  @Test
  void test_findActorById_with_invalid_id_returns_null() {
	  Actor a = db.findActorById(-42);
	  assertNull(a);
  }
  
  @Test
  void test_findActorById_with_id_1_getFirstName_returns_Penelope() {
	  Actor a = db.findActorById(1);
	  String firstName = a.getFirstName();
	  assertEquals(firstName, "Penelope");
  }
  
  //findActorsByFilmId
  @Test
  void test_findActorsByFilmId_with_invalid_id_returns_empty_list() {
	  List<Actor> list = db.findActorsByFilmId(-42);
	  assertEquals(list.size(), 0);
  }
  
  @Test
  void test_findActorsByFilmId_with_id_1_returns_list_with_size_10() {
	  List<Actor> list = db.findActorsByFilmId(1);
	  assertEquals(list.size(), 10);
  }

}
