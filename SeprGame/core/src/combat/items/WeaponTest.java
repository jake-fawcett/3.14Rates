package combat.items;

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

    @Test
    public void decrementCooldown() {
        tester.decrementCooldown(50);
        assertEquals("decrementCooldown should never lower ticks past 0", 0,
                tester.getCurrentCooldown());
        tester.fire();
        tester.decrementCooldown(50);
        assertEquals("decrementCooldown should lower the cooldown by the given number of ticks",
                tester.getBaseCooldown() - 50, tester.getCurrentCooldown());
    }
}
