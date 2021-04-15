package application.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FS {
	private Path workDir;
	private Path selectedDir;
	public void setWorkDir(Path newDir) {
		this.workDir = newDir;
	}
	
	public Path getWorkDir() {
		return workDir;
	}

	public void setSelectedDir(Path selectedDir) {
		this.selectedDir = selectedDir;
	}
	
	public Path getSelectedDir() {
		return selectedDir;
	}
	
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
