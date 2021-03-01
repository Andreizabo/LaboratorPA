/**
 * The class representing a problem
 */
public class Problem {
    private Source[] sources;
    private Destination[] destinations;
    private int[][] cost;

    /**
     * A constructor which initializes the problem with the given parameters
     * @param sources The given sources
     * @param destinations The given destinations
     * @param cost The cost matrix
     */
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

    /**
     * The default constructor, it will create a new problem using the information from the one in the given example
     */
    public Problem() {
        this.sources = new Source[] {
                new Factory("S1", 10),
                new Warehouse("S2", 35),
                new Warehouse("S3", 25)
        };
        this.destinations = new Destination[] {
                new Destination("D1", 20),
                new Destination("D2", 25),
                new Destination("D3", 25)
        };
        this.cost = new int[][] {{2, 3, 1}, {5, 4, 8}, {5, 6, 8}};
    }

    /**
     * Prints the cost matrix, together with the names of the sources and destinations, close to as shown in the example
     */
    public void printProblem() {
        if(sources.length > 9 || destinations.length > 9) { //We avoid printing huge matrices because they might not fir well into the window
            System.out.print("Too many sources or destinations for matrix to be printed.\n");
            return;
        }
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

    /**
     * Creates a string containing all the information about the problem, and returns it
     * @return The String containing the information about the problem
     */
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

    /**
     * Returns the source at the given index. The index is verified beforehand
     * @param index The array index
     * @return The source at the given index
     */
    public Source getSource(int index) {
        if(index < 0 || index >= sources.length) {
            return null;
        }
        return sources[index];
    }

    /**
     * Sets the source at a given index. The index is verified beforehand
     * @param source The new source
     * @param index The array index
     */
    public void setSource(Source source, int index) {
        if(index < 0 || index >= sources.length) {
            return;
        }
        sources[index] = source;
    }

    /**
     * Returns the destination at the given index. The index is verified beforehand
     * @param index The array index
     * @return The destination at the given index
     */
    public Destination getDestination(int index) {
        if(index < 0 || index >= destinations.length) {
            return null;
        }
        return destinations[index];
    }

    /**
     * Sets the destination at the given index. The index is verified beforehand
     * @param destination The new destination
     * @param index The array index
     */
    public void setDestination(Destination destination, int index) {
        if(index < 0 || index >= destinations.length) {
            return;
        }
        destinations[index] = destination;
    }

    /**
     * Modifies the cost at the specified position. The position is verified beforehand
     * @param line The line index of the cost matrix
     * @param column The column index of the cost matrix
     * @param value The new value
     */
    public void setCostAtIndex(int line, int column, int value) { //Modifies the cost at a certain position
        if(line > 0 && line < cost.length && column > 0 && column < cost.length) {
            cost[line][column] = value;
        }
    }

    /**
     * Returns the cost at the specified position. The position is verified beforehand
     * @param line The line index of the cost matrix
     * @param column The column index of the cost matrix
     * @return The cost at the specified position
     */
    public int getCostAtIndex(int line, int column) { //Returns the cost at a certain position
        if(line > 0 && line < cost.length && column > 0 && column < cost.length) {
            return cost[line][column];
        }
        return -1;
    }

    /**
     * Returns the array of sources
     * @return The array of sources
     */
    public Source[] getSources() {
        return sources;
    }

    /**
     * Replaces the array of sources with a new one
     * @param sources The new array of sources
     */
    public void setSources(Source[] sources) {
        this.sources = sources;
    }

    /**
     * Returns the array of destinations
     * @return The array of destinations
     */
    public Destination[] getDestinations() {
        return destinations;
    }

    /**
     * Replaces the array of destinations with a new one
     * @param destinations The new array of destinations
     */
    public void setDestinations(Destination[] destinations) {
        this.destinations = destinations;
    }

    /**
     * Returns the cost matrix
     * @return The cost matrix
     */
    public int[][] getCost() {
        return cost;
    }

    /**
     * Replaces the cost matrix with a new one
     * @param cost The new cost matrix
     */
    public void setCost(int[][] cost) {
        this.cost = cost;
    }
}
