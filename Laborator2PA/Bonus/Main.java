import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The main class
 */
public class Main {
    /**
     * The main method, it's the one that will be called when the program is ran
     * @param args Arguments, if any
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        //To create the example from the lab, the default constructor must be used
        /*
        Problem problem = new Problem();
        problem.printProblem();
        Algorithm greedy = new Greedy(problem);
        Algorithm vogel = new Vogel(problem);

        Solution solution = greedy.solve();
        solution.printSolution();

        solution = vogel.solve();
        solution.printSolution();
        */
        ProblemGenerator pg = new ProblemGenerator(15, 15, 125, 125, 30, 30, 50, 50);
        Problem problem;
        Algorithm greedy;
        Algorithm vogel;
        Solution solution;

        for(int i = 0; i < 100; ++i) {
            problem = pg.generate();
            greedy = new Greedy(problem);
            vogel = new Vogel(problem);

            solution = greedy.solve();
            System.out.print("\n{\n\tGreedy total cost: " + solution.calculateCost() + "\n");

            solution = vogel.solve();
            System.out.print("\tVogel total cost: " + solution.calculateCost() + "\n}");
        }
    }
}
