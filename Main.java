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

        System.out.println("Sea of Islands:");
        for (Island island : sea.getAllIslands()) {
            System.out.println("- " + island);
        }
    }
}
