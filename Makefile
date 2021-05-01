build:
	@echo ---- creating a jar file with java ----
	javac src/*.java
	jar cfm OS.jar src/MANIFEST.MF src/*.class

clean:
	rm -f OS.jar
	rm -f src/*.class