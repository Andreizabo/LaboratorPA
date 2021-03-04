import java.time.LocalTime;

/**
 * Classes implementing this must have opening hours and closing hours
 */
public interface Visitable {
    LocalTime getOpeningHours();
    LocalTime getClosingHours();
    void setOpeningHours(LocalTime openingHours);
    void setClosingHours(LocalTime closingHours);
}
