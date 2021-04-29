package application;
import java.nio.file.Path;

import javafx.scene.media.MediaPlayer;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.stage.Stage;

import application.model.*;

public class EditorController {

	@FXML 
	public TableView<Object> songTableView;

	@FXML ListView dataList;

	@FXML
	private MenuItem importSong;

	@FXML
	private MenuItem importAlbum;

	@FXML
	private MenuBar theMenu;


	@FXML
	void showLoad(ActionEvent event){
		try{
			Stage stage = (Stage) theMenu.getScene().getWindow();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("FileManager.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	//	@FXML

	//	void playSong(ActionEvent event) {
	//		ObservableList<Song> song = songTableView.getSelectionModel().getSelectedItems();
	//		Media m = new Media();
	//		MediaPlayer player = new MediaPlayer(m);
	//		player.play();
	//	}

	/* Required in order to display the songs in the pane when the FileManagerController loads the EditorController. 
	For now, we are simply loading the filename in place of the "song name" field
	Eventually, once I get the code working to parse tags, I'll make the tag parser read in 
	all the tags for each song, and represent those internally as some Song object. 
	This is just to get my code 'working' for now. */

	// Is called when Editor.fxml loads the EditorController.java
	@FXML
	void initialize() {
		
		TableColumn<Object, String> artistColumn =new TableColumn<Object,String>("Artist");
		artistColumn.setCellValueFactory(new PropertyValueFactory<Object,String>("artist"));

		TableColumn<Object, String> titleColumn  = new TableColumn<Object,String>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<Object,String>("title"));

		TableColumn<Object, String> albumColumn  = new TableColumn<Object,String>("Album");
		albumColumn.setCellValueFactory(new PropertyValueFactory<Object,String>("album"));

		TableColumn<Object, String> yearColumn  = new TableColumn<Object,String>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Object,String>("year"));

		this.songTableView.getColumns().setAll(artistColumn, titleColumn, albumColumn, yearColumn);
		
		Path selected = Main.filesystem.getSelectedDir();
		if(selected != null) {
			Song song;
			ObservableList<Object> songs = FXCollections.observableArrayList();
			Media m = new Media(selected.toUri().toString());
			m.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
				ObservableMap<String,Object> load = m.getMetadata();
				songs.addAll(load);
			});
			System.out.println(songs);
			MediaPlayer player = new MediaPlayer(m);
			MediaPlayer mp = new MediaPlayer(m);
			// Handles metadata in new thread.
			
			
			mp.setOnReady( handleMetadata(mp.getMedia().getMetadata()) );
    		this.songTableView.setItems(songs);
		}
	}


	public Thread handleMetadata(ObservableMap<String,Object> metadata) {
		Thread t = new Thread(() -> {
			String artist = (String) metadata.get("artist");
			String title  = (String) metadata.get("title");
			int year   	  = (int) metadata.get("year"); 
			String album  = (String) metadata.get("album");
			Song song 	  = new Song(artist, title, album, year);
			System.out.println("Artist: " + song.getArtist());
			System.out.println("Title: " + song.getTitle());
			System.out.println("Album: " + song.getAlbum());
			System.out.println("Year: " + song.getYear());
		});
		return t;
	}

}

