package combat.items;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeaponBankTest {

    @Before
    public void testNotEmpty(){
        assertFalse("These tests will not work with an empty WeaponBank",
                WeaponBank.values().length == 0);
    }

    @Test
    public void getWeaponGetsWeapon(){
        Weapon myWeapon = WeaponBank.values()[0].getWeapon();
        assertEquals("You should be able to get a weapon from the WeaponBank", Weapon.class,
                myWeapon.getClass());
    }

    @Test
    public void getWeaponCreatesNewInstances() {
    Weapon w1 = WeaponBank.values()[0].getWeapon();
    Weapon w2 = WeaponBank.values()[0].getWeapon();
    w1.fire();
    assertNotEquals("Weapons created from the same id should still be independent.", w1.getCurrentCooldown(),
            w2.getCurrentCooldown());
    }
}