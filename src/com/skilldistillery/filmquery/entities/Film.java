package com.skilldistillery.filmquery.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.skilldistillery.filmquery.app.SETTINGS;

public class Film {
	private int id;
	private String title;
	private String description;
	private String releaseYear;
	private Integer languageId;
	private Integer rentalDuration;
	private Double rentalRate;
	private Integer length;
	private Double replacementCost;
	private String rating;
	private String specialFeatures;
	private List<Actor> actors;
	private String language;

	public Film() {
		super();
	}

	public Film(int id, String title, String description, String releaseYear, Integer languageId,
			Integer rentalDuration, Double rentalRate, Integer length, Double replacementCost, String rating,
			String specialFeatures, List<Actor> actors) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.languageId = languageId;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replacementCost = replacementCost;
		this.rating = rating;
		this.specialFeatures = specialFeatures;
		this.actors = actors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(Integer rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public Double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(Double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Double getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(Double replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getShortDetails() {
		List<String> list = new ArrayList<String>();
		list.add("ID: " + id);
		list.add("Title: " + title);
		list.add("Year: " + releaseYear);
		list.add("Rating: " + rating);
		list.add("Language: " + language);
		list.add("Description: " + description);
		for (int i = 0; i < actors.size(); i++) {
			String header = actors.size() + " Actors: ";
			StringBuilder empty = new StringBuilder();
			for (int j = 0; j < header.length(); j++) {
				empty.append(" ");
			}
			if (i == 0) {
				list.add(header + actors.get(i).getShortDetails());
			} else {
				list.add(empty + actors.get(i).getShortDetails());
			}
		}

		return list;
	}
	
	public List<String> getAdditionalDetails() {
		List<String> list = new ArrayList<String>();
		list.add("Length: " + length);
		list.add("Special Features: " + specialFeatures);
		list.add("Rental Duration: " + rentalDuration);
		list.add("Rental Rate: $" + rentalRate);
		list.add("Replacement Cost: $" + replacementCost);
		return list;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", description=" + description + ", releaseYear=" + releaseYear
				+ ", languageId=" + languageId + ", rentalDuration=" + rentalDuration + ", rentalRate=" + rentalRate
				+ ", length=" + length + ", replacementCost=" + replacementCost + ", rating=" + rating
				+ ", specialFeatures=" + specialFeatures + ", actors=" + actors + ", language=" + language + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(actors, description, id, language, languageId, length, rating, releaseYear, rentalDuration,
				rentalRate, replacementCost, specialFeatures, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(actors, other.actors) && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(language, other.language) && Objects.equals(languageId, other.languageId)
				&& Objects.equals(length, other.length) && Objects.equals(rating, other.rating)
				&& Objects.equals(releaseYear, other.releaseYear)
				&& Objects.equals(rentalDuration, other.rentalDuration) && Objects.equals(rentalRate, other.rentalRate)
				&& Objects.equals(replacementCost, other.replacementCost)
				&& Objects.equals(specialFeatures, other.specialFeatures) && Objects.equals(title, other.title);
	}

}
