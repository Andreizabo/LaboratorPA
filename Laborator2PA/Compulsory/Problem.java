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
        this.sources = new Source[3];
        this.destinations = new Destination[3];
        this.cost = new int[3][3];

        sources[0] = new Source("S1", 10, SourceType.FACTORY);
        sources[1] = new Source("S2", 35, SourceType.WAREHOUSE);
        sources[2] = new Source("S3", 25, SourceType.WAREHOUSE);

        destinations[0] = new Destination("D1", 20);
        destinations[1] = new Destination("D2", 25);
        destinations[2] = new Destination("D3", 25);

        cost[0][0] = 2;
        cost[0][1] = 3;
        cost[0][2] = 1;
        cost[1][0] = 5;
        cost[1][1] = 4;
        cost[1][2] = 8;
        cost[2][0] = 5;
        cost[2][1] = 6;
        cost[2][2] = 8;
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
        return "Problem{" +
                "sources=" + Arrays.toString(sources) +
                ", destinations=" + Arrays.toString(destinations) +
                ", cost=" + Arrays.toString(cost) +
                '}';
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
