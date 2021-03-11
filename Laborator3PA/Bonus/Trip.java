import java.util.Arrays;
import java.util.List;

/**
 * A class representing a trip, a collection of daily visiting plans for a tourist
 */
public class Trip {
    private List<MyPair<Double, List<Location>>> dayTrips;
    private List<Location> locations;

    /**
     * The only constructor, takes 2 peremeters
     * @param dayTrips A list of plans for each day
     * @param locations The list of all locations in the city
     */
    public Trip(List<MyPair<Double, List<Location>>> dayTrips, List<Location> locations) {
        this.dayTrips = dayTrips;
        this.locations = locations;
    }

    /**
     * Adds a new day to the travel plan
     * @param newDay The new day
     */
    public void addDay(MyPair<Double, List<Location>> newDay) {
        this.dayTrips.add(newDay);
    }

    /**
     * Adds a location to a certain day
     * @param location The location
     * @param index The index of the day
     */
    public void addToDay(Location location, int index) {
        this.dayTrips.get(index).getSecond().add(location);
    }

    /**
     * Prints out the trip, specifying where the tourist goes every day and how much time it takes. Also specifies what places they didn't go to.
     * @param prefix A prefix
     * @param suffix A suffix
     */
    public void print(String prefix, String suffix) {
        if(dayTrips.size() == 0) {
            System.out.print(prefix + "The tourist couldn't go anywhere." + suffix);
            return;
        }

        boolean[] visited = new boolean[locations.size()];
        Arrays.fill(visited, false);

        System.out.print(prefix);
        for(int i = 0; i < dayTrips.size(); ++i) {
            System.out.print("On day " + (i + 1) + " the tourist chose this route: \n\t");
            dayTrips.get(i).getSecond().forEach(l -> {
                System.out.print(" -> " + l.getName());
                visited[l.getID()] = true;
            });
            System.out.print("\nIt only took him " + dayTrips.get(i).getFirst() + " minutes.\n");
        }

        for(int i = 0; i < visited.length; ++i) {
            if(!visited[i]) {
                System.out.print("The tourist couldn't go to " + locations.get(i).getName() + "\n");
            }
        }

        System.out.print(suffix);
    }
}
