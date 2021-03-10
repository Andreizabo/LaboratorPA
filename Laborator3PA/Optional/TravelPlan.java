import java.util.ArrayList;
import java.util.List;

/**
 * A travel plan, contains a city and someone's preferences
 */
public class TravelPlan {
    public City city;
    public List<Location> preferences;

    /**
     * The only constructor, takes 2 parameters
     * @param city The city
     * @param preferences The preferences
     */
    public TravelPlan(City city, List<Location> preferences) {
        this.city = city;
        this.preferences = preferences;
    }

    /**
     * Using Dijkstra's Algorithm, the shortest path between "start" and "finish" is calculated
     * @param start The starting location
     * @param finish The ending location
     * @return The result, consisting of a total cost and the path made out of locations
     */
    public ShortestPathResult shortestPath(Location start, Location finish) {
        List<Location> bestPath = new ArrayList<>();
        bestPath.add(start);

        MyPair<Double, List<Location>>[] costs = new MyPair[city.getLocations().size()];
        for(int i = 0; i < costs.length; ++i) {
            costs[i] = new MyPair<>(Double.MAX_VALUE, null, 0);
        }
        costs[start.getID()] = new MyPair<>((double) 0, bestPath, 0);
        int selected = 0;

        while (selected < city.getLocations().size()) {
            double current = Double.MAX_VALUE;
            int crtIndex = 0;
            for(int i = 0; i < city.getLocations().size(); ++i) {
                if(costs[i].getMeta() == 0 && costs[i].getFirst() < current) {
                    current = costs[i].getFirst();
                    crtIndex = i;
                }
                else if(costs[i].getMeta() == 0 && costs[i].getFirst() == current) {
                    for(int j = 0; j < preferences.size(); ++j) {
                        if(preferences.get(j).equals(city.getLocations().get(i))) {
                            crtIndex = i;
                            break;
                        }
                        else if (preferences.get(j).equals(city.getLocations().get(crtIndex))) {
                            break;
                        }
                    }
                }
            }
            costs[crtIndex].setMeta(1);
            ++selected;

            if(finish.equals(city.getLocations().get(crtIndex))) {
                return new ShortestPathResult(costs[crtIndex]);
            }

            for(Location location : city.getLocations()) {
                if(city.getLocations().get(crtIndex).getMap().containsKey(location)) {
                    if(costs[location.getID()].getMeta() == 0 && costs[crtIndex].getFirst() + city.getLocations().get(crtIndex).getMap().get(location) < costs[location.getID()].getFirst()) {
                        costs[location.getID()].setFirst(costs[crtIndex].getFirst() + city.getLocations().get(crtIndex).getMap().get(location));
                        costs[location.getID()].setSecond(new ArrayList<>(costs[crtIndex].getSecond()));
                        costs[location.getID()].getSecond().add(location);
                    }
                }
            }
        }
        return null;
    }
}
