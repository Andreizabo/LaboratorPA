import java.util.ArrayList;
import java.util.List;

/**
 * Represents a city with multiple locations
 */
public class City {
    private List<Location> locations;

    /**
     * Default constructor, initializes locations
     */
    public City() {
        locations = new ArrayList<>();
    }

    /**
     * Adds a location to the city
     * @param l The location
     */
    public void addLocation(Location l) {
        locations.add(l);
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
}
