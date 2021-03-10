import java.time.Duration;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Classes implementing this must have opening hours and closing hours
 */
public interface Visitable {
    default LocalTime getOpeningHours() {
        return LocalTime.parse("09:30");
    }
    default LocalTime getClosingHours() {
        return LocalTime.parse("20:00");
    }
    void setOpeningHours(LocalTime openingHours);
    void setClosingHours(LocalTime closingHours);

    static Duration getVisitingDuration(LocalTime openingHours, LocalTime closingHours) {
        return Duration.ofMinutes(openingHours.until(closingHours, MINUTES));
    }
}
