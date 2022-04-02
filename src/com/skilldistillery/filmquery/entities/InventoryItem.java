package com.skilldistillery.filmquery.entities;

import java.util.Objects;

public class InventoryItem {
	private int id;
	private int filmId;
	private int storeId;
	private String mediaCondition;
	private String lastUpdate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getMediaCondition() {
		return mediaCondition;
	}

	public void setMediaCondition(String mediaCondition) {
		this.mediaCondition = mediaCondition;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public String getShortDetails() {
		return "Condition: " + mediaCondition + " (id " + id + ")";
	}

	@Override
	public String toString() {
		return "InventoryItem [id=" + id + ", filmId=" + filmId + ", storeId=" + storeId + ", mediaCondition="
				+ mediaCondition + ", lastUpdate=" + lastUpdate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId, id, lastUpdate, mediaCondition, storeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryItem other = (InventoryItem) obj;
		return filmId == other.filmId && id == other.id && Objects.equals(lastUpdate, other.lastUpdate)
				&& Objects.equals(mediaCondition, other.mediaCondition) && storeId == other.storeId;
	}

}
