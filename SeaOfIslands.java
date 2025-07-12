import java.util.*;

public class SeaOfIslands {
    private Map<String, Island> islands = new HashMap<>();
    private Map<String, List<Route>> adjacencyList = new HashMap<>();

    public void addIsland(Island island) {
        islands.put(island.name, island);
        adjacencyList.putIfAbsent(island.name, new ArrayList<>());
    }

    public void addRoute(String from, String to, double travelTime) {
        Island source = islands.get(from);
        Island dest = islands.get(to);
        if (source != null && dest != null) {
            adjacencyList.get(from).add(new Route(source, dest, travelTime));
        }
    }

    public Island getIsland(String name) {
        return islands.get(name);
    }

    public List<Route> getRoutesFrom(String islandName) {
        return adjacencyList.getOrDefault(islandName, new ArrayList<>());
    }

    public Collection<Island> getAllIslands() {
        return islands.values();
    }
}
