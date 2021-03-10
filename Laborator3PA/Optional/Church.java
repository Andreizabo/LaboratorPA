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
     * This method will print all available info about this church
     * @param prefix What will be printed before the message
     * @param suffix What will be printed after the message
     */
    @Override
    public void printInfo(String prefix, String suffix) {
        System.out.print(prefix + this.getName() + " is a church that opens at " + this.getOpeningHours().toString() + " and closes at " + this.getClosingHours() + "." + suffix);
    }
}
