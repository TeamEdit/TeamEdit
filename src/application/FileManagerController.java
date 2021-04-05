package application;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FileManagerController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private Button openFile;
	
	@FXML
	private void loadEditor(ActionEvent event) {
		try{
			Stage stage = (Stage) openFile.getScene().getWindow();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Editor.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
