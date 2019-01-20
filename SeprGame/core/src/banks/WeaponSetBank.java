package banks;

import combat.items.Weapon;

import java.util.ArrayList;
import java.util.List;

import static banks.WeaponBank.*;

/**
 * A bank of sets of weapons available to ships and departments
 */
public enum WeaponSetBank {
    //NEW_WEAPON_SET_TEMPLATE(w1, w2, w3, w4)
    STARTER_WEAPONS(STARTER_WEAPON, STARTER_WEAPON, STARTER_WEAPON, null),
    MED_1(STORM, STORM, null, null),
    COMP_SCI_WEPS(SEPR, SCATTER, CRITTER,null),
    LMB_WEPS(LAWBRINGER, MORTAR, BOOM, null);

    // Internal workings of the enum
    private WeaponBank w1;
    private WeaponBank w2;
    private WeaponBank w3;
    private WeaponBank w4;

    // Internal workings of the enum
    WeaponSetBank(WeaponBank w1, WeaponBank w2, WeaponBank w3, WeaponBank w4) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }

    /**
     * @return A set of weapons.
     */
    public List<Weapon> getWeaponList() {
//        As with ShipBank, the weird loops are to ensure that weapons created in this way are truly unique.
//        Before this solution we had a problem where you you would fire a weapon and every other weapon instantiated
//        In this way would go on cooldown. This is no longer an issue.
        List<Weapon> out = new ArrayList<Weapon>();
        WeaponBank[] weapons = {w1, w2, w3, w4};

        for (WeaponBank w : weapons) {
            if (w != null) {
                out.add(w.getWeapon());
            }
        }

        return out;
    }
}
