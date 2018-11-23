package combat.items;

public class RoomUpgrade {
    private String name;
    private int cost;
    private double multiplier;

    public RoomUpgrade(String name, int cost, double multiplier) {
        this.name = name;
        this.cost = cost;
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
