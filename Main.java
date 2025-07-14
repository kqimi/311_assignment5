import java.util.*;

public class Main {
    public static void main(String[] args) {
        SeaOfIslands sea = new SeaOfIslands();

        // Create islands
        Island hawaii = new Island("Hawaii", 1000);
        Island niihau = new Island("Niihau", 200);
        Island tahiti = new Island("Tahiti", 800);

        hawaii.addResource("SweetPotato", 0);
        niihau.addResource("ShellLeis", 100);

        // Add islands to sea
        sea.addIsland(hawaii);
        sea.addIsland(niihau);
        sea.addIsland(tahiti);

        // Add routes
        sea.addRoute("Niihau", "Tahiti", 5.0);
        sea.addRoute("Tahiti", "Hawaii", 7.0);
        sea.addRoute("Hawaii", "Niihau", 9.5);

        // Test LeaderDistribution
        LeaderDistribution leaderDist = new LeaderDistribution(sea);
        List<List<Island>> paths = leaderDist.distributeLeader(hawaii, 1);

        // Print results
        System.out.println("Sea of Islands:");
        for (Island island : sea.getAllIslands()) {
            System.out.println("- " + island);
        }
        System.out.println("\nLeader Distribution Paths from Hawaii:");
        for (List<Island> path : paths) {
            System.out.println(path);
        }
    }
}
