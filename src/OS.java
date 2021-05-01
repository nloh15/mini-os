package src;

import java.io.*;
import java.io.IOException;  
import javax.swing.*;

public class OS {
	int userCount;
	int diskCount;
	int printerCount;

	// Declare managers
	DiskManager diskManager;
	PrinterManager printerManager;
	DirectoryManager directoryManager;

	// Declare arrays
	Disk[] disksList;
	Printer[] printersList;
	UserThread[] usersList;

	public static void main(String[] args) {
		OS os = new OS(args);
	}

	OS(String[] args){
		// Parse input and get number of users, disks and printers
		userCount = Integer.parseInt(args[0].replaceAll("-",""));
		diskCount = Integer.parseInt(args[userCount + 1].replaceAll("-",""));
		printerCount = Integer.parseInt(args[userCount + 2].replaceAll("-",""));

		System.out.println("Number of users: " + userCount);
		System.out.println("Number of disks: " + diskCount);
		System.out.println("Number of printers:  " + printerCount);

		// Allocate memory to arrays
		disksList = new Disk[diskCount];
    	printersList = new Printer[printerCount];
    	usersList = new UserThread[userCount];

    	// Initialize managers
    	diskManager = new DiskManager(disksList);
    	printerManager = new PrinterManager(printersList);
    	directoryManager = new DirectoryManager();

    	// Initialize disks
  		for (int i = 0; i < diskCount; i++){
			disksList[i] = new Disk();
		}

		// Initialize printer
		for (int i = 0; i < printerCount; i++){
			printersList[i] = new Printer(i+1);
		}

		// Initialize and start user threads
		for (int i = 0; i < userCount; i++){
			usersList[i] = new UserThread(i+1, diskManager, printerManager, directoryManager); 
			usersList[i].start();
		}
	}
}