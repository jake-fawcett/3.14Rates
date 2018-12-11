package combat.items;

import combat.ship.RoomFunction;

public enum RoomUpgradeBank {
    //TODO Create weapons
    A("Refined Gunpowder", 150, 1.25, RoomFunction.GUN_DECK);

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

    public RoomUpgrade getUpgrade() {
        return new RoomUpgrade(name, cost, multiplier, affectsRoom);
    }
}
