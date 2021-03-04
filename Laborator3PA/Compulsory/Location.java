import java.util.HashMap;

/**
 * This is an abstract class that must be extended by others such as Church or Restaurant
 */
public abstract class Location implements Comparable<Location> {
    private HashMap<Location, Double> map;
    private String name;

    /**
     * The only constructor, takes one parameter
     * @param name The name of the location
     */
    public Location(String name) {
        this.name = name;
        map = new HashMap<>();
    }

    /**
     * Gets the Map of the location
     * @return The Map of the location, containing all accessible locations
     */
    public HashMap<Location, Double> getMap() {
        return map;
    }

    /**
     * Modifies the Map of the location
     * @param map The new Map
     */
    public void setMap(HashMap<Location, Double> map) {
        this.map = map;
    }

    /**
     * Gets the cost to go to a certain location
     * @param location The location to travel to
     * @return The cost
     */
    public Double getMapItem(Location location) {
        return map.get(location);
    }

    /**
     * Modifies the cost to go to a certain location
     * @param location The location
     * @param cost The new cost
     */
    public void addMapItem(Location location, Double cost) {
        map.put(location, cost);
    }

    /**
     * Gets the name of the location
     * @return The name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the location
     * @param name The new name of the location
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method will print all available info about a location
     */
    public abstract void printInfo();

    /**
     * Compares the current location to another one
     * @param location The location to compare to
     * @return The comparison between the names of the two locations
     */
    @Override
    public int compareTo(Location location) {
        return name.compareTo(location.getName());
    }
}
