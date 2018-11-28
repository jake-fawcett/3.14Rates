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
    public void cooldownStartsAt0(){
        assertEquals("Cooldown should start at 0", tester.getCurrentCooldown(), 0);
    }

    @Test
    public void fireResetsCooldown() {
        tester.fire();
        assertEquals("Cooldown should be reset to base after fire", tester.getCurrentCooldown(),
                tester.getBaseCooldown());
    }
}
