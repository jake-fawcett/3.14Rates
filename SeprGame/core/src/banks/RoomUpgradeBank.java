package banks;

import combat.items.RoomUpgrade;
import combat.ship.RoomFunction;

import static other.Constants.DEFAULT_UPGRADE_COST;

public enum RoomUpgradeBank {
    //TODO Create Upgrades
    A("Refined Gunpowder", DEFAULT_UPGRADE_COST, 1.25, RoomFunction.GUN_DECK);

    private String name;
    private int cost;
    private double multiplier;
    private RoomFunction affectsRoom;

    RoomUpgradeBank(String name, int cost, double multiplier, RoomFunction affectsRoom) {
        this.name = name;
        this.cost = cost;
        this.multiplier = multiplier;
        this.affectsRoom = affectsRoom;
    }

    public RoomUpgrade getRoomUpgrade() {
        return new RoomUpgrade(name, cost, multiplier, affectsRoom);
    }
}
