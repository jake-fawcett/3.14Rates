package combat.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeaponTest {
    private Weapon tester;

    @Before
    public void setUp() {
        tester = WeaponBank.values()[0].getWeapon();
    }

    @Test
    public void fire() {
        fail();
    }
}
