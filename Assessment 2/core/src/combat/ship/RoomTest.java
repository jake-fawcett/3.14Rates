package combat.ship;

import com.sun.deploy.util.ArrayUtil;
import combat.items.RoomUpgrade;
import org.junit.Before;
import org.junit.Test;
import testing_tools.SampleObjects;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RoomTest {

    private Room tester;

    @Before
    public void setUp() {
        tester = new Room(100, 100, new RoomUpgrade[3], RoomFunction.CROWS_NEST);
    }

    @Test
    public void damage() {
        tester = new Room(100, 100, new RoomUpgrade[3], RoomFunction.CROWS_NEST);
        tester.damage(50);
        assertEquals("HP should be 50 after 50 damage taken on a 100HP ship", 50, tester.getHp());
        tester.damage(100);
        assertEquals("HP should be 0 after 150 damage taken on a 100HP ship", 0, tester.getHp());

    }

    @Test
    public void getMultiplierWithNoUpgrades() {
        tester = new Room(100, 100, new RoomUpgrade[3], RoomFunction.CROWS_NEST);
        assertEquals("Initial multiplier should be 1.0", 1.0, tester.getMultiplier(), 0.001);

        tester.damage(25);
        assertEquals("After health is reduced by quarter, multiplier should be 0.75", 0.75,
                tester.getMultiplier(), 0.001);

        tester.damage(25);
        assertEquals("After health is halved, multiplier should be 0.5", 0.5, tester.getMultiplier(),
                0.001);

        tester.damage(50);
        assertEquals("After health is reduced to 0, multiplier should be 0.0", 0.0,
                tester.getMultiplier(), 0.001);
    }

    @Test
    public void getMultiplierWithUpgrades() {
        tester = new Room(100, 100, SampleObjects.createSampleUpgrades(RoomFunction.CROWS_NEST, 2),
                RoomFunction.CROWS_NEST);
        assertEquals("This configuration of upgrades should give this value at full health",
                2.689, tester.getMultiplier(), 0.05);
        tester.damage(tester.getBaseHP() / 2);
        assertEquals("This configuration of upgrades should give this value at half health",
                1.345, tester.getMultiplier(), 0.05);

    }

    @Test
    public void addUpgradeIfThereIsSpace() {
        RoomUpgrade upgrade = new RoomUpgrade("TestUpgrade", 1, 1.2, RoomFunction.CROWS_NEST);
        tester.addUpgrade(upgrade);
        assertTrue("Upgrade should be added", Arrays.asList(tester.getUpgrades()).contains(upgrade));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotAddMoreThanThreeUpgrades() {
        tester.addUpgrade(new RoomUpgrade("TestUpgrade", 1, 1.2, RoomFunction.CROWS_NEST));
        tester.addUpgrade(new RoomUpgrade("TestUpgrade", 1, 1.2, RoomFunction.CROWS_NEST));
        tester.addUpgrade(new RoomUpgrade("TestUpgrade", 1, 1.2, RoomFunction.CROWS_NEST));
        tester.addUpgrade(new RoomUpgrade("TestUpgrade", 1, 1.2, RoomFunction.CROWS_NEST));
    }

    @Test
    public void delUpgrade() {
        RoomUpgrade up1 = new RoomUpgrade("a", 1, 1, RoomFunction.CROWS_NEST);
        RoomUpgrade up2 = new RoomUpgrade("b", 1, 1, RoomFunction.CROWS_NEST);
        RoomUpgrade up3 = new RoomUpgrade("c", 1, 1, RoomFunction.CROWS_NEST);

        tester.addUpgrade(up1);
        tester.addUpgrade(up2);
        tester.addUpgrade(up3);

        assertTrue("Upgrades should be correctly initialised",
                tester.getUpgrades()[0].getName() == "a");
        assertTrue("Upgrades should be correctly initialised",
                tester.getUpgrades()[1].getName() == "b");
        assertTrue("Upgrades should be correctly initialised",
                tester.getUpgrades()[2].getName() == "c");

        tester.delUpgrade(up2);
        assertFalse("Upgrades should be correctly removed", Arrays.asList(tester.getUpgrades()).contains(up2));

        assertTrue("Upgrades should be correctly shifted after deletion",
                tester.getUpgrades()[0].getName() == "a");
        assertTrue("Upgrades should be correctly shifted after deletion",
                tester.getUpgrades()[1].getName() == "c");
        assertTrue("Upgrades should be correctly shifted after deletion",
                tester.getUpgrades()[2] == null);
    }
}