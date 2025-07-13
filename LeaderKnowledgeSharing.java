import java.util.*;

public class Island {
    public String name;
    public int population;
    public Map<String, Double> resources;
    public long lastVisited; // Added for tracking last visit time
    public List<Edge> edges; // Added for graph representation

    public Island(String name, int population) {
        this.name = name;
        this.population = population;
        this.resources = new HashMap<>();
        this.lastVisited = 0;
        this.edges = new ArrayList<>();
    }

    public void addResource(String resourceName, double amount) {
        resources.put(resourceName, amount);
    }

    public String toString() {
        return name + " (pop: " + population + ")";
    }
}

class Edge {
    Island destination;
    double travelTime; // Changed to double to align with resource amounts

    Edge(Island destination, double travelTime) {
        this.destination = destination;
        this.travelTime = travelTime;
    }
}

class LeaderKnowledgeSharing {
    private Map<String, Island> islands;

    LeaderKnowledgeSharing() {
        islands = new HashMap<>();
    }

    void addIsland(String name, int population) {
        islands.put(name, new Island(name, population));
    }

    void addRoute(String from, String to, double travelTime) {
        Island fromIsland = islands.get(from);
        Island toIsland = islands.get(to);
        if (fromIsland != null && toIsland != null) {
            fromIsland.edges.add(new Edge(toIsland, travelTime));
        }
    }

    List<String> planKnowledgeSharingRoute(String startIsland, long currentTime) {
        PriorityQueue<Island> pq = new PriorityQueue<>(
            (a, b) -> {
                if (a.population != b.population) {
                    return b.population - a.population; // Prioritize higher population
                }
                return Long.compare(a.lastVisited, b.lastVisited); // Then older visits
            }
        );

        Map<String, Double> visitTimes = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Set<String> visited = new HashSet<>();

        for (Island island : islands.values()) {
            visitTimes.put(island.name, Double.MAX_VALUE);
        }
        visitTimes.put(startIsland, 0.0);
        pq.offer(islands.get(startIsland));

        while (!pq.isEmpty()) {
            Island current = pq.poll();
            if (visited.contains(current.name)) continue;

            visited.add(current.name);
            current.lastVisited = currentTime;

            for (Edge edge : current.edges) {
                if (!visited.contains(edge.destination.name)) {
                    double newTime = visitTimes.get(current.name) + edge.travelTime;
                    if (newTime < visitTimes.get(edge.destination.name)) {
                        visitTimes.put(edge.destination.name, newTime);
                        predecessors.put(edge.destination.name, current.name);
                        pq.offer(edge.destination);
                    }
                }
            }
        }

        List<String> route = new ArrayList<>();
        String current = findFarthestIsland(visitTimes);
        while (current != null) {
            route.add(0, current);
            current = predecessors.get(current);
        }
        return route;
    }

    private String findFarthestIsland(Map<String, Double> visitTimes) {
        String farthest = null;
        double maxTime = Double.MIN_VALUE;
        for (Map.Entry<String, Double> entry : visitTimes.entrySet()) {
            if (entry.getValue() != Double.MAX_VALUE && entry.getValue() > maxTime) {
                maxTime = entry.getValue();
                farthest = entry.getKey();
            }
        }
        return farthest;
    }

    public static void main(String[] args) {
        LeaderKnowledgeSharing lks = new LeaderKnowledgeSharing();
        lks.addIsland("Hawaii", 1000);
        lks.addIsland("Tahiti", 800);
        lks.addIsland("Rapanui", 500);
        lks.addIsland("Aotearoa", 700);

        lks.addRoute("Hawaii", "Tahiti", 5.0);
        lks.addRoute("Tahiti", "Hawaii", 5.0);
        lks.addRoute("Tahiti", "Rapanui", 3.0);
        lks.addRoute("Rapanui", "Tahiti", 3.0);
        lks.addRoute("Tahiti", "Aotearoa", 4.0);
        lks.addRoute("Aotearoa", "Tahiti", 4.0);

        List<String> route = lks.planKnowledgeSharingRoute("Hawaii", System.currentTimeMillis());
        System.out.println("Route: " + route);
    }
}
