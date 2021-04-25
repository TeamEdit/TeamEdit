package application.model;

import java.nio.file.Path;

public class Song {
    private String artist = null;
    private String title = null;
    private String album = null;
    private int number = -1;
    private String filepathStr = null;
    private Path filepath;
    
    public Song(String artist, String title, String album, int number, Path filepath) {
    	this.setAlbum(album);
    	this.setArtist(artist);
    	this.setTitle(title);
    	this.setNumber(number);
    	this.setFilepath(filepath);
    	this.setFilepathStr(filepath.toString());
    }
    
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public String getFilepathStr() {
		return filepathStr;
	}

	public void setFilepathStr(String filepathStr) {
		this.filepathStr = filepathStr;
	}
	
	public Path getFilepath() { 
		return filepath;
	}
	
	public void setFilepath(Path filepath) {
		this.filepath = filepath;
	}
    
}
