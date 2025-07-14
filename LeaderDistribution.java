import java.util.*;

public class LeaderDistribution {
    private SeaOfIslands seaOfIslands;
    private Map<Island, Integer> visitHistory;

    public LeaderDistribution(SeaOfIslands seaOfIslands) {
        this.seaOfIslands = seaOfIslands;
        this.visitHistory = new HashMap<>();
    }

    public List<List<Island>> distributeLeader(Island startIsland, int currentTime) {
        Set<Island> visited = new HashSet<>();
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
            if (a.population != b.population) return b.population - a.population;
            return a.distance - b.distance;
        });
        Map<Island, Integer> distances = new HashMap<>();
        Map<Island, Island> previous = new HashMap<>();

        // Initialize distances
        for (Island island : seaOfIslands.getAllIslands()) {
            distances.put(island, Integer.MAX_VALUE);
        }
        distances.put(startIsland, 0);
        pq.offer(new Node(startIsland, 0, startIsland.population));

        // Dijkstra's algorithm
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            Island island = node.island;
            if (visited.contains(island)) continue;
            visited.add(island);

            // Update visit history
            visitHistory.put(island, currentTime);

            // Check neighbors
            for (Route route : seaOfIslands.getRoutesFrom(island.name)) {
                Island neighbor = route.to;
                int newDist = distances.get(island) + (int) route.travelTime;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, island);
                    pq.offer(new Node(neighbor, newDist, neighbor.population));
                }
            }
        }

        // Construct paths prioritizing recency and population
        List<Island> sortedIslands = new ArrayList<>(seaOfIslands.getAllIslands());
        sortedIslands.sort((a, b) -> {
            int timeA = visitHistory.getOrDefault(a, 0);
            int timeB = visitHistory.getOrDefault(b, 0);
            if (timeA != timeB) return timeA - timeB;
            return b.population - a.population;
        });

        List<List<Island>> paths = new ArrayList<>();
        for (Island island : sortedIslands) {
            if (island != startIsland && distances.get(island) != Integer.MAX_VALUE) {
                List<Island> path = new ArrayList<>();
                Island current = island;
                while (current != null) {
                    path.add(0, current);
                    current = previous.get(current);
                }
                paths.add(path);
            }
        }

        return paths;
    }

    private static class Node {
        Island island;
        int distance;
        int population;

        Node(Island island, int distance, int population) {
            this.island = island;
            this.distance = distance;
            this.population = population;
        }
    }
}
