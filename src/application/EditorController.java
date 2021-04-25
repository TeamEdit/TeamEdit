package application;
import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import application.model.*;

public class EditorController {
	
	@FXML 
	private TableView<Song> songTableView;
	
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
    void initialize() { // Is called when Editor.fxml loads the EditorController.java 
    	/* Required in order to display the songs in the pane when the FileManagerController loads the EditorController. 
    	For now, we are simply loading the filename in place of the "song name" field
    	Eventually, once I get the code working to parse tags, I'll make the tag parser read in 
    	all the tags for each song, and represent those internally as some Song object. 
    	This is just to get my code 'working' for now. */ 
    	
    	// Setup columns for songView (artist, song, etc)
    	TableColumn<Song, String> artistColumn =new TableColumn<>("Artist");
    	artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
    	
    	TableColumn<Song, String> titleColumn  = new TableColumn<>("Title");
    	titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    	
    	TableColumn<Song, String> filePathColumn  = new TableColumn<>("Filepath");
    	filePathColumn.setCellValueFactory(new PropertyValueFactory<>("filepathStr"));
    	
    	songTableView.getColumns().addAll(artistColumn, titleColumn, filePathColumn);
    	
    	Path selected = Main.filesystem.getSelectedDir();
    	if(selected != null) {
    		System.out.println("About to load song: " + selected.toString());
    		// We have placeholder content until we can read tags....
    		// i'm just obsessed with this song so I'm using as test data
    		Song song1 = new Song("Lady Gaga", "Sine from Above", "Chromatica", 1, selected);
    		ObservableList<Song> songs = FXCollections.observableArrayList(song1);	
    		songTableView.setItems(songs);
    		System.out.println("added " + song1.getFilepathStr());
    	}
    		
    	
    }
}
