import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A travel plan, contains a city and someone's preferences
 */
public class TravelPlan {
    public City city;
    public List<Location> preferences;
    public int numberOfDays, numberOfMinutes;

    /**
     * The only constructor, takes 2 parameters
     * @param city The city
     * @param preferences The preferences
     */
    public TravelPlan(City city, List<Location> preferences, int numberOfDays, int numberOfMinutes) {
        this.city = city;
        this.preferences = preferences;
        this.numberOfDays = numberOfDays;
        this.numberOfMinutes = numberOfMinutes;
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

        if(start.equals(finish)) {
            return new ShortestPathResult(0, bestPath);
        }

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

    /**
     * Using Dijkstra's Algorithm, we return all the shortest paths from start
     * @param start The starting location
     * @return An array of pairs, a pair being the cost to that location and the path it takes to get there
     */
    public MyPair<Double, List<Location>>[] shortestPathFull(Location start) {
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
        return costs;
    }

    /**
     * Creates a trip for the tourist, prioritizing their preferences over the number of locations they can visit
     * @return The trip
     */
    public Trip travelPreference() {
        Trip result = new Trip(new ArrayList<>(), city.getLocations());

        boolean[] visited = new boolean[city.getLocations().size() + 1]; //The while inside the for will always stop before "Out of bounds"
        Arrays.fill(visited, false);
        visited[0] = true;

        //We remove inaccessible locations
        int index = 1;
        while (!isAccessible(city.getLocation(0), preferences.get(index)) || !isAccessible(preferences.get(index), city.getLocation(0))) {
            ++index;
        }

        //Each day the tourist will go to his most preferred location, if time allows it
        for(int i = 0; i < numberOfDays; ++i) {
            while (visited[index++]); //If a place is already visited, we skip it
            --index;

            if(index >= preferences.size()) {
                return result;
            }

            ShortestPathResult wayForward = shortestPath(city.getLocation(0), preferences.get(index));
            ShortestPathResult wayBack = shortestPath(preferences.get(index), city.getLocation(0));

            while(wayForward.getCost() + wayBack.getCost() > numberOfMinutes) {
                 ++index;
                 if(index >= preferences.size()) {
                     return result;
                 }
                 wayForward = shortestPath(city.getLocation(0), preferences.get(index));
                 wayBack = shortestPath(preferences.get(index), city.getLocation(0));
            }
            wayForward.getPath().forEach(l -> visited[l.getID()] = true);
            wayBack.getPath().forEach(l -> visited[l.getID()] = true);

            result.addDay(new MyPair<>(wayForward.getCost() + wayBack.getCost(), new ArrayList<>()));

            for(int j = 0; j < wayForward.getPath().size() - 1; ++j) {
                result.addToDay(wayForward.getPath().get(j), i);
            }
            for(int j = 0; j < wayBack.getPath().size(); ++j) {
                result.addToDay(wayBack.getPath().get(j), i);
            }
        }

        return result;
    }

    /**
     * Creates a trip for the tourist, prioritizing the number of locations they can visit over their preferences
     * @return The trip
     */
    public Trip travel() {
        int numberOfLocations = city.getLocations().size();
        Trip result = new Trip(new ArrayList<>(), city.getLocations());

        boolean[] visited = new boolean[numberOfLocations];
        Arrays.fill(visited, false);
        visited[0] = true;

        //We remove inaccessible locations
        int index = 1;
        while (!isAccessible(city.getLocation(0), city.getLocations().get(index)) || !isAccessible(city.getLocations().get(index), city.getLocation(0))) {
            visited[index] = true;
        }

        MyPair<Double, List<Location>>[] fromHotel = shortestPathFull(city.getLocation(0));
        MyPair<MyPair<Double, List<Location>>,  MyPair<Double, List<Location>>>[] allPaths = new MyPair[numberOfLocations];

        for(int i = 0; i < numberOfLocations; ++i) {
            allPaths[i] = new MyPair<>(fromHotel[i], shortestPath(city.getLocation(i), city.getLocation(0)).getAsPair());
        }

        Arrays.sort(allPaths, Comparator.comparingDouble((MyPair<MyPair<Double, List<Location>>, MyPair<Double, List<Location>>> p) -> (differentUnvisitedLocations(p.getFirst().getSecond(), p.getSecond().getSecond(), visited))));

        index = allPaths.length - 1;

        //Each day the tourist will go to the path with most unvisited locations, if time allows it.
        for(int i = 0; i < numberOfDays; ++i) {
            //If the tourist visited all locations we will stop calculating paths.
            boolean allVisited = true;
            for(int j = 0; j < visited.length; ++j) {
                if(!visited[j]) {
                    allVisited = false;
                    break;
                }
            }
            if(allVisited) {
                break;
            }

            while (allPaths[index].getFirst().getFirst() + allPaths[index].getSecond().getFirst() > numberOfMinutes) { //We skip over all the paths that can't be taken in the given time
                --index;
            }

            MyPair<Double, List<Location>> currentDay = new MyPair(0, new ArrayList<>());
            ShortestPathResult wayForward = new ShortestPathResult(allPaths[index].getFirst());
            ShortestPathResult wayBack = new ShortestPathResult(allPaths[index].getSecond());

            wayForward.getPath().forEach(l -> visited[l.getID()] = true);
            wayBack.getPath().forEach(l -> visited[l.getID()] = true);

            result.addDay(new MyPair<>(wayForward.getCost() + wayBack.getCost(), new ArrayList<>()));

            for(int j = 0; j < wayForward.getPath().size() - 1; ++j) {
                result.addToDay(wayForward.getPath().get(j), i);
            }
            for(int j = 0; j < wayBack.getPath().size(); ++j) {
                result.addToDay(wayBack.getPath().get(j), i);
            }

            Arrays.sort(allPaths, Comparator.comparingDouble((MyPair<MyPair<Double, List<Location>>, MyPair<Double, List<Location>>> p) -> (differentUnvisitedLocations(p.getFirst().getSecond(), p.getSecond().getSecond(), visited))));

            index = allPaths.length - 1;
        }

        return result;
    }

    private boolean isAccessible(Location start, Location finish) {
        boolean[] visited = new boolean[city.getLocations().size()];
        Arrays.fill(visited, false);
        return dfs(start, finish, visited);
    }

    private boolean dfs(Location node, Location target, boolean[] visited) {
        boolean ret = false;
        for(Location l : node.getMap().keySet()) {
            if(ret) {
                return true;
            }
            if(l.equals(target)) {
                return true;
            }
            else if(!visited[l.getID()]) {
                visited[l.getID()] = true;
                ret = dfs(l, target, visited) || ret;
            }
        }
        return false;
    }

    private int differentUnvisitedLocations(List<Location> list, boolean[] visited) {
        boolean[] visitedCopy = new boolean[visited.length];
        System.arraycopy(visited, 0, visitedCopy, 0, visited.length);

        int count = 0;
        for(Location l : list) {
            if(!visitedCopy[l.getID()]) {
                ++count;
                visitedCopy[l.getID()] = true;
            }
        }

        return count;
    }

    private int differentUnvisitedLocations(List<Location> list1, List<Location> list2, boolean[] visited) {
        List<Location> list = new ArrayList<>(list1);
        list.addAll(list2);

        boolean[] visitedCopy = new boolean[visited.length];
        System.arraycopy(visited, 0, visitedCopy, 0, visited.length);

        int count = 0;
        for(Location l : list) {
            if(!visitedCopy[l.getID()]) {
                ++count;
                visitedCopy[l.getID()] = true;
            }
        }

        return count;
    }
}
