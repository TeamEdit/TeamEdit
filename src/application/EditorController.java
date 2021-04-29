package application;
import java.nio.file.Path;
import java.util.ArrayList;

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

	@FXML

	void playSong(ActionEvent event) {
	ObservableList<Song> song = songTableView.getSelectionModel().getSelectedItems();
	
	
	}

	/* Required in order to display the songs in the pane when the FileManagerController loads the EditorController. 
	For now, we are simply loading the filename in place of the "song name" field
	Eventually, once I get the code working to parse tags, I'll make the tag parser read in 
	all the tags for each song, and represent those internally as some Song object. 
	This is just to get my code 'working' for now. */

	// Is called when Editor.fxml loads the EditorController.java
	@FXML
	void initialize() {

		TableColumn<Song, String> artistColumn =new TableColumn<Song,String>("Artist");
		artistColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));

		TableColumn<Song, String> titleColumn  = new TableColumn<Song,String>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("title"));

		TableColumn<Song, String> albumColumn  = new TableColumn<Song,String>("Album");
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));

		TableColumn<Song, String> yearColumn  = new TableColumn<Song,String>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("year"));

		this.songTableView.getColumns().setAll(artistColumn, titleColumn, albumColumn, yearColumn);
		Path selected = Main.filesystem.getSelectedDir();
		
		if(selected != null) {
			//create a list of objects for metadata extract
			ArrayList<Object> list = new ArrayList<Object>();
			Media m = new Media(selected.toUri().toString());
			
			//add a listener thread for metadata updates
			m.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
				
				//in thread, extract metadata
				ObservableMap<String,Object> load = m.getMetadata();
				String artist = (String)load.get("artist");
				String title = (String)load.get("title");
				String album = (String)load.get("album");
				int year = (Integer)load.get("year");
				
				//apply to list, for some reason will add "null" till it retrieves all the data
				//if statement does NOT help
				if(artist != "null" && title != "null" && album != "null" && year != 0){
					list.add(artist);
					list.add(title);
					list.add(album);
					list.add((year));
				}
				
				//create another list, to get relevant data
				ArrayList<Object> list2 = new ArrayList<Object>();
				
				//runs 24 times, last 4 are the metadata extracted as (artist, title, album, year)
				//so when the list size is over 22, get the data to avoid null pointer exceptions
				if(list.size() > 22) {
					list2.add(list.get(20));
					list2.add(list.get(21));
					list2.add(list.get(22));
					list2.add(list.get(23));
					
					//song constructor needs an integer
					String year2 = list2.get(3).toString();
					
					//create song object
					Song song = new Song((String)list2.get(0), (String)list2.get(1), (String)list2.get(2), Integer.parseInt(year2));
					
					//add to observable list
					Main.songs.add(song);
					
					//apply items
					this.songTableView.setItems(Main.songs);
				}
			});
		}
	}
}

