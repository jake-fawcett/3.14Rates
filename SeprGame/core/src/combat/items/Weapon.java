package combat.items;

/**
 * A weapon that can be bought from departments and applied to ships to use in combat.
 */
public class Weapon {
    /**
     * Name that appears in departments
     */
    private String name;
    /**
     * Cost of weapon in departments
     */
    private int cost;
    /**
     * Baseline damage done by weapon
     */
    private int damage;
    /**
     * Baseline cooldown of weapon (Measured in arbitrary ticks which can be adjusting in constants)
     */
    private int cooldown;
    /**
     * Baseline chance for a shot to do double damage on any given hit.
     */
    private double critChance;
    /**
     * Baseline chance for any given shot to hit
     */
    private double accuracy;
    /**
     * Cooldown value of weapon. When this is 0 the weapon can fire.
     */
    private int currentCooldown;

    /**
     * @param name       The name of the weapon in shops
     * @param cost       The cost of the weapon in shops
     * @param baseDamage The damage the weapon does on a hit
     * @param cooldown   The number of ticks a weapon needs to wait between shots
     * @param critChance The chance for a hit to do double damage
     * @param accuracy   The chance for a shot to hit
     */
    public Weapon(String name, int cost, int baseDamage, int cooldown, double critChance,
                  double accuracy) {
        this.name = name;
        this.cost = cost;
        this.damage = baseDamage;
        this.cooldown = cooldown;
        this.critChance = critChance;
        this.accuracy = accuracy;
        this.currentCooldown = 0;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getBaseDamage() {
        return damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public double getCritChance() {
        return critChance;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    /**
     * Starts the cooldown or throws can't fire exception.
     */
    public void fire() {
        if (currentCooldown == 0) {
            currentCooldown = cooldown;
        } else {
            throw new IllegalStateException("Cannot fire before cooldown reaches 0");
        }
    }

    /**
     * Adjust the cooldown
     * @param ticks Number of ticks to adjust it by. Note: It is recommended you only use the value from the constants
     *              file for this.
     */
    public void decrementCooldown(int ticks) {
        currentCooldown -= ticks;
        if (currentCooldown < 0) {
            currentCooldown = 0;
        }
    }
}