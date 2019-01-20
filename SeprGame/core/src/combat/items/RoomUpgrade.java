package combat.items;

import combat.ship.RoomFunction;

/**
 * An upgrade that can be bought from departments and
 * applied to a room. It increases the effectiveness of that room in combat.
 */
public class RoomUpgrade {
    /**
     * Name of the item displayed in shops etc.
     */
    private String name;
    /**
     * Price of the item in shops.
     */
    private int cost;
    /**
     * The multiplier it has on the room that it effects. E.g. 2.0 would double the effectiveness of the room it is
     * applied to.
     */
    private double multiplier;
    /**
     * The type of room that it effects. E.g. GUN_DECK would increase the damage fo each shot.
     */
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

    public RoomUpgrade(String name, int cost, double multiplier, RoomFunction affectsRoom) {
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
