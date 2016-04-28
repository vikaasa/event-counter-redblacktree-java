# event-counter-redblacktree-java
Implemented an Event Counter using a Red Black Tree in Java, which reads redirected input from a text file, executes different commands on the data, and prints the results.  The program was tested successfully with an input text file containing 100 million records. Implemented in Java

Compiling Instructions:
The project has been written in java and can be compiled using standard JDK 1.8.0_60, with javac compiler.
Unzip the submitted folder. The folder contains the project files (.java files), the Makefile, the commands.txt file, and my output files (.txt). The input text files must be placed in the project directory before running the program.
To compile in the Unix environment, run the following command after changing the directory to the project directory:
$ make
To run the program such that it supports redirected input from a text-file “filename” that contains the initial sorted list, use the following command:
$ java bbst filename
To accept the commands from a text file and print to a text file, use the following command:
 $ java bbst filename < commands.txt > myoutput.txt
Note: In order to run the program for the test file “test_100000000”, the heapspace will have to be set to 10 GB using the following command:
$ java -Xmx10000m bbst test_100000000 < commands.txt
