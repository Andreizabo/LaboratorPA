import java.util.List;

/**
 * A class to display the result given by a shortest path algorithm
 */
public class ShortestPathResult {
    private double cost;
    private List<Location> path;

    /**
     * The constructor that takes 2 parameters
     * @param cost The total cost of the path
     * @param path A list of locations through which someone must pass to get from start to finish
     */
    public ShortestPathResult(double cost, List<Location> path) {
        this.cost = cost;
        this.path = path;
    }

    /**
     * The constructor that takes a pair, similar to the one that takes 2 parameters
     * @param pair A pair of the cost of the path and the list of places one must go through to get from start to finish
     */
    public ShortestPathResult(MyPair<Double, List<Location>> pair) {
        this.cost = pair.getFirst();
        this.path = pair.getSecond();
    }

    /**
     * Prints the result
     * @param prefix What to be printed before
     * @param suffix What to be printed after
     */
    public void print(String prefix, String suffix) {
        System.out.print(prefix + "The shortest path from " + path.get(0).getName() + " to " + path.get(path.size() - 1).getName() + " is:\n\t");
        path.forEach(l -> System.out.print(" -> " + l.getName()));
        System.out.print("\nIt costs a total of " + cost + suffix);
    }
}
