package combat.items;

public enum WeaponBank {
    TEST_WEAPON(new Weapon("Storm Bringer", 200, 50, 100, 0.2,
            0.9));

    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    WeaponBank(Weapon weapon) {
        this.weapon = weapon;
    }
}
