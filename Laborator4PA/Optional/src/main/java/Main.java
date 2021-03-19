import com.github.javafaker.Faker;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Problem problem = ProblemGenerator.generateProblem(5, 2, 3);
        problem.printProblem("\n", "\n");

        Algorithm greedy = new Greedy();
        Solution solution = greedy.solve(problem);
        solution.printSolution("\n", "\n");
        solution.printSolution("", "");
    }
}
