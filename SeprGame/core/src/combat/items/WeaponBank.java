package combat.items;

public enum WeaponBank {
    //TODO Create weapons
    A("Storm Bringer", 200, 50, 100, 0.2, 0.9);

    private String name;
    private int cost;
    private int baseDamage;
    private int baseCooldown;
    private double baseCritChance;
    private double baseChanceToHit;

    WeaponBank(String name, int cost, int baseDamage, int baseCooldown, double baseCritChance, double baseChanceToHit) {
        this.name = name;
        this.cost = cost;
        this.baseDamage = baseDamage;
        this.baseCooldown = baseCooldown;
        this.baseCritChance = baseCritChance;
        this.baseChanceToHit = baseChanceToHit;
    }

    public Weapon getWeapon() {
        return new Weapon(name, cost, baseDamage, baseCooldown, baseCritChance, baseChanceToHit);
    }
}
