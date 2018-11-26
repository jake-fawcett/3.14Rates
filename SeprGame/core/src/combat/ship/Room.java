package combat.ship;

import combat.items.RoomUpgrade;

public class Room {
    private String name;
    private double multiplier;
    private int baseHP;
    private int hp;

    /**
     * A 1x3 array of RoomUpgrade
     *
     * Stores the upgrades applied to the room. Each upgrade slot past the first has its effectiveness diminished by
     * a third. So the first slot is 100% effective, the second is 66%, then 33%.
     */
    private RoomUpgrade[] upgrades;
    private RoomFunction function;

    public Room(String name, double multiplier, int baseHealth, int hp, RoomUpgrade[] upgrades,
                RoomFunction function) {
        this.name = name;
        this.multiplier = multiplier;
        this.baseHP = baseHealth;
        this.hp = hp;
        this.upgrades = upgrades;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public int getHp() {
        return hp;
    }

    public RoomUpgrade[] getUpgrades() {
        return upgrades;
    }
}

