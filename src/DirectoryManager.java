package src;
import java.util.Hashtable;

class DirectoryManager {
	private Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();
	// Enter file into directory
	void enter(StringBuffer fileName, FileInfo file){
		// Map fileName to file
		T.put(fileName.toString(), file);

	}

	// Search for file
	FileInfo lookup(StringBuffer fileName){
		FileInfo file = T.get(fileName.toString());
		return file;
	}
}