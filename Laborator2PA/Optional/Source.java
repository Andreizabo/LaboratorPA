/**
 * This is the class used to create sources
 */
public abstract class Source {
    protected String name;
    protected int supply;

    /**
     * The only constructor, it takes two parameters
     * @param name The name of the source
     * @param supply The supply of the source
     */
    public Source(String name, int supply) { //The only available constructor, name, supply and type must be provided
        this.name = name;
        this.supply = supply;
    }

    /**
     * Returns the name of the source
     * @return The name of the source
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces the name of the source
     * @param name The new name of the source
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the supply of the source
     * @return The supply of the source
     */
    public int getSupply() {
        return supply;
    }

    /**
     * Modifies the supply of the source
     * @param supply The new supply of the source
     */
    public void setSupply(int supply) {
        this.supply = supply;
    }

    /**
     * Returns information about the source in a String format
     * @return A string containing information about the source
     */
    @Override
    public String toString() {
        return "Source{" +
                "name='" + name + '\'' +
                ", supply=" + supply +
                '}';
    }

    /**
     * Verifies if the object given as parameter is a source and has the same name as the current source
     * @param obj The object to be compared
     * @return True if it does, False if it doesn't
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Source)) {
            return false;
        }
        return ((Source) obj).name.equals(this.name);
    }

    /**
     * Prints on the screen the information about the source
     */
    public void printInfo() { //Prints all the information about the source
        System.out.print("Source " + this.name + " and has a supply of " + this.supply + ".\n");
    }
}
