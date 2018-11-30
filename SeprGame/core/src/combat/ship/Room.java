package combat.ship;

import combat.items.RoomUpgrade;

@SuppressWarnings("WeakerAccess")
public class Room {
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

    public Room(int baseHealth, int hp, RoomUpgrade[] upgrades, RoomFunction function) {
        this.baseHP = baseHealth;
        this.hp = hp;
        this.upgrades = upgrades;
        this.function = function;
    }

    public double getMultiplier() {
        //Fixme Take into account Upgrades
        return (float) hp/ (float) baseHP;
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

    public RoomFunction getFunction() {
        return function;
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }
}

