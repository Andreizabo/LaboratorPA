import java.util.ArrayList;
import java.util.Arrays;

public class Problem {
    private Source[] sources;
    private Destination[] destinations;
    private int[][] cost;

    public Problem(Source[] sources, Destination[] destinations, int[][] cost) { //The constructor used for a custom problem
        //Firstly the array lengths are verified
        if(cost.length != sources.length) {
            System.out.print("Cost matrix has wrong number of lines.\n");
            return;
        }
        for(int i = 0; i < cost.length; ++i) {
            if(cost[i].length != destinations.length) {
                System.out.print("Cost matrix has wrong number of columns at line " + i + " .\n");
                return;
            }
        }
        this.sources = sources.clone();
        this.destinations = destinations.clone();
        this.cost = cost.clone();
    }

    public Problem() { //The constructor for the default problem (The example from the lab)
        this.sources = new Source[] {
                new Source("S1", 10, SourceType.FACTORY),
                new Source("S2", 35, SourceType.WAREHOUSE),
                new Source("S3", 25, SourceType.WAREHOUSE)
        };
        this.destinations = new Destination[] {
                new Destination("D1", 20),
                new Destination("D2", 25),
                new Destination("D3", 25)
        };
        this.cost = new int[][] {{2, 3, 1}, {5, 4, 8}, {5, 6, 8}};
    }

    public void solve() {} //To be implemented in Bonus

    public void printProblem() { //Prints the cost matrix, together with the names of the sources and destinations, close to as shown in the example
        for(int i = 0; i < sources[0].getName().length() + 3; ++i) {
            System.out.print(" ");
        }
        for(int i = 0; i < destinations.length; ++i) {
            System.out.print(destinations[i].getName() + " ");
        }
        System.out.print("\n");
        for(int i = 0; i < sources.length; ++i) {
            System.out.print(sources[i].getName() + " |");
            for(int j = 0; j < destinations.length; ++j) {
                System.out.print("  " + cost[i][j]);
            }
            System.out.print(" | " + sources[i].getSupply() + "\n");
        }
        for(int i = 0; i < sources[0].getName().length() + 3; ++i) {
            System.out.print(" ");
        }
        for(int i = 0; i < destinations.length; ++i) {
            System.out.print(destinations[i].getDemand() + " ");
        }
        System.out.print("\n");
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Problem {\n\tsources = [\n\t");
        for(int i = 0; i < sources.length; ++i) {
            string.append('\t').append(sources[i].toString()).append("\n\t");
        }
        string.append("]\n\tdestinations = [\n\t");
        for(int i = 0; i < destinations.length; ++i) {
            string.append('\t').append(destinations[i].toString()).append("\n\t");
        }
        string.append("]\n\tcosts = [\n\t");
        for(int i = 0; i < cost.length; ++i) {
            string.append("\t[ ");
            for(int j = 0; j < cost[i].length; ++j) {
                string.append(cost[i][j]);
                if(j == cost[i].length - 1) {
                    string.append(" ]");
                }
                else {
                    string.append(", ");
                }
            }
            string.append("\n\t");
        }
        string.append("]\n}");
        return string.toString();
    }

    public Source getSource(int index) {
        if(index < 0 || index >= sources.length) {
            return null;
        }
        return sources[index];
    }
    public void setSource(Source source, int index) {
        if(index < 0 || index >= sources.length) {
            return;
        }
        sources[index] = source;
    }

    public Destination getDestination(int index) {
        if(index < 0 || index >= destinations.length) {
            return null;
        }
        return destinations[index];
    }
    public void setDestination(Destination destination, int index) {
        if(index < 0 || index >= destinations.length) {
            return;
        }
        destinations[index] = destination;
    }

    public void setCostAtIndex(int line, int column, int value) { //Modifies the cost at a certain position
        if(line > 0 && line < cost.length && column > 0 && column < cost.length) {
            cost[line][column] = value;
        }
    }
    public int getCostAtIndex(int line, int column) { //Returns the cost at a certain position
        if(line > 0 && line < cost.length && column > 0 && column < cost.length) {
            return cost[line][column];
        }
        return -1;
    }

    public Source[] getSources() {
        return sources;
    }
    public void setSources(Source[] sources) {
        this.sources = sources;
    }

    public Destination[] getDestinations() {
        return destinations;
    }
    public void setDestinations(Destination[] destinations) {
        this.destinations = destinations;
    }

    public int[][] getCost() {
        return cost;
    }
    public void setCost(int[][] cost) {
        this.cost = cost;
    }
}
