Note: I implemented a simple Deque class in order to generate the trees for Optional and Bonus

---Compulsory:  
  
I used two functions besides the Main function:  
&nbsp;&nbsp;&nbsp;transform(int x) -> applies the needed operations on the integer x  
&nbsp;&nbsp;&nbsp;addDigits(int x) -> calculates the sum of x's digits and assigns the value of the sum to x. This is repeated until x has only one digit  
After applying these on the random number generated, the required text is printed.  

---Optional:  
  
I first verify if the given argument is an odd integer. If not, a message is sent, and the program is terminated.  
If the given integer is greater than 9, the matrix printing will be disabled.  
A random matrix for the graph is generated, with the dimensions equal to the given integer, and then it is printed.  
The program verifies whether the graph is connected and shows a proper response. If it is connected we create a partial tree from the generated graph applying a BFS-like algorithm, and then we show the corresponding matrix.  
In the end the running time is shown.  
This program must be ran with the "-Xss32M" argument for large number of nodes.  
  
---Bonus:  
  
Firstly I read an integer representing the number of nodes.  
A BFS-like algorithm is applied, instead of adding a node to the deque when it is connected to the current node we add it with a certain decreasing chance and connect it to the current node.  
In order to create the output described in the exercise I apply a DFS-like algorithm, printing for each node the nodes it is connected to, starting with the root.  
The number of nodes must be given as parameter when running the program.  
  
The "connectionModifier" constant will have the following effects on the final tree:  
&nbsp;&nbsp;- A tree with a greater breadth than depth, when the constant is lower (connectionModifier <= 40)
&nbsp;&nbsp;- A tree with a greater depth than breadth, when the constant is higher (connectionModifier > 40)
