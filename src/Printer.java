package src;
import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException; 

class Printer
    // extends Thread
{
	FileWriter myWriter;
	static int PRINTER_DELAY = 2750;
	int printerNum;
    Printer(int id)
    {	
    	this.printerNum = id;
    	// Initialize printer file
    	try {
    		System.out.println("Creating printer file: " + "PRINTER" + id);
    		myWriter = new FileWriter("PRINTER" + id);
    	}
    	catch (IOException e) {
    		System.out.println("ERROR: IOException");
    	}
    }
    void print(StringBuffer sb)
    {
    	System.out.println("Calling print to "+ "PRINTER" + printerNum);
    	String str = sb.toString();
        String printerName = "PRINTER" + printerNum;

        // Write to printer file
    	try {
            FileWriter fileWriter = new FileWriter(printerName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(str);
            printWriter.close();
    	}
    	catch (IOException e) {
    		System.out.println("ERROR: IOException");
    	}

        try {
            Thread.sleep(PRINTER_DELAY);
        }
        catch (InterruptedException e) {
            System.out.println("ERROR: InterruptedException");
        }
    }
}
