/**
 * The main class
 */
public class Main {
    /**
     * The main method, it's the one that will be called when the program is ran
     * @param args Arguments, if any
     */
    public static void main(String[] args) {
        //To create the example from the lab, the default constructor must be used
        Problem problem = new Problem();
        problem.printProblem();
        Algorithm greedy = new Greedy(problem);
        Solution solution = greedy.solve();
        solution.printSolution();
    }
}
