package combat.ship;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import testing_tools.SampleObjects;

import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

import static org.junit.Assert.*;

public class ShipTest {
    private Ship tester;

    @Before
    public void setUp() {
        tester = SampleObjects.createSampleShip(1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getRoomThrowsIllegalArgument(){
        tester.getRoom(RoomFunction.GUN_DECK);
    }

    @Test
    public void getRoom() {
        assertEquals("getRoom(RoomFunction.CREW_QUARTERS) should return a room",
                Room.class, tester.getRoom(RoomFunction.CROWS_NEST).getClass());
        assertEquals("getRoom(RoomFunction.CREW_QUARTERS) should return a room whose function is crows nest",
                RoomFunction.CROWS_NEST, tester.getRoom(RoomFunction.CROWS_NEST).getFunction());
    }

    @Test
    public void damage() {
        tester.damage(50);
        assertEquals("HP should be reduced by 50", tester.getBaseHullHP() - 50,
                tester.getHullHP());
        tester.damage(tester.getBaseHullHP());
        assertEquals("HP should not be allowed to go below 0", 0, tester.getHullHP());
    }

    @Test
    public void repair() {
        tester.damage(tester.getBaseHullHP() - 1);
        assertEquals("HP should be 1 before these tests run", 1, tester.getHullHP());
        tester.repair(50);
        assertEquals("HP should be raised to 51", 51, tester.getHullHP());
        tester.repair(tester.getBaseHullHP());
        assertEquals("HP should not be allowed to go above max", tester.getBaseHullHP(),
                tester.getHullHP());
    }

    @Ignore
    @Test
    public void addWeapon() {
        Weapon weapon = new Weapon("Weapon to add", 5, 5, 5, 0.1,
                0.1);
        tester.addWeapon(weapon);
        assertTrue("Weapon should be added to weapons", tester.getWeapons().contains(weapon));
    }

    @Ignore
    @Test
    public void addUpgrade() {
        RoomUpgrade upgrade = new RoomUpgrade("up", 1, 0.1, RoomFunction.CREW_QUARTERS);
        tester.addUpgrade(upgrade);
        assertTrue("Upgrade should be added to appropriate room", Arrays.asList(tester.getRoom(
                RoomFunction.CREW_QUARTERS).getUpgrades()).contains(upgrade));
        assertFalse("Upgrade should not be added to any other room", Arrays.asList(tester.getRoom(
                RoomFunction.CROWS_NEST).getUpgrades()).contains(upgrade));
    }
}