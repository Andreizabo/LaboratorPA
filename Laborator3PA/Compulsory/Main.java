import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        City city = new City();
        city.addLocation(new Hotel("Hotel A", 4.5f, 100));
        city.addLocation(new Museum("Museum A", LocalTime.of(10, 20), LocalTime.of(19, 30), 20));
        city.addLocation(new Museum("Museum B", LocalTime.of(9, 0), LocalTime.of(16, 30), 5));
        city.addLocation(new Church("Church A", LocalTime.of(6, 0), LocalTime.of(23, 30)));
        city.addLocation(new Church("Church B", LocalTime.of(8, 0), LocalTime.of(22, 0)));
        city.addLocation(new Restaurant("Restaurant A", LocalTime.of(8, 0), LocalTime.of(22, 0), 3.1f, 50));

        city.getLocation(0).addMapItem(city.getLocation(1), 10.0);
        city.getLocation(0).addMapItem(city.getLocation(2), 50.0);
        city.getLocation(1).addMapItem(city.getLocation(2), 20.0);
        city.getLocation(1).addMapItem(city.getLocation(3), 20.0);
        city.getLocation(1).addMapItem(city.getLocation(4), 10.0);
        city.getLocation(2).addMapItem(city.getLocation(3), 20.0);
        city.getLocation(2).addMapItem(city.getLocation(1), 20.0);
        city.getLocation(3).addMapItem(city.getLocation(4), 30.0);
        city.getLocation(3).addMapItem(city.getLocation(5), 10.0);
        city.getLocation(4).addMapItem(city.getLocation(3), 30.0);
        city.getLocation(4).addMapItem(city.getLocation(5), 20.0);

        city.printMap();
        city.getLocation(5).printInfo();
    }
}
