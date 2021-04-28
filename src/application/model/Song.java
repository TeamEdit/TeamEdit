package application.model;

import java.io.File;
import java.nio.file.Path;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.WritableObjectValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
//sets up the song class, string properties
public final class Song extends SimpleObjectProperty<String>{
	private StringProperty artist;
	private StringProperty title;
	private StringProperty album;
	private StringProperty year;



	private Media media; // The actual javafx media

	public Song(Path filepath, ObservableList<Song> songs) {
		//Set the file path for media object 
		File f = filepath.toFile();
		System.out.println(f.toString());
		//create the media object    	
		this.media = new Media(f.toURI().toString());
		
		//listens for values and adds to class strings  	
		this.media.getMetadata().addListener(new MapChangeListener<String, Object>() {
			@Override
			public void onChanged(Change<? extends String, ? extends Object> ch) {
				if (ch.wasAdded()) {
					if(handleMetadata(ch.getKey(), ch.getValueAdded())) {
						System.out.println("Loaded key: " + ch.getKey() + ": " + ch.getValueAdded());
					}
				}
			}

		});

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
	public String getYear() {
		return getYearProperty().get();
	}

	public StringProperty getYearProperty() {
		if( this.year == null ) 
			this.year = new SimpleStringProperty(this, "year");
		return this.year;
	}

	public void setYear(String year) {
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

	//handles adding metadata to appropriate sting key
	private boolean handleMetadata(String key, Object value) {
		boolean loadedValidKey = false;
		if (key.equals("album")) {
			this.setAlbum(value.toString());
			loadedValidKey = true;
		} else if (key.equals("artist")) {
			this.setArtist(value.toString());
			loadedValidKey = true;
		} else if (key.equals("title")) {
			this.setTitle(value.toString());
			loadedValidKey = true;
		} else if (key.equals("year")) {
			this.setYear(value.toString());
			loadedValidKey = true;
		}
		return loadedValidKey;
	}
	
}
