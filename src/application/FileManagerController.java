package application;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FileManagerController {
	
	@FXML
	private TextField newDir;
	
	@FXML
	private Button openFile;
	
	@FXML
	private ListView<Label> dirResults;
	
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
	
	@FXML
	private void loadNewDir(ActionEvent event) {
		String newPathStr = newDir.getText();
		Path newPath 	  = Paths.get(newPathStr);
		DirectoryStream<Path> ds = null;
		try {
			ds = Files.newDirectoryStream(newPath);
		} catch (IOException e) {
			System.out.println("Can't read path " + newPathStr);
		} 
		
		if(ds != null) {
			ObservableList<Label> ol = FXCollections.observableArrayList();
			for(Path p: ds) {
				Label l = new Label();
				l.setText(p.getFileName().toString());
				ol.add(l);
				System.out.println(p.toString());
			}
			dirResults.setItems(ol);
		}
	}
	
}
