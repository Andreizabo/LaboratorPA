# Compulsory  
  
For Compulsory I created methods to save/load a catalog as/from an external file as a text.  
Images, movies, books and other files are stored in their corresponding folder.  
  
The code is [here](https://github.com/Andreizabo/LaboratorPA/tree/main/Laborator5PA/Compulsory/src/main/java).  
  
Note: The program waits for user input for each file to play. You need to enter a character in the console and press enter to play the next file.  
  
# Optional  
  
To run the program, use [run.bat](https://github.com/Andreizabo/LaboratorPA/tree/main/Laborator5PA/Optional/run.bat).  
These are the possible commands:  
* new "[catalog name]" "[save path]"  
* add "[catalog]" "[Item Type] '[item arg 1]' '[item arg 2]' '[item arg n]'"  
* list "[catalog]"  
* load "[catalog in which to load]" "[path to catalog to load]"  
* save "[catalog to save]" "[path]"  
* play "[catalog]" "[item index]"  
  
# Bonus  
  
I chose constraint programming using [Choco solver](https://choco-solver.org/) to solve the COL problem.  
My implementation doesn't always provide the best solution, and doesn't work for big instances.  
The info command doesn't work when ran from the jar because of dependencies, I tried both integrating the dependencies in the jar
and link them through the manifest using the Intellij GUI, but neither worked.