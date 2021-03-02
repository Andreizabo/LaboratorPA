/**
 * A kind of source
 */
public class Factory extends Source {
    /**
     * The only constructor, takes two parameters
     * @param name The name of the factory
     * @param supply The supply of the factory
     */
    public Factory(String name, int supply) {
        super(name, supply);
    }

    /**
     * Returns information about the factory in a String format
     * @return A string containing information about the factory
     */
    @Override
    public String toString() {
        return "Factory{" +
                "name='" + name + '\'' +
                ", supply=" + supply +
                '}';
    }

    /**
     * Prints on the screen the information about the factory
     */
    @Override
    public void printInfo() { //Prints all the information about the factory
        System.out.print("Factory " + this.name + " and has a supply of " + this.supply + ".\n");
    }
}
