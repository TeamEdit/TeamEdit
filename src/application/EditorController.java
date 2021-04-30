package application;

import java.nio.file.Path;
import java.util.ArrayList;

import org.blinkenlights.jid3.ID3Exception;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.stage.Stage;

import application.model.*;


// EditorController : controller for the Editor / Player scene
public class EditorController {

	private ObservableList<String> editingSongData; // Song we are currently editing
	
	@FXML 
	public TableView<Song> songTableView;

	@FXML ListView<String> dataList;

	@FXML 
	public Button applyButton, playBut, stopBut;
	
	@FXML
	private MenuItem importSong;

	@FXML
	private MenuItem importAlbum;

	@FXML
	private MenuBar theMenu;

	private Song editingSong;
	private Path path2;
	private Media media;
	private MediaPlayer player;


	@FXML
	void showLoad(ActionEvent event){
		MenuItem clicked = (MenuItem) event.getSource();
		if( clicked.getId().equals("importSong") ) Main.mode = 0; // Import song
		else if (clicked.getId().equals("importAlbum")) Main.mode = 1; // Import album

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


		ArrayList<Path> selectedSongs = Main.filesystem.getSelectedDirs();
		if(selectedSongs != null && !selectedSongs.isEmpty()) {
			for(Path selected : selectedSongs) {
				//create a list of objects for metadata extract
				Media m = new Media(selected.toUri().toString());
				MediaPlayer mp = new MediaPlayer(m);
				// Handles metadata in new thread.
				mp.setOnReady( handleMetadata(mp.getMedia().getMetadata(), selected ) );
				songTableView.setOnMousePressed(e ->{
					if (e.getClickCount() == 1 && e.isPrimaryButtonDown() ){
						this.editingSongData = FXCollections.observableArrayList();
						this.editingSong     = songTableView.getSelectionModel().getSelectedItem();
						this.editingSongData.add(this.editingSong.getArtist());
						this.editingSongData.add(this.editingSong.getTitle());
						this.editingSongData.add(this.editingSong.getAlbum());
						this.editingSongData.add(String.valueOf(this.editingSong.getYear()));
						this.dataList.setItems(this.editingSongData);
						this.dataList.setEditable(true);
						this.dataList.setCellFactory(TextFieldListCell.forListView());
					} else if ( e.getClickCount() == 2 && e.isPrimaryButtonDown()  ) {
						path2 = this.editingSong.getPath();
						media = new Media(path2.toUri().toString());
						player = new MediaPlayer(media);
						player.play();
					}
				});
			}
		}



	}
	public Thread handleMetadata(ObservableMap<String,Object> metadata, Path p) {
		Thread t = new Thread(() -> {
			String artist = (String) metadata.get("artist");
			String title  = (String) metadata.get("title");
			int year   	  = (int) metadata.get("year"); 
			String album  = (String) metadata.get("album");
			Song song 	  = new Song(artist, title, album, year);
			song.setPath(p);
			Main.songs.add(song);
			this.songTableView.setItems(Main.songs);
		});
		return t;
	}
	
	@FXML
	public void saveMetadata(ActionEvent event) {
		String artist = this.dataList.getItems().get(0);
		String title  = this.dataList.getItems().get(1);
		String album  = this.dataList.getItems().get(2);
		int year	  = (Integer.valueOf(this.dataList.getItems().get(3)));
		Song newSong = new Song(artist, title, album, year);
		String filename = this.editingSong.getPath().toString();
		newSong.setPath(this.editingSong.getPath());
		
		try {
			metaEdit.editFile(filename, newSong); // saves to disk
			// loop through to find song whose filename matches the filename
			int i = 0;
			for(Song s: Main.songs) {
				if(s.getPath().equals(newSong.getPath())) {
					break;
				}
				i++;
			}
			Main.songs.set(i, newSong);
			this.songTableView.setItems(Main.songs);

			
		} catch (ID3Exception e) {
			System.out.println("ID3Exception when saving metadata");
			e.printStackTrace();
		}
	}
	
	@FXML
	public void playSong(ActionEvent event) {
		player.play();
	}
	
	@FXML
	public void stopSong(ActionEvent event) {
		player.stop();
	}
}

