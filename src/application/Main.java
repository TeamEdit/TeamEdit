package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

import java.nio.file.Paths;

import application.model.FS;
import application.model.Song;

public class Main extends Application {
	public static FS filesystem;
	public static ObservableList<Song> songs = FXCollections.observableArrayList();

	public static int mode;
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("Editor.fxml"));
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		filesystem = new FS();
		filesystem.setWorkDir(Paths.get("")); // Current working directory
		launch(args);
	}
}
