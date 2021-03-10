import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Represents a city with multiple locations
 */
public class City {
    private int locationsID;
    private List<Location> locations;
    private final List<List<MyPair<Location, Double>>> paths;

    /**
     * Default constructor, initializes locations
     */
    public City() {
        locations = new ArrayList<>();
        paths = new ArrayList<>();
        locationsID = 0;
    }

    /**
     * Adds a location to the city
     * @param l The location
     */
    public void addLocation(Location l) {
        l.setID(locationsID++);
        locations.add(l);
        paths.add(new ArrayList<>());
    }

    /**
     * Gets a location at the specific index
     * @param index The index
     * @return The location
     */
    public Location getLocation(int index) {
        if(index > -1 && index < locations.size()) {
            return locations.get(index);
        }
        return null;
    }

    /**
     * Gets the locations
     * @return The locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Sets the locations
     * @param locations The locations
     */
    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    /**
     * Prints where you can get from every location
     */
    public void printMap() {
        for(Location l : locations) {
            if(l.getMap().size() > 0) {
                System.out.print("\nFrom " + l.getName() + " you can get to:");
            }
            else {
                System.out.print("\nYou can't get anywhere from " + l.getName());
            }
            for(Location ll : locations) {
                if(l.getMapItem(ll) != null) {
                    System.out.print("\n\t" + ll.getName() + " at the cost of " + l.getMapItem(ll));
                }
            }
        }
    }

    /**
     * Prints all the locations that can be visited and it costs nothing to do so
     */
    public void showFreeVisitable() {
        List<Location> freeVisitableLocations = new ArrayList<>();
        for(Location l : locations) {
            if(l instanceof Visitable && !(l instanceof Payable)) {
                freeVisitableLocations.add(l);
            }
        }
        freeVisitableLocations.sort((Location a, Location b) -> (int)((Visitable)b).getOpeningHours().until(((Visitable)a).getOpeningHours(), MINUTES));

        System.out.print("\nThe following locations are free to visit:");
        freeVisitableLocations.forEach(l -> l.printInfo("\n\t", ""));
        System.out.print("\n");
    }

    /**
     * Creates the path list, a list where each element is a list of locations, corresponding to the locations that can be accessed from the current element (location).
     */
    public void calculatePaths() {
        locations.forEach(l -> l.getMap().forEach((key, element) -> paths.get(l.getID()).add(new MyPair<>(key, element))));
    }

}
