//Task 3 Kimi Li
import java.util.*;

public class ResourcePlanter {
    private SeaOfIslands sea;      // Your graph of islands and routes
    private String resourceName;   // The name of the resource being planted
    private int canoeCount;        // Number of available canoes per planting round

    // Constructor to initialize the planter
    public ResourcePlanter(SeaOfIslands sea, String resourceName, int canoeCount) {
        this.sea = sea;
        this.resourceName = resourceName;
        this.canoeCount = canoeCount;
    }

    // Method to simulate distributing and planting the resource from a source island
    public void distributeFrom(String sourceName) {
        Set<String> planted = new HashSet<>();         // Tracks islands that have already been planted
        Queue<String> queue = new LinkedList<>();      // Queue for islands to send canoes from (BFS style)

        queue.add(sourceName);
        planted.add(sourceName);

        System.out.println("Starting to plant " + resourceName + " from " + sourceName);
        System.out.println("Planted " + resourceName + " on: " + sourceName);

        while (!queue.isEmpty()) {
            List<String> currentTrips = new ArrayList<>();

            // Send canoes from current source islands (up to canoeCount)
            for (int i = 0; i < canoeCount && !queue.isEmpty(); i++) {
                currentTrips.add(queue.poll());
            }

            // For each island in this trip, explore its outgoing routes
            for (String islandName : currentTrips) {
                List<Route> routes = sea.getRoutesFrom(islandName);

                for (Route route : routes) {
                    String nextIsland = route.to.name;

                    if (!planted.contains(nextIsland)) {
                        System.out.println("✅ Planted " + resourceName + " on: " + nextIsland + " via " + islandName);
                        planted.add(nextIsland);
                        queue.add(nextIsland); // Schedule this island to send canoes from later
                    }
                }
            }
        }

        System.out.println("Planting complete. Total islands planted: " + planted.size());
    }
}
