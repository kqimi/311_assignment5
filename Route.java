public class Route {
    public Island from;
    public Island to;
    public double travelTime;

    public Route(Island from, Island to, double travelTime) {
        this.from = from;
        this.to = to;
        this.travelTime = travelTime;
    }
}
