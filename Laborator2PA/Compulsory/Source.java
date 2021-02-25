public class Source {
    private String name;
    private int supply;
    private SourceType type;

    public Source(String name, int supply, SourceType type) { //The only available constructor, name, supply and type must be provided
        this.name = name;
        this.supply = supply;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Source{" +
                "name='" + name + '\'' +
                ", supply=" + supply +
                ", type=" + type +
                '}';
    }

    public void printInfo() { //Prints all the information about the source
        System.out.print("Source " + this.name + " has a supply of " + this.supply + " and is a " + this.type + ".\n");
    }
}
