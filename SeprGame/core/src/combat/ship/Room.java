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
/*  TODO Add in enumeration for function.
    I'm not confident on how to do them so if someone who is wants to take it I'm happy for them to.
    - Scott*/
//    private FunctionsEnumerable function GOES HERE;

    public Room(String name, double multiplier, int baseHealth, int hp, RoomUpgrade[] upgrades) {
        this.name = name;
        this.multiplier = multiplier;
        this.baseHP = baseHealth;
        this.hp = hp;
        this.upgrades = upgrades;
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

