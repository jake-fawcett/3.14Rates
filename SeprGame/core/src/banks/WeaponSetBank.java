package banks;

import combat.items.Weapon;

import java.util.ArrayList;
import java.util.List;

import static banks.WeaponBank.*;

public enum WeaponSetBank {
    STARTER_WEAPONS(STARTER_WEAPON, null, null, null),
    MED_1(STORM, STORM, null, null);

    private WeaponBank w1;
    private WeaponBank w2;
    private WeaponBank w3;
    private WeaponBank w4;

    WeaponSetBank(WeaponBank w1, WeaponBank w2, WeaponBank w3, WeaponBank w4) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }

    public List<Weapon> getWeaponList() {
        List<Weapon> out = new ArrayList<Weapon>();
        WeaponBank[] weapons = {w1, w2, w3, w4};

        for (WeaponBank w : weapons) {
            if (w != null) {
                Weapon copying = w.getWeapon();
                out.add(w.getWeapon());
            } else {
                out.add(null);
            }
        }

        return out;
    }
}
