package combat.items;

import combat.ship.RoomFunction;

public class RoomUpgrade {
    private String name;
    private int cost;
    private double multiplier;
    private RoomFunction affectsRoom;

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public RoomFunction getAffectsRoom() {
        return affectsRoom;
    }

    public RoomUpgrade(String name, int cost, double multiplier, RoomFunction affectsRoom) throws IllegalStateException{
        this.name = name;
        this.cost = cost;
        this.multiplier = multiplier;
        if (affectsRoom == RoomFunction.NON_FUNCTIONAL) {
            throw new IllegalArgumentException("Room upgrades cannot be applied to non-functional rooms!");
        } else {
            this.affectsRoom = affectsRoom;
        }
    }
}
