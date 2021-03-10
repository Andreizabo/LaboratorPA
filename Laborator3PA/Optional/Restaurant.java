import java.time.LocalTime;

/**
 * Represents a specific location, a restaurant. It is visitable, payable and classifiable
 */
public class Restaurant extends Location implements Visitable, Payable, Classifiable {
    private LocalTime openingHours;
    private LocalTime closingHours;
    private double price;
    private Float locationClass;

    /**
     * The only constructor, takes 5 parameters
     * @param name The name of the restaurant
     * @param openingHours When the restaurant opens
     * @param closingHours When the restaurant closes
     * @param locationClass The rating of the restaurant
     * @param price The average price of a meal
     */
    public Restaurant(String name, LocalTime openingHours, LocalTime closingHours, Float locationClass, double price) {
        super(name);
        this.price = price;
        this.locationClass = locationClass;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
    }

    /**
     * Gets the rating of this location
     * @return The rating of this location
     */
    @Override
    public Float getLocationClass() {
        return locationClass;
    }

    /**
     * Sets the rating of this location
     * @param classArg The new rating of this location
     */
    @Override
    public void setLocationClass(Float classArg) {
        locationClass = classArg;
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
     * This method will print all available info about this restaurant
     * @param prefix What will be printed before the message
     * @param suffix What will be printed after the message
     */
    @Override
    public void printInfo(String prefix, String suffix) {
        System.out.print(prefix + this.getName() + " is a restaurant that opens at " + this.getOpeningHours() + ", closes at " + this.getClosingHours() + ", has a rating of " + this.getLocationClass() + " and the food costs " + this.getPrice() + " on average." + suffix);
    }
}
