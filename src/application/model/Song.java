package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//sets up the song class, string properties
public final class Song extends SimpleObjectProperty<String>{
	private StringProperty artist;
	private StringProperty title;
	private StringProperty album;
	private SimpleIntegerProperty year;

	public Song(String artist, String title, String album, int year) {
			this.setArtist(artist);
			this.setTitle(title);
			this.setAlbum(album);
			this.setYear(year);
	}

	// Artist getters / setters
	public String getArtist() {
		return getArtistProperty().get();
	}

	public StringProperty getArtistProperty() {
		if( this.artist == null ) 
			this.artist = new SimpleStringProperty(this, "artist");
		return this.artist;
	}

	public void setArtist(String artist) {
		getArtistProperty().set(artist);
	}

	// Album getters / setters
	public String getAlbum() {
		return getAlbumProperty().get();
	}

	public StringProperty getAlbumProperty() {
		if( this.album == null ) 
			this.album = new SimpleStringProperty(this, "album");
		return this.album;
	}

	public void setAlbum(String album) {
		getAlbumProperty().set(album);
	}

	// Year getters / setters	
	public int getYear() {
		return getYearProperty().get();
	}

	public IntegerProperty getYearProperty() {
		if( this.year == null ) 
			this.year = new SimpleIntegerProperty(this, "year");
		return this.year;
	}

	public void setYear(int year) {
		getYearProperty().set(year);
	}

	// Title getters / setters
	public String getTitle() {
		return getTitleProperty().get();
	}

	public StringProperty getTitleProperty() {
		if( this.title == null ) 
			this.title = new SimpleStringProperty(this, "title");
		return this.title;
	}

	public void setTitle(String title) {
		getTitleProperty().set(title);
	}
	
}
