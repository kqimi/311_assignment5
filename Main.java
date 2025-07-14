public class Main {
    public static void main(String[] args) {
        SeaOfIslands sea = new SeaOfIslands();

        Island hawaii = new Island("Hawaii", 1000);
        Island niihau = new Island("Niihau", 200);
        Island tahiti = new Island("Tahiti", 800);

        hawaii.addResource("SweetPotato", 0);
        niihau.addResource("ShellLeis", 100);

        sea.addIsland(hawaii);
        sea.addIsland(niihau);
        sea.addIsland(tahiti);

        sea.addRoute("Niihau", "Tahiti", 5.0);
        sea.addRoute("Tahiti", "Hawaii", 7.0);
        sea.addRoute("Hawaii", "Niihau", 9.5);

        Island rapanui = new Island("Rapanui", 500);
        Island samoa = new Island("Samoa", 600);

        sea.addIsland(rapanui);
        sea.addIsland(samoa);

        sea.addRoute("Tahiti", "Rapanui", 4.0);
        sea.addRoute("Samoa", "Rapanui", 3.5);
        sea.addRoute("Hawaii", "Samoa", 6.0);

        System.out.println("Sea of Islands:");
        for (Island island : sea.getAllIslands()) {
            System.out.println("- " + island);
        }
        
        ResourcePlanter planter = new ResourcePlanter(sea, "SweetPotato", 2);
        planter.distributeFrom("Hawaii");

        // Test ShellDistribution from Ni'ihau
        ShellDistribution distribute = new ShellDistribution(sea);
        distribute.distributeShellLeis("Niihau", 100);
    }
}
