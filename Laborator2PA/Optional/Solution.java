import java.util.Arrays;

/**
 * The class that will contain the solution to the problem.
 */
public class Solution {
    private int[][] solutionMatrix;
    private int[][] costMatrix;
    private String[] sourceNames;
    private String[] destinationNames;

    /**
     * Returns the total cost calculated by the algorithm.
     * @return The total cost, as a string
     */
    @Override
    public String toString() {
        return Integer.toString(calculateCost());
    }

    /**
     * Returns the total cost calculated by the algorithm.
     * @return The total cost, as an integer
     */
    public int calculateCost() {
        int totalCost = 0;
        for(int i = 0; i < solutionMatrix.length; ++i) {
            for (int j = 0; j < costMatrix.length; ++j) {
                totalCost += solutionMatrix[i][j] * costMatrix[i][j];
            }
        }
        return totalCost;
    }

    /**
     * Prints the whole solution, including which routes are involved and the total cost
     */
    public void printSolution() {
        System.out.print("\n");
        for(int i = 0; i < solutionMatrix.length; ++i) {
            boolean printBeginning = true;
            for(int j = 0; j < solutionMatrix[i].length; ++j) {
                if(solutionMatrix[i][j] != 0) {
                    if(printBeginning) {
                        System.out.print((sourceNames == null ? Integer.toString(i) :  sourceNames[i]) + " sent:\n");
                        printBeginning = false;
                    }
                    System.out.print("\t-> " + solutionMatrix[i][j] + " units to "
                            + (destinationNames == null ? Integer.toString(j) : destinationNames[j]) + ", valuing a total of "
                            + solutionMatrix[i][j] * costMatrix[i][j] + "\n");
                }
            }
        }
        System.out.print("\nThe total cost is " + calculateCost() + "\n");
    }

    /**
     * Returns the matrix containing the number of units sent on each position
     * @return The solution matrix
     */
    public int[][] getSolutionMatrix() {
        return solutionMatrix;
    }

    /**
     * Modifies the matrix containing the number of units sent on each position
     * @param solutionMatrix The new matrix
     */
    public void setSolutionMatrix(int[][] solutionMatrix) {
        this.solutionMatrix = solutionMatrix;
    }

    /**
     * Returns the matrix containing the cost of each position
     * @return The cost matrix
     */
    public int[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * Modifies the matrix containing the cost of each position
     * @param costMatrix The new matrix
     */
    public void setCostMatrix(int[][] costMatrix) {
        this.costMatrix = costMatrix;
    }

    /**
     * Sets the names of each source and destination, for better printing of the solution
     * @param sources The names of the sources, as a String Array
     * @param destinations The names of the destinations, as a String Array
     */
    public void setNames(String[] sources, String[] destinations) {
        sourceNames = sources;
        destinationNames = destinations;
    }

    /**
     * Sets the names of each source and destination, for better printing of the solution
     * @param sources The names of the sources, as a Source Array
     * @param destinations The names of the destinations, as a Destination Array
     */
    public void setNames(Source[] sources, Destination[] destinations) {
        sourceNames = new String[sources.length];
        destinationNames = new String[destinations.length];

        for(int i = 0; i < sources.length; ++i) {
            sourceNames[i] = sources[i].getName();
        }
        for(int i = 0; i < destinations.length; ++i) {
            destinationNames[i] = destinations[i].getName();
        }
    }
}
