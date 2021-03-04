import java.time.LocalTime;

/**
 * Represents a specific location, a church. It is visitable
 */
public class Church extends Location implements Visitable {
    private LocalTime openingHours;
    private LocalTime closingHours;

    /**
     * The only constructor, takes 3 parameters
     * @param name The name of the church
     * @param openingHours The opening hours of the church
     * @param closingHours The closing hours of the church
     */
    public Church(String name, LocalTime openingHours, LocalTime closingHours) {
        super(name);
        this.openingHours = openingHours;
        this.closingHours = closingHours;
    }

    /**
     * Gets the opening hours
     * @return The opening hours
     */
    @Override
    public LocalTime getOpeningHours() {
        return openingHours;
    }

    /**
     * Gets the closing hours
     * @return The closing hours
     */
    @Override
    public LocalTime getClosingHours() {
        return closingHours;
    }

    /**
     * Sets the opening hours
     * @param openingHours The new opening hours
     */
    @Override
    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    /**
     * Sets the closing hours
     * @param closingHours The new closing hours
     */
    @Override
    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }

    /**
     * Prints all the info about this church
     */
    @Override
    public void printInfo() {
        System.out.print("\n" + this.getName() + " is a church that opens at " + this.getOpeningHours().toString() + " and closes at " + this.getClosingHours() + ".\n");
    }
}
