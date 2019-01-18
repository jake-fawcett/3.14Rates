package combat.items;

public class Weapon {
    private String name;
    private int cost;
    private int damage;
    private int cooldown;
    private double critChance;
    private double accuracy;
    private int currentCooldown;

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

    public void fire() {
        if (currentCooldown == 0) {
            currentCooldown = cooldown;
        } else {
            throw new IllegalStateException("Cannot fire before cooldown reaches 0");
        }
    }

    public void decrementCooldown(int ticks){
        currentCooldown -= ticks;
        if (currentCooldown < 0) {
            currentCooldown = 0;
        }
    }
}