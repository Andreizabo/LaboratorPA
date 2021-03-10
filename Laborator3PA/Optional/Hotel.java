/**
 * Represents a specific location, a hotel. It is payable and classifiable
 */
public class Hotel extends Location implements Payable, Classifiable {
    private Float locationClass;
    private double price;

    /**
     * The only constructor, takes 3 parameters
     * @param name The name of the hotel
     * @param locationClass The ranking of the hotel
     * @param price The price to stay one night at the hotel
     */
    public Hotel(String name, Float locationClass, double price) {
        super(name);
        this.locationClass = locationClass;
        this.price = price;
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
     * This method will print all available info about this hotel
     * @param prefix What will be printed before the message
     * @param suffix What will be printed after the message
     */
    @Override
    public void printInfo(String prefix, String suffix) {
        System.out.print(prefix + this.getName() + " is a hotel with " + this.getLocationClass() + " rating and it costs " + this.getPrice() + " to stay here per night." + suffix);
    }
}
