package combat.ship;

import combat.items.RoomUpgrade;

@SuppressWarnings("WeakerAccess")
public class Room {
    private int baseHP;
    private int hp;
    /**
     * A 1x3 array of RoomUpgrade
     * <p>
     * Stores the upgrades applied to the room. Each upgrade slot past the first has its effectiveness diminished by
     * a third. So the first slot is 100% effective, the second is 66%, then 33%.
     */
    private RoomUpgrade[] upgrades;
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

    public double getMultiplier() {
        double roomMultiplier = (double) hp / (double) baseHP;

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

    public void damage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

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

    public void delUpgrade(RoomUpgrade upgrade) {
        Boolean deleted = false;
        for (int i = 0; i < 3; i++) {
            if (!deleted) {
                if (upgrades[i] == upgrade) {
                    upgrades[i] = null;
                    deleted = true;
                }
            } else {
                upgrades[i-1] = upgrades[i];
                upgrades[i] = null;
            }
        }


    }

    public void repair(int amount) {
        hp += amount;
        if (hp > baseHP) {
            hp = baseHP;
        }
    }
}

