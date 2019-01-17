package banks;

import combat.items.Weapon;

import static other.Constants.*;

public enum WeaponBank {
    STARTER_WEAPON("Pea Shooter", DEFAULT_WEAPON_COST / 4, DEFAULT_WEAPON_DAMAGE / 4,
            DEFAULT_WEAPON_COOLDOWN / 2, DEFAULT_WEAPON_CRIT_CHANCE, DEFAULT_WEAPON_HIT_CHANCE),

    STORM("Storm Bringer", DEFAULT_WEAPON_COST, DEFAULT_WEAPON_DAMAGE, DEFAULT_WEAPON_COOLDOWN,
            DEFAULT_WEAPON_CRIT_CHANCE, DEFAULT_WEAPON_HIT_CHANCE),

    SCATTER("Scatter Gun", DEFAULT_WEAPON_COST * 3, DEFAULT_WEAPON_DAMAGE * 4, DEFAULT_WEAPON_COOLDOWN * 3,
            0, DEFAULT_WEAPON_HIT_CHANCE),

    LAWBRINGER("Lawbringer", DEFAULT_WEAPON_COST * 5, DEFAULT_WEAPON_DAMAGE * 4, DEFAULT_WEAPON_COOLDOWN / 2,
            DEFAULT_WEAPON_CRIT_CHANCE, 100),

    SEPR("Super Energetic Pulse Rifle", DEFAULT_WEAPON_COST * 4, DEFAULT_WEAPON_DAMAGE * 5, DEFAULT_WEAPON_COOLDOWN,
            0, DEFAULT_WEAPON_HIT_CHANCE),

    GATLING("Gatling", DEFAULT_WEAPON_COST * 2, DEFAULT_WEAPON_DAMAGE, DEFAULT_WEAPON_COOLDOWN / 4,
            DEFAULT_WEAPON_CRIT_CHANCE, DEFAULT_WEAPON_HIT_CHANCE / 2),

    BOOM("Boom Box", DEFAULT_WEAPON_COST, DEFAULT_WEAPON_DAMAGE * 2, DEFAULT_WEAPON_COOLDOWN / 2,
            0, DEFAULT_WEAPON_HIT_CHANCE / 3),

    MORTAR("Mortar", DEFAULT_WEAPON_COST, DEFAULT_WEAPON_DAMAGE / 2, DEFAULT_WEAPON_COOLDOWN / 2,
            DEFAULT_WEAPON_CRIT_CHANCE * 6, DEFAULT_WEAPON_HIT_CHANCE),

    TREB("Trebuchet", DEFAULT_WEAPON_COST / 2, DEFAULT_WEAPON_DAMAGE * 2, DEFAULT_WEAPON_COOLDOWN * 4,
            DEFAULT_WEAPON_CRIT_CHANCE, DEFAULT_WEAPON_HIT_CHANCE),

    WIN("Winstrike", DEFAULT_WEAPON_COST * 10, DEFAULT_WEAPON_DAMAGE * 6, DEFAULT_WEAPON_COOLDOWN * 2,
            DEFAULT_WEAPON_CRIT_CHANCE * 2, DEFAULT_WEAPON_HIT_CHANCE),

    CRITTER("Crit Cannon", DEFAULT_WEAPON_COST, DEFAULT_WEAPON_DAMAGE / 2, DEFAULT_WEAPON_COOLDOWN,
            DEFAULT_WEAPON_CRIT_CHANCE * 3, DEFAULT_WEAPON_HIT_CHANCE),

    WATER("Water Cannon", DEFAULT_WEAPON_COST / 3, DEFAULT_WEAPON_DAMAGE / 3, DEFAULT_WEAPON_COOLDOWN / 3,
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
