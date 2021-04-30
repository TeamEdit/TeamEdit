package application.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import application.Main;

//class for creating directory and selected file, Path objects
public class FS {
	private Path workDir;
	// private Path selectedDir;
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

	public void setSelectedDir(Path selectedDir) {
		this.setSelectedDirs(selectedDir);
	}
	
	
	//sets file path as selected
	public void setSelectedDirs(Path selectedDir) {
		if(this.selectedDirs != null && !this.selectedDirs.isEmpty()) this.selectedDirs.clear();
		if(Main.mode == 0) {
			if(selectedDir.getFileName().toString().endsWith(".mp3")) {
				this.selectedDirs.add(selectedDir);
			}
		}
		else if (Main.mode == 1) {
			this.selectedDirs = this.getDirContents(selectedDir);
		}
	}
	
	//returns file path	
	public ArrayList<Path> getSelectedDirs() {
		return this.selectedDirs;
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
	
	private ArrayList<Path> getDirContents(Path dir) {
		// only mp3 please
		ArrayList<Path> ls = new ArrayList<Path>();
		try {
			System.out.println("dir: " + dir.toString());
			Files.list(dir).forEach(p -> {
				if(p.getFileName().toString().endsWith(".mp3"))
					ls.add(p);
			});
		} catch (IOException e) {
			System.out.println("IOException while trying to read contents of directory " + workDir.toString());
		}
		return ls;
	}
}
