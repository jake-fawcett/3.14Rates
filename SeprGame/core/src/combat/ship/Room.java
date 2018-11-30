package combat.ship;

import combat.items.RoomUpgrade;

import java.util.HashMap;
import java.util.Map;

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
        final float SLOT_0_MULTIPLIER = (float) 1, SLOT_1_MULTIPLIER = (float) 0.666, SLOT_2_MULTIPLIER = (float) 0.333;
        Map<Integer, Float> slots = new HashMap<Integer, Float>();
        slots.put(0, SLOT_0_MULTIPLIER);
        slots.put(1, SLOT_1_MULTIPLIER);
        slots.put(2, SLOT_2_MULTIPLIER);
        double roomMultiplier;

        roomMultiplier = (double) hp / (double) baseHP;

        for (int i = 0; i < 3; i ++) {
            if (upgrades[i] != null) {
                roomMultiplier *= ((upgrades[i].getMultiplier() - 1)  * slots.get(i)) + 1;
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
}

