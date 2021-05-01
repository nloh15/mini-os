package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.lang.*;
import java.io.*;

// Each user read from file named USERi, where i is index
// Each file contain series of three comans (.save X, .end, .print X)

class UserThread extends Thread {
	String fileName;
	DiskManager diskManager;
	PrinterManager printerManager;
	DirectoryManager directoryManager;
	BufferedReader fileBuffer;

	UserThread(int userId, DiskManager diskManager, PrinterManager printerManager, DirectoryManager directoryManager){
		this.fileName = "USER" + String.valueOf(userId);
		this.diskManager = diskManager;
		this.printerManager = printerManager;
		this.directoryManager = directoryManager;

		System.out.println("User thread started for: " + this.fileName);
		try {
			// Get input file from user
			fileBuffer = new BufferedReader(new FileReader("./inputs/" + fileName));
		}
		catch (FileNotFoundException e) {
		    System.out.println("ERROR: File not found");
		    e.printStackTrace();
		}
	}

	public void run(){
		processesCommandsIn();
	}

	void processesCommandsIn(){
		System.out.println("Starting to process files for: " + this.fileName);
		// Loop for each line in filebuffer
		boolean flag = true;
		while (flag){
			String data = null;

			// Read line from file buffer
			try {
		        data = fileBuffer.readLine();
			}
			catch (IOException e) {
				System.out.println("ERROR: IOException");
			}
			catch (NullPointerException e){
				System.out.println("ERROR: NullPointerException");
			}
			if (data != null){
				String[] dataTokens = data.split(" "); 
		        String command = dataTokens[0];
		        switch(command){
		        	case ".save":
		        		System.out.println("Saving: " + dataTokens[1]);
		        		try {
		        			// Save file to disk
		        			saveFile(dataTokens[1]);
		        		}
		        		catch (InterruptedException e){
		        			System.out.println("ERROR: InterruptedException");
		        		}
		        		break;
		        	case ".print":
		        		System.out.println("Printing: " + dataTokens[1]);
		        		try {
		        			// Start new print job thread
		        			printFile(dataTokens[1]);
		        		}
		        		catch (InterruptedException e){
		        			System.out.println("ERROR: InterruptedException");
		        		}
		        		break;
		        	default:
		        		flag = false;
		        		break;
		        }
			}
			else {
				// If end of file is reached, file processing complete for user, stop the loop
				System.out.println("Finished processing file for: " + this.fileName);
				flag = false;
			}
		}
	}

	void saveFile(String fileName) throws InterruptedException{
		System.out.println("Saving file: " + fileName);
		int d = this.diskManager.request();

		// Get next free sector on disk
		int offset = this.diskManager.disks[d].freeSectorIndex;
		System.out.println("Disk slot: " + d + "\nOffset: " + offset);
		int fileLines = 0;
		boolean flag = true;

		// Read data and add to disk until .end is seen
		while(flag){
			String linesToRead = null;
			try {
				linesToRead = fileBuffer.readLine();
				System.out.println(linesToRead);
			}
			catch (IOException e) {
				System.out.println("ERROR: IOException");
			}
			catch (NullPointerException e){
				System.out.println("ERROR: NullPointerException");
			}	
			if (linesToRead != null){
				String[] linesToReadTokens = linesToRead.split(" ");
				String endOfFileChecker = linesToReadTokens[0];
				switch(endOfFileChecker){
					// If line is end of file
		        	case ".end":
		        		System.out.println("End of file detected... Entering file to directory...");
		        		// Add file to directory
						this.directoryManager.enter(stringToStringBuffer(fileName), makeFileInfo(d, offset, fileLines));
						flag = false;
		        		break;
		        	default:
		        		// Write to disk
		        		this.diskManager.disks[d].write(offset + fileLines, stringToStringBuffer(linesToRead));
						System.out.println(linesToRead);
						fileLines++;
		        		break;
		        }
			}	
		}

		// Set next free sector value
		this.diskManager.setNextFreeSectorOnDisk(d, offset+fileLines);
		
		// Release disk slot
		this.diskManager.release(d);
	}

	void printFile(String fileName) throws InterruptedException{
		PrintJobThread newJobThread = new PrintJobThread(this.directoryManager, this.printerManager, this.diskManager, stringToStringBuffer(fileName));
		// Start new print job thread
		newJobThread.start();
	}

	FileInfo makeFileInfo(int d, int offset, int fileLines){
		// Format file info
		FileInfo newFileInfo = new FileInfo();
		newFileInfo.diskNumber = d;
		newFileInfo.startingSector = offset;
		newFileInfo.fileLength = fileLines;
		return newFileInfo;
	}

	StringBuffer stringToStringBuffer(String str){
		StringBuffer buffer = new StringBuffer();
		buffer.append(str);
		return buffer;
	}
}