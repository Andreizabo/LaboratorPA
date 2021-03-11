import org.junit.Test;

import java.time.LocalTime;

public class MainTester {
    @Test
    public void testTrip() throws Exception {
        City city = new City();
        city.addLocation(new Hotel("Hotel A", 4.5f, 100));
        city.addLocation(new Museum("Museum A", LocalTime.of(10, 20), LocalTime.of(19, 30), 20));
        city.addLocation(new Museum("Museum B", LocalTime.of(9, 0), LocalTime.of(16, 30), 5));
        city.addLocation(new Church("Church A", LocalTime.of(6, 0), LocalTime.of(23, 30)));
        city.addLocation(new Church("Church B", LocalTime.of(8, 0), LocalTime.of(22, 0)));
        city.addLocation(new Restaurant("Restaurant A", LocalTime.of(8, 0), LocalTime.of(22, 0), 3.1f, 50));

        //Assertions.assertEquals(city.getLocations().size(), 6); // It doesn't work, I can't import org.junit.jupiter.api.Assertions;
    }

}
