/**
 * Classes implementing this must have a price
 */
public interface Payable {
    Double getPrice();
    void setPrice(Double price);
}
