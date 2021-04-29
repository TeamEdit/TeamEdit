package application.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

//class for creating directory and selected file, Path objects
public class FS {
	private Path workDir;
	private Path selectedDir;
	public void setWorkDir(Path newDir) {
		this.workDir = newDir;
	}

	//returns directory	path
	public Path getWorkDir() {
		return workDir;
	}

	//sets file path
	public void setSelectedDir(Path selectedDir) {
		this.selectedDir = selectedDir;
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
}
