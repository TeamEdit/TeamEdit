package application.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

//class for creating directory and selected file, Path objects
public class FS {
	private Path workDir;
	private Path selectedDir;
	private ArrayList<Path> selectedDirs;
	
	public FS() {
		this.selectedDirs = new ArrayList<Path>();
	}
	public void setWorkDir(Path newDir) {
		this.workDir = newDir;
	}

	//returns directory	path
	public Path getWorkDir() {
		return workDir;
	}

	//sets file path as selected
	public void setSelectedDir(Path selectedDir) {
		System.out.println("Selecting SINGLE");
		this.selectedDir = selectedDir;
	}
	
	// sets the CONTENTS of selectedDir to be selected. 
	public void setSelectedDirContents(Path selectedDir) {
		System.out.println("Selecting MULTIPLE");
		if(this.selectedDirs != null) {
			this.selectedDirs.clear();
		}		
		try {
			Files.list(selectedDir).forEach(p -> {this.selectedDirs.add(p);});
		} catch (IOException e) {
			System.out.println("IOException while trying to read contents of directory " + workDir.toString());
		}
		System.out.println(this.selectedDirs.toString());
	}

	//returns file path	
	public Path getSelectedDir() {
		return selectedDir;
	}

	//creates and returns a list of files in current working directory
	public ArrayList<Path> lsWorkDir() {
		ArrayList<Path> ls = new ArrayList<Path>();
		try {
			Files.list(workDir).forEach(p -> {
				ls.add(p);
			});
		} catch (IOException e) {
			System.out.println("IOException while trying to read contents of directory " + workDir.toString());
		}
		return ls;
	}
	
	public ArrayList<Path> getDirContents(Path dir) {
		ArrayList<Path> ls = new ArrayList<Path>();
		try {
			Files.list(dir).forEach(p -> {
				ls.add(p);
			});
		} catch (IOException e) {
			System.out.println("IOException while trying to read contents of directory " + workDir.toString());
		}
		return ls;
	}
}
