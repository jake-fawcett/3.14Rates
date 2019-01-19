package banks;

import combat.items.RoomUpgrade;
import combat.ship.RoomFunction;

import static other.Constants.DEFAULT_UPGRADE_COST;

public enum RoomUpgradeBank {
    REF_GUN("Refined Gunpowder", (int) (DEFAULT_UPGRADE_COST), 1.125, RoomFunction.GUN_DECK),
    SIGHT("Cannon Sights", (int) (DEFAULT_UPGRADE_COST * 1.25), 1.25, RoomFunction.GUN_DECK),
    BED("Crew Beds", (int) (DEFAULT_UPGRADE_COST * 1.25), 1.25, RoomFunction.CREW_QUARTERS),
    ORANGES("Oranges", (int) (DEFAULT_UPGRADE_COST * 1.75), 1.5, RoomFunction.CREW_QUARTERS),
    LOOK("Looking Glass", (int) (DEFAULT_UPGRADE_COST), 1.125, RoomFunction.CROWS_NEST),
    BINOC("Binoculars", (int) (DEFAULT_UPGRADE_COST * 1.6), 1.4, RoomFunction.CROWS_NEST),
    STITCH("Reinforced Sail Stitching", (int) (DEFAULT_UPGRADE_COST), 1.125, RoomFunction.HELM);


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
