// Derived from resource Manager
package src;
class DiskManager extends ResourceManager {
	// Keep track of next frer sector on eadch disk
	// Should contain the directoryManager for finding file sectors on disk
	Disk disks[];

	DiskManager(Disk dList[]){
		super(dList.length);
		this.disks = dList;
	}

	void setNextFreeSectorOnDisk(int diskNum, int newFreeSector){
		// Set free index sector
		this.disks[diskNum].freeSectorIndex = newFreeSector;
	}
}