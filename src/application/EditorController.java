package application;
import java.nio.file.Path;
import java.util.ArrayList;

import org.blinkenlights.jid3.ID3Exception;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
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

public class EditorController {

	private ObservableList<String> editingSong; // Song we are currently editing
	
	@FXML 
	public TableView<Song> songTableView;

	@FXML ListView<String> dataList;

	@FXML 
	public Button applyButton;
	
	@FXML
	private MenuItem importSong;

	@FXML
	private MenuItem importAlbum;

	@FXML
	private MenuBar theMenu;


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
				System.out.println("Getting metadata for one song");
				//create a list of objects for metadata extract
				Media m = new Media(selected.toUri().toString());
				MediaPlayer mp = new MediaPlayer(m);
				mp.setOnReady( 
						new Runnable(){

							@Override
							public void run() {
								// ALL metadata is available now that MediaPlayer is ready!
								ObservableMap<String,Object> metadata = mp.getMedia().getMetadata();
								System.out.println("Artist: " + (String) metadata.get("artist"));
								System.out.println("Title: " + (String) metadata.get("title"));
							}
						});
				// Handles metadata in new thread.
				mp.setOnReady( handleMetadata(mp.getMedia().getMetadata()) );
				songTableView.setOnMousePressed(e ->{
					if (e.getClickCount() == 2 && e.isPrimaryButtonDown() ){
						this.editingSong = FXCollections.observableArrayList();
						Song song = songTableView.getSelectionModel().getSelectedItem();
						this.editingSong.add(song.getArtist());
						this.editingSong.add(song.getTitle());
						this.editingSong.add(song.getAlbum());
						this.editingSong.add(String.valueOf(song.getYear()));
						this.dataList.setItems(this.editingSong);
						this.dataList.setEditable(true);
						this.dataList.setCellFactory(TextFieldListCell.forListView());
					}
				});
			}
		}



	}
	public Thread handleMetadata(ObservableMap<String,Object> metadata) {
		Thread t = new Thread(() -> {
			String artist = (String) metadata.get("artist");
			String title  = (String) metadata.get("title");
			int year   	  = (int) metadata.get("year"); 
			String album  = (String) metadata.get("album");
			Song song 	  = new Song(artist, title, album, year);
			Main.songs.add(song);
			this.songTableView.setItems(Main.songs);
			System.out.println("Artist: " + song.getArtist());
			System.out.println("Title: " + song.getTitle());
			System.out.println("Album: " + song.getAlbum());
			System.out.println("Year: " + song.getYear());
		});
		return t;
	}
	
	@FXML
	public void saveMetadata(ActionEvent event) {
		System.out.println("apply button clicked");
		System.out.println(this.editingSong.toString());
		String artist = this.editingSong.get(0);
		String title  = this.editingSong.get(1);
		String album  = this.editingSong.get(2);
		int year	  = (Integer.valueOf(this.editingSong.get(3)));
		System.out.println("New Artist Value: " + artist);
		Song s = new Song(artist, title, album, year);
		String filename = "song.mp3";
		try {
			metaEdit.editFile(filename, s);
			Main.songs.add(s);
		} catch (ID3Exception e) {
			System.out.println("ID3Exception when saving metadata");
			e.printStackTrace();
		}
	}
}

