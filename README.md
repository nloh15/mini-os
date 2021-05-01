Mini Operating System
====================

A simple operating system that allows users to save and print files, allowing concurrent processes to happen among disks, printers and users. Files are saved to disk sectors, with the directory manger keeping track of where files are stored. Resources are managed and supplied to threads when a print is requested in order to balance load.

Running
-------
```sh
$ make
$ java -jar OS.JAR -[number of users] [users] -[number of disks] -[number of printers]
```

Example
-------
To print files from 4 users, using 2 disks and 3 printers, we would execute:
```sh
$ java -jar OS.JAR -3 USER1 USER2 USER3 USER4 -2 -3
```