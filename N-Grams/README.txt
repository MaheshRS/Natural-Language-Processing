-----------------------------------------------------
=== Homework - 2 (N-Grams) ===
-----------------------------------------------------
Name: Mahesh Shanbhag
Email: mrs160630@utdallas.edu
NetID: mrs160630

Requires Java 8

-----------------------------------------------------
== Files ==
-----------------------------------------------------
1. README.txt: Information on installing, software requirementt and how to run the program.
2. SAMPLE OUTPUT.txt: Contains sample output of the program.

-----------------------------------------------------
== Contents ==
-----------------------------------------------------
1. USAGE: HOW TO RUN THE PROGRAM / INSTALLATION


-----------------------------------------------------
== USAGE: HOW TO RUN THE PROGRAM / INSTALLATION ==
-----------------------------------------------------
To run the program the user must navigate inside the "src" folder where the bash script is available.

===== RUNNING VIA SCRIPT =====
There is one bash script available ('run.sh').
This script internally uses another script compile.sh, that compiles the source code. Once the compilation is complete, the run.sh script then runs the program with parameters passed to it.

1. run.sh (USAGE: "sh run.sh <path to corpus> <sentence 1> <sentence 2>" or "bash run.sh <path to corpus> <sentence 1> <sentence 2>")

   a. Navigate into the 'src' folder
   b. Copy and run the command 'sh run.sh ./Corpus/Corpus.txt "<sentence 1>" "<sentence 2>"' in the terminal.

   or

   c. Copy and run the command 'bash run.sh ./Corpus/Corpus.txt "<sentence 1>" " "<sentence 2>"' in the terminal.


NOTE: 
1. PLEASE MAKE SURE THAT THE TWO SENTENCES ARE ENCLOSED WITHIN DOUBLE QUOTES.
2. THE FIRST PARAMETER IS ALWAYS THE PATH TO CORPUS.
3. THE SECOND AND THE THIRD PARAMETER ARE THE SENTENCES. REPLACE <sentence 1> AND <sentence 2> with the actual sentences.


