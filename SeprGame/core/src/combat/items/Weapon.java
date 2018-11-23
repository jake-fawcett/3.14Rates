package combat.items;

public class Weapon {
    private String name;
    private int Cost;
    private int baseDamage;
    private int baseCooldown;
    private int baseCritChance;
    private int baseChanceToHit;
    private int currentCooldown;

    public Weapon(String name, int cost, int baseDamage, int baseCooldown, int baseCritChance, int baseChanceToHit,
                  int currentCooldown) {
        this.name = name;
        Cost = cost;
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
        return Cost;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseCooldown() {
        return baseCooldown;
    }

    public int getBaseCritChance() {
        return baseCritChance;
    }

    public int getBaseChanceToHit() {
        return baseChanceToHit;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }
}
