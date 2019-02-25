package banks;

import combat.items.Weapon;
import combat.ship.Room;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WeaponSetBankTest {

    @Before
    public void testNotEmpty() {
        assertFalse("These tests will not work with an empty WeaponSetBank",
                WeaponSetBank.values().length == 0);
    }

    @Test
    public void getWeaponListGetsWeaponList() {
        List<Weapon> weaponList = WeaponSetBank.STARTER_WEAPONS.getWeaponList();
        assertEquals("You should be returned a list", java.util.ArrayList.class, weaponList.getClass());
        assertFalse("The list should not be empty", weaponList.size() == 0);
        assertEquals("You should be returned a list of weapons", Weapon.class, weaponList.get(0).getClass());
    }

    @Test
    public void getWeaponListCreatesNewInstances() {
        List<Weapon> weaponList = WeaponSetBank.STARTER_WEAPONS.getWeaponList();
        List<Weapon> weaponList2 = WeaponSetBank.STARTER_WEAPONS.getWeaponList();
        assertFalse("The list should not be empty", weaponList.size() == 0);
        weaponList.get(0).fire();
        assertNotEquals("Rooms created from the same set should still be independent.",
                weaponList.get(0).getCurrentCooldown(),
                weaponList2.get(0).getCurrentCooldown());
    }

}