/**
 * A kind of source
 */
public class Warehouse extends Source {
    /**
     * The only constructor, takes two parameters
     * @param name The name of the warehouse
     * @param supply The supply of the warehouse
     */
    public Warehouse(String name, int supply) {
        super(name, supply);
    }

    /**
     * Returns information about the warehouse in a String format
     * @return A string containing information about the warehouse
     */
    @Override
    public String toString() {
        return "Warehouse{" +
                "name='" + name + '\'' +
                ", supply=" + supply +
                '}';
    }

    /**
     * Prints on the screen the information about the warehouse
     */
    @Override
    public void printInfo() { //Prints all the information about the warehouse
        System.out.print("Warehouse " + this.name + " and has a supply of " + this.supply + ".\n");
    }
}
