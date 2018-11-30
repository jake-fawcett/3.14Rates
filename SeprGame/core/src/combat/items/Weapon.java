package combat.items;

public class Weapon {
    private String name;
    private int cost;
    private int baseDamage;
    private int baseCooldown;
    private double baseCritChance;
    private double baseChanceToHit;
    private int currentCooldown;

    public Weapon(String name, int cost, int baseDamage, int baseCooldown, double baseCritChance,
                  double baseChanceToHit) {
        this.name = name;
        this.cost = cost;
        this.baseDamage = baseDamage;
        this.baseCooldown = baseCooldown;
        this.baseCritChance = baseCritChance;
        this.baseChanceToHit = baseChanceToHit;
        this.currentCooldown = 0;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseCooldown() {
        return baseCooldown;
    }

    public double getBaseCritChance() {
        return baseCritChance;
    }

    public double getBaseChanceToHit() {
        return baseChanceToHit;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void fire() throws IllegalStateException{
        if (currentCooldown == 0) {
            currentCooldown = baseCooldown;
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