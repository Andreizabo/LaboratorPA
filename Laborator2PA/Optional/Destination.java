/**
 * This is the class used to create destinations
 */
public class Destination {
    private String name;
    private int demand;

    /**
     * The only constructor, it takes two parameters
     * @param name The name of the destination
     * @param demand The demand of the destination
     */
    public Destination(String name, int demand) { //The only available constructor, name and demand must be provided
        this.name = name;
        this.demand = demand;
    }

    /**
     * Returns the name of the destination
     * @return The name of the destination
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the destination
     * @param name The name of the destination
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the demand of the destination
     * @return The demand of the destination
     */
    public int getDemand() {
        return demand;
    }

    /**
     * Sets the demand of the destination
     * @param demand The demand of the destination
     */
    public void setDemand(int demand) {
        this.demand = demand;
    }

    /**
     * Returns information about the destination in a String format
     * @return A string containing information about the destination
     */
    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                ", demand=" + demand +
                '}';
    }

    /**
     * Verifies if the object given as parameter is a destination and has the same name as the current destination
     * @param obj The object to be compared
     * @return True if it does, False if it doesn't
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Destination)) {
            return false;
        }
        return ((Destination) obj).name.equals(this.name);
    }

    /**
     * Prints on the screen the information about the Destination
     */
    public void printInfo() { //Prints all the information about the destination
        System.out.print("Destination " + this.name + " and has a demand of " + this.demand + ".\n");
    }
}
