import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * A greedy algorithm supposed to solve the given problem
 */
public class Greedy extends Algorithm {
    private ArrayList<MyPair> costs;
    private int numberOfSources;
    private int numberOfDestinations;

    /**
     * The only constructor, it has one parameter
     * @param problem The problem that must be solved
     */
    public Greedy(Problem problem) {
        super(problem);
    }

    /**
     * The actual algorithm, it has three main steps:
     * 1. We create an array of pairs (pairs of a destination and a source)
     * 2. We sort those pairs based on the cost of transporting units on the certain position
     * 3. We go through the pairs, passing as much supply as we can to a demand, until we fulfill the total demand
     * @return The solution to the problem
     */
    @Override
    public Solution solve() {
        populateCosts();
        sortCosts();

        Solution solution = new Solution();
        solution.setCostMatrix(problem.getCost());
        solution.setNames(problem.getSources(), problem.getDestinations());

        int[][] unitMatrix = new int[numberOfSources][numberOfDestinations];
        int[] demand = new int[numberOfDestinations];
        int[] supply = new int[numberOfSources];
        int totalDemand = 0;

        for(int i = 0; i < numberOfSources; ++i) {
            Arrays.fill(unitMatrix[i], 0);
            demand[i] = problem.getDestinations()[i].getDemand();
            supply[i] = problem.getSources()[i].getSupply();
            totalDemand += demand[i];
        }

        int index = 0;
        while(totalDemand > 0 && index < costs.size()) {
            MyPair currentPair = costs.get(index);
            int send = Math.min(supply[currentPair.getFirst()], demand[currentPair.getSecond()]);
            if(send != 0) {
                supply[currentPair.getFirst()] -= send;
                demand[currentPair.getSecond()] -= send;
                unitMatrix[currentPair.getFirst()][currentPair.getSecond()] = send;
                totalDemand -= send;
            }
            ++index;
        }

        if(totalDemand > 0) {
            return null; //The problem wasn't solved
        }

        solution.setSolutionMatrix(unitMatrix);

        return solution;
    }

    private void populateCosts() {
        numberOfSources = problem.getSources().length;
        numberOfDestinations = problem.getDestinations().length;
        int[][] matrix = problem.getCost();

        costs = new ArrayList<>(numberOfSources * numberOfDestinations);

        for(int i = 0; i < numberOfSources; ++i) {
            for(int j = 0; j < numberOfDestinations; ++j) {
                costs.add(new MyPair(i, j, matrix[i][j]));
            }
        }
    }

    private void sortCosts() {
        costs.sort(Comparator.comparingInt(MyPair::getMeta)); //This will sort the pairs based on the cost from the matrix
    }
}
