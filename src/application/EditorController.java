package application;
import java.nio.file.Path;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditorController {
	

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
    	Path selected = Main.filesystem.getSelectedDir();
    	if(selected != null)
    		System.out.println("About to load song: " + selected);
    	
    }
}
