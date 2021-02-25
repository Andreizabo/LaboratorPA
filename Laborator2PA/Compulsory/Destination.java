public class Destination {
    private String name;
    private int demand;

    public Destination(String name, int demand) { //The only available constructor, name and demand must be provided
        this.name = name;
        this.demand = demand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                ", demand=" + demand +
                '}';
    }

    public void printInfo() { //Prints all the information about the destination
        System.out.print("Destination " + this.name + " and has a demand of " + this.demand + ".\n");
    }
}
