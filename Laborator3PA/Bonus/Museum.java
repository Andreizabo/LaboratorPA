import java.time.LocalTime;

/**
 * Represents a specific location, a museum. It is visitable and payable
 */
public class Museum extends Location implements Visitable, Payable {
    private LocalTime openingHours;
    private LocalTime closingHours;
    private double price;

    /**
     * The only constructor, takes 4 parameters
     * @param name The name of the museum
     * @param openingHours The opening hours of the museum
     * @param closingHours The closing hours of the museum
     * @param price The price of a ticket at the museum
     */
    public Museum(String name, LocalTime openingHours, LocalTime closingHours, double price) {
        super(name);
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.price = price;
    }

    /**
     * Gets the price for this location
     * @return The price for this location
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price for this location
     * @param price The new price for this location
     */
    @Override
    public void setPrice(Double price) {
        this.price = price;
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
     * This method will print all available info about this museum
     * @param prefix What will be printed before the message
     * @param suffix What will be printed after the message
     */
    @Override
    public void printInfo(String prefix, String suffix) {
        System.out.print(prefix + this.getName() + "is a museum that opens at " + this.getOpeningHours() + ", closes at " + this.getClosingHours() + " and a ticket costs " + this.getPrice() + "." + suffix);
    }
}
