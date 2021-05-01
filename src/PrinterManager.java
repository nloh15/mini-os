package src;

// Derived from Resource manager
class PrinterManager extends ResourceManager {

	Printer[] printers;

	PrinterManager(Printer[] printers){
		super(printers.length);
		this.printers = printers;
	}

}