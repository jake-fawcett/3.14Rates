package combat.items;

public enum WeaponBank {
    //TODO Create weapons
    A(new Weapon("Storm Bringer", 200, 50, 100, 0.2,
            0.9)),
    B(new Weapon("Storm Bringer", 200, 50, 100, 0.2,
            0.9)),
    C(new Weapon("Storm Bringer", 200, 50, 100, 0.2,
            0.9)),
    D(new Weapon("Storm Bringer", 200, 50, 100, 0.2,
            0.9));

    private Weapon weapon;

    public Weapon getWeapon() {
        return new Weapon(weapon.getName(), weapon.getCost(), weapon.getBaseDamage(), weapon.getBaseCooldown(),
                weapon.getBaseCritChance(), weapon.getBaseChanceToHit());
    }

    WeaponBank(Weapon weapon) {
        this.weapon = weapon;
    }
    }
