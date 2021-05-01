package src;

class PrintJobThread
    extends Thread
{
    StringBuffer line = new StringBuffer(); // only allowed one line to reuse for read from disk and print to printer
    int p;
    FileInfo f;
    DirectoryManager directoryManager;
    PrinterManager printerManager;
    DiskManager diskManager;

    PrintJobThread(DirectoryManager dirManager, PrinterManager pManager, DiskManager diskManager, StringBuffer fileName)
    {
    	this.f = dirManager.lookup(fileName);
        this.directoryManager = dirManager;
        this.printerManager = pManager;
        this.diskManager = diskManager;
    }
    
    public void run()
    {
        System.out.println("Starting print job...");
        int start = this.f.startingSector;
        int d = this.f.diskNumber;
        
        try {
            this.p = this.printerManager.request();
            System.out.println("Resources allocated: PRINTER" + this.p);
        }
        catch (InterruptedException e){
            System.out.println("ERROR: InterruptedException");
        }

        int fileLength = f.fileLength;

        System.out.println("Printing file to: PRINTER" + this.p);
        for (int i = 0; i < fileLength; i++){
            line = this.diskManager.disks[d].read(start + i);
            this.printerManager.printers[this.p].print(line);
        }

        System.out.println("Successfully printed to PRINTER" + this.p);
        printerManager.release(this.p);
    }
}
