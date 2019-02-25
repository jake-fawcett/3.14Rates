package combat.ship;

import combat.items.RoomUpgrade;

/**
 * 8 rooms make up a ship.
 * Had to implement Serializable due to encoding needed for saving game data
 */
@SuppressWarnings("WeakerAccess")
public class Room implements java.io.Serializable {
    /**
     * The default HP a room is given.
     */
    private int baseHP;
    /**
     * The current hp of the room.
     */
    private int hp;
    /**
     * A 1x3 array of RoomUpgrade
     * <p>
     * Stores the upgrades applied to the room. Each upgrade slot past the first has its effectiveness diminished by
     * a third. So the first slot is 100% effective, the second is 66%, then 33%.
     */
    private RoomUpgrade[] upgrades;
    /**
     * The function of the room in combat. See the enum RoomFunction for more info on what each function does.
     */
    private RoomFunction function;

    public Room(int baseHealth, RoomUpgrade[] upgrades, RoomFunction function) {
        this.baseHP = baseHealth;
        this.hp = baseHealth;
        this.upgrades = upgrades;
        this.function = function;
    }

    public Room(int baseHealth, int hp, RoomUpgrade[] upgrades, RoomFunction function) {
        this.baseHP = baseHealth;
        this.hp = hp;
        this.upgrades = upgrades;
        this.function = function;
    }

    /**
     * As rooms are damaged, fixed or upgraded the strength of the part of combat that they affect varies. This method
     * finds the rooms contribution to this effectiveness.
     * @return The current effectiveness contribution of this room.
     */
    public double getMultiplier() {
//        The effectiveness decrease due to damage
        double roomMultiplier = (double) hp / (double) baseHP;

//        The effectiveness increase due to upgrades. See javadoc on the property "upgrades" to see why it is calculated
//        in this way
        double slotMultiplier = 0.999;
        for (RoomUpgrade upgrade : upgrades) {
            if (upgrade != null) {
                double upgradeMultiplier = upgrade.getMultiplier();
                double slotUpgradePairMultiplier = ((upgradeMultiplier - 1) * slotMultiplier) + 1;
                roomMultiplier *= slotUpgradePairMultiplier;
                slotMultiplier -= 0.333;
            }
        }
        return roomMultiplier;
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

    /**
     * Applies damage to the room
     * @param damage The amount of damage to be applied
     */
    public void damage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    /**
     * Adds an upgrade to the room
     * @param upgrade The upgrade to add
     */
    public void addUpgrade(RoomUpgrade upgrade) {
        Boolean set = false;
        for (int i = 0; i < 3; i++) {
            if (upgrades[i] == null && !set) {
                upgrades[i] = upgrade;
                set = true;
            }
        }

        if (!set) {
            throw new IllegalStateException("Upgrade Slots full");
        }
    }

    /**
     * Deletes an upgrade from the room
     * @param upgrade The upgrade to delete
     */
    public void delUpgrade(RoomUpgrade upgrade) {
        Boolean deleted = false;
        for (int i = 0; i < 3; i++) {
            if (!deleted) {
                if (upgrades[i] == upgrade) {
                    upgrades[i] = null;
                    deleted = true;
                }
            } else {
                upgrades[i - 1] = upgrades[i];
                upgrades[i] = null;
            }
        }


    }

    /**
     * Repairs the room
     * @param amount The amount of damage to be repaired
     */
    public void repair(int amount) {
        hp += amount;
        if (hp > baseHP) {
            hp = baseHP;
        }
    }
}

