package src;

public class Disk
    // extends Thread
{
	static final int NUM_SECTORS = 1024;
	StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];

    // Counter to maintain index of next free sector
    public int freeSectorIndex = 0;
    int diskNum;

    static int READ_WRITE_DELAY = 200;

    StringBuffer read(int sector)
    {
    	// Get data to read from sector
        try {
            Thread.sleep(READ_WRITE_DELAY);
        }
        catch (InterruptedException e){
            System.out.println("ERROR: InterruptedException!");
        }
        return sectors[sector];
    }

    void write(int sector, StringBuffer data)
    {
        try {
            Thread.sleep(READ_WRITE_DELAY);
        }
        catch (InterruptedException e){
            System.out.println("ERROR: InterruptedException!");
        }

        // Copy data from string buffer into the sector
        // Write into sectors[sector]
        sectors[sector] = data;

        // Move to next freesector index
        freeSectorIndex++;
    }

    public int getNextFreeSector(){
        return freeSectorIndex;
    }
}
