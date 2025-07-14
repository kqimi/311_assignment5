import java.util.*;

public class ShellDistribution {
    // task: distribute kahelelani shells leis from Ni'iahu to as many islands as efficiently as possible
    // load islands and routes from SeaOfIslands graph
    private SeaOfIslands map;

    public ShellDistribution(SeaOfIslands map) {
        this.map = map;
    }

    // helper class
    private static class IslandDistance {
        String name;
        double distance;

        IslandDistance(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }

    // Dijkstra's algorithm from Ni'ihau
    public Map<String, Double> shortestPaths(String sourceName) {
        Map<String, Double> distance = new HashMap<>(); // keep track of shortest known travel time to each island
        // find the shortest distance from an island
        PriorityQueue<IslandDistance> queue = new PriorityQueue<>(Comparator.comparingDouble(a -> a.distance));

        for (Island island : map.getAllIslands()) {
            distance.put(island.name, Double.POSITIVE_INFINITY); // not discorved yet
        }

        distance.put(sourceName, 0.0); // distance is 0 since we are already there
        queue.offer(new IslandDistance(sourceName, 0.0)); // look at Ni'ihau first (put in priorty queue)
        
        while(!queue.isEmpty()) {
            IslandDistance current = queue.poll(); // get the next closet island
            String currentName = current.name;
            double currentDistance = current.distance;

            for (Route route : map.getRoutesFrom(currentName)) { // look at all routes from that island
                String neighborName = route.to.name;
                double travelTime = route.travelTime;
                double newDistance = currentDistance + travelTime; // calculate travel time to each neighbor

                if (newDistance < distance.get(neighborName)) {
                    distance.put(neighborName, newDistance); // update map 
                    queue.offer(new IslandDistance(neighborName, newDistance)); // add neighbor into priority queue to explore next
                }
            }
        }
        return distance;
    }

    // distribute amount of leis to reachable islands
    public void distributeShellLeis(String sourceIsland, double totalLeis) {
        Map<String, Double> travelTime = shortestPaths(sourceIsland); // get travel times
        List<String> reachableIslands = new ArrayList<>(); // store reachable islands in an array list
        for (String islandName : travelTime.keySet()) {
            if (!islandName.equals(sourceIsland) && travelTime.get(islandName) != Double.POSITIVE_INFINITY) {
                reachableIslands.add(islandName); // if the island is not the one were on and we know the travel time of it, add the island to the array list of reachable islands
            }
        }
        
        if (reachableIslands.isEmpty()) {
            System.out.println("No reachable islands from " + sourceIsland);
            return;
        }
        
        double leisPerIsland = totalLeis / reachableIslands.size(); // calculate the amount of leis to give to each island
        System.out.println("Distributing " + totalLeis + " kahelelani shell leis from " + sourceIsland + ":");

        for (String island : reachableIslands) {
            System.out.printf("Send %.2f leis to %s (shortest travel time: %.2f units)\n", leisPerIsland, island, travelTime.get(island));
        }
    }
}