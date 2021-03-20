# Compulsory  
  
For Compulsory I used a LinkedList to store the students and a TreeSet to store the schools.
I also created two maps, a LinkedListMap and a TreeMap to store the preferences of each student/school  
  
# Optional  
  
For Optional I implemented a greedy algorithm to solve the problem. It will sort the students
based on their grade (if the grade is equal, based on name) and then assign each one to their
most preferred school that isn't already full.  
I used [java-faker](https://github.com/DiUS/java-faker) for the random names.  
  
# Bonus  
  
In order to apply the Gale-Shapley algorithm I had to change the structure of the problem a bit.
Since said algorithm solves the problem for a set of n men and n women I chose the following transcription:  
  * The men are represented by the students  
  * The women are represented by the schools  
  * Since schools have capacity, I created an extra array of schools in which I inserted, for each school, as many copies of it as it had capacity, thus assuring that we have the same number of students and 1-capacity schools  
The algorithm is very simmilar to the one given as example [here](https://en.wikipedia.org/wiki/Gale–Shapley_algorithm), with the following modifications:  
  * Since a student can have an equal desire to go to two or more schools, instead of treating the first not selected school from his preference list, we treat all of the schools on one level.  
The implementation of the studentPreferences is almost identical to the one for Optional, the only difference being that instead of 
storing a list of schools, we store a list of lists of schools. Each element from the big list is one level of preference:  
  * Level 1 of preference: These are the schools that the student ranks the highest  
  * Level 2 of preference: These are the schools that the student ranks the second  
  * etc.  
Therefore we create the option of a tie.