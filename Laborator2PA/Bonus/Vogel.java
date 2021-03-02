import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Vogel's approximation method, implemented based on:
 * https://www.geeksforgeeks.org/transportation-problem-set-4-vogels-approximation-method/
 */
public class Vogel extends Algorithm{
    private int[] supply;
    private int[] demand;
    private int remainingDestinations, remainingSources;
    int[][] unitMatrix;
    int[][] costMatrix;

    /**
     * The only constructor, has one parameter
     * @param problem The problem that must Be solved
     */
    public Vogel(Problem problem) {
        super(problem);
    }

    /**
     * Vogel's approximation method is an algorithm that is composed of 3 phases, each with its own steps:
     * First Phase:
     * 1. We calculate the penalty, which is the absolute value of the difference between the lowest value and the second lowest value on a row/column, for each row/column.
     * 2. We choose the highest penalty, and its corresponding row/column
     * 3. We select the lowest value on that row/column, and send as many units we can from the source to the destination
     * 4. We cancel the source if it sent all of its supply, or the destination if it received all of its demand
     * 5. Repeat until only one row/column is left
     * Second Phase:
     * 1. We choose the lowest value on the remaining column/row
     * 2. We send as many units we can from the source to the destination
     * 3. We cancel the source if it sent all of its supply, or the destination if it received all of its demand
     * 4. Repeat until only one cell is left
     * Third Phase:
     * 1. We send all the units from the remaining source to the last destination, thus ending the algorithm
     * @return The approximate best configuration of the solution to the given problem
     */
    @Override
    public Solution solve() {
        Solution solution = new Solution();
        solution.setCostMatrix(problem.getCost());
        solution.setNames(problem.getSources(), problem.getDestinations());

        getData();

        int lastSource = -1, lastDestination = -1;

        while (remainingSources > 0 && remainingDestinations > 0) {
            if(remainingSources > 1 && remainingDestinations > 1) { //Phase 1
                String highestPenalty = calulatePenalty();
                int lineIndex = Integer.parseInt(highestPenalty.split(":")[1]);
                int columnIndex;
                if(highestPenalty.split(":")[0].equals("Line")) {
                    columnIndex = getLeastLineValue(lineIndex);
                }
                else {
                    int aux = getLeastColumnValue(lineIndex);
                    columnIndex = lineIndex;
                    lineIndex = aux;
                }
                transferUnits(lineIndex, columnIndex);
            }
            else if(remainingSources > 1) { //Phase 2
                if(lastDestination == -1) {
                    for(int i = 0; i < demand.length; ++i) {
                        if(demand[i] > 0) {
                            lastDestination = i;
                            break;
                        }
                    }
                }
                int index = getLeastColumnValue(lastDestination);
                transferUnits(index, lastDestination);
            }
            else if(remainingDestinations > 1) { //Phase 2
                if(lastSource == -1) {
                    for(int i = 0; i < supply.length; ++i) {
                        if(supply[i] > 0) {
                            lastSource = i;
                            break;
                        }
                    }
                }
                int index = getLeastLineValue(lastSource);
                transferUnits(lastSource, index);
            }
            else { //Phase 3
                if(lastDestination == -1) {
                    for(int i = 0; i < demand.length; ++i) {
                        if(demand[i] > 0) {
                            lastDestination = i;
                            break;
                        }
                    }
                }
                if(lastSource == -1) {
                    for(int i = 0; i < supply.length; ++i) {
                        if(supply[i] > 0) {
                            lastSource = i;
                            break;
                        }
                    }
                }
                transferUnits(lastSource, lastDestination);
            }
        }

        solution.setSolutionMatrix(unitMatrix);

        return solution;
    }

    private void getData() {
        costMatrix = problem.getCost();

        remainingDestinations = problem.getDestinations().length;
        remainingSources = problem.getSources().length;
        supply = new int[remainingSources];
        demand = new int[remainingDestinations];

        for(int i = 0; i < remainingSources; ++i) {
            supply[i] = problem.getSources()[i].getSupply();
        }
        for(int i = 0; i < remainingDestinations; ++i) {
            demand[i] = problem.getDestinations()[i].getDemand();
        }

        unitMatrix = new int[remainingSources][remainingDestinations];
        for(int i = 0; i < remainingSources; ++i) {
            Arrays.fill(unitMatrix[i], 0);
        }
    }

    private String calulatePenalty() {
        int maximum = -1, current;
        String index = "";
        for(int i = 0; i < supply.length; ++i) {
            if(supply[i] > 0) {
                current = getDifferenceLine(i);
                if (maximum < current) {
                    maximum = current;
                    index = ("Line:" + i);
                }
            }
        }
        for(int i = 0; i < demand.length; ++i) {
            if(demand[i] > 0) {
                current = getDifferenceColumn(i);
                if (maximum < current) {
                    maximum = current;
                    index = ("Column:" + i);
                }
            }
        }
        return index;
    }

    private int getLeastLineValue(int index) {
        int result = -1, least = -1;
        for(int i = 0; i < demand.length; ++i) {
            if((least == -1 || least > costMatrix[index][i]) && demand[i] > 0) {
                least = costMatrix[index][i];
                result = i;
            }
        }
        return result;
    }
    private int getLeastColumnValue(int index) {
        int result = -1, least = -1;
        for(int i = 0; i < supply.length; ++i) {
            if((least == -1 || least > costMatrix[i][index]) && supply[i] > 0) {
                least = costMatrix[i][index];
                result = i;
            }
        }
        return result;
    }

    private int getDifferenceLine(int index) {
        int[] values = new int[remainingDestinations];
        int j = 0;
        for(int i = 0; i < demand.length; ++i) {
            if(demand[i] > 0) {
                values[j++] = costMatrix[index][i];
            }
        }
        Arrays.sort(values); //Will sort "values" based on the cost in the unitMatrix
        return Math.abs(values[0] - values[1]);
    }
    private int getDifferenceColumn(int index) {
        int[] values = new int[remainingSources];
        int j = 0;
        for(int i = 0; i < supply.length; ++i) {
            if(supply[i] > 0) {
                values[j++] = costMatrix[i][index];
            }
        }
        Arrays.sort(values); //Will sort "values" based on the cost in the unitMatrix
        return Math.abs(values[0] - values[1]);
    }

    private void transferUnits(int line, int column) {
        if(supply[line] < demand[column]) {
            unitMatrix[line][column] = supply[line];
            demand[column] -= supply[line];
            supply[line] = 0;
            --remainingSources;
        }
        else if(supply[line] > demand[column]) {
            unitMatrix[line][column] = demand[column];
            supply[line] -= demand[column];
            demand[column] = 0;
            --remainingDestinations;
        }
        else {
            unitMatrix[line][column] = demand[column];
            supply[line] = 0;
            demand[column] = 0;
            --remainingSources;
            --remainingDestinations;
        }
    }
}
