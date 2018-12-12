package banks;

import combat.items.Weapon;

import static other.Constants.*;

public enum WeaponBank {
    //TODO Create weapons
    STARTER_WEAPON("Pea Shooter", DEFAULT_WEAPON_COST / 4, DEFAULT_WEAPON_DAMAGE / 4,
            DEFAULT_WEAPON_COOLDOWN / 2, DEFAULT_WEAPON_CRIT_CHANCE, DEFAULT_WEAPON_HIT_CHANCE),

    A("Storm Bringer", DEFAULT_WEAPON_COST, DEFAULT_WEAPON_DAMAGE, DEFAULT_WEAPON_COOLDOWN,
            DEFAULT_WEAPON_CRIT_CHANCE, DEFAULT_WEAPON_HIT_CHANCE);

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
