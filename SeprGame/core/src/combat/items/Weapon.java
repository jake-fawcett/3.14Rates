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
                  double baseChanceToHit, int currentCooldown) {
        this.name = name;
        this.cost = cost;
        this.baseDamage = baseDamage;
        this.baseCooldown = baseCooldown;
        this.baseCritChance = baseCritChance;
        this.baseChanceToHit = baseChanceToHit;
        this.currentCooldown = currentCooldown;
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
}