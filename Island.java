import java.util.HashMap;
import java.util.Map;

public class Island {
    public String name;
    public int population;
    public Map<String, Double> resources;

    public Island(String name, int population) {
        this.name = name;
        this.population = population;
        this.resources = new HashMap<>();
    }

    public void addResource(String resourceName, double amount) {
        resources.put(resourceName, amount);
    }

    public String toString() {
        return name + " (pop: " + population + ")";
    }
}
