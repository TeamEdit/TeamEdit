package application;
import java.nio.file.Path;

import com.sun.media.jfxmedia.MediaPlayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.stage.Stage;

import application.model.*;

public class EditorController {

	@FXML 
	public TableView<Song> songTableView;

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
		Song song1 = new Song();
		Path selected = Main.filesystem.getSelectedDir();
		if(selected != null) {
			System.out.println("About to load song: " + selected.toString());

			song1 = new Song(selected, songs);
			System.out.println(song1);
		}
		//create columns for songView (artist, song, etc)
		
		TableColumn<Song, String> artistColumn =new TableColumn<Song,String>("Artist");
		artistColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
		
		TableColumn<Song, String> titleColumn  = new TableColumn<Song,String>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("title"));
		
		TableColumn<Song, String> albumColumn  = new TableColumn<Song,String>("Album");
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));
		
		TableColumn<Song, String> yearColumn  = new TableColumn<Song,String>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("year"));
		
		//get path from FS class 
		ObservableList<Song> songs = FXCollections.observableArrayList();
		
			String Artist = song1.getArtist();
			songs.add(song1);
			this.songTableView.setItems(songs);
			this.songTableView.getColumns().setAll(artistColumn, titleColumn, albumColumn, yearColumn);
			
			
			System.out.println("Artist: " + song1.getArtist());
			System.out.println("Title : " + song1.getTitle());
			System.out.println("Album : " + song1.getAlbum());
			System.out.println("Year : " + song1.getYear());
		}
	}
	
}
