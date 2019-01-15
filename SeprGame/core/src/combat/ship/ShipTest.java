package combat.ship;

import banks.ShipBank;
import combat.items.RoomUpgrade;
import combat.items.Weapon;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import testing_tools.SampleObjects;

import java.util.Arrays;

import static org.junit.Assert.*;
import static other.Constants.BASE_SHIP_ACCURACY;

public class ShipTest {
    private Ship tester;
    private Ship tester2;

    @Before
    public void setUp() {
        tester = SampleObjects.createSampleShip(1);
        tester2 = ShipBank.STARTER_SHIP.getShip();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRoomThrowsIllegalArgument() {
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

    @Test
    public void addWeapon() {
        Weapon weapon = new Weapon("Weapon to add", 5, 5, 5, 0.1,
                0.1);
        tester.addWeapon(weapon);
        assertTrue("Weapon should be added to weapons", tester.getWeapons().contains(weapon));
    }

    @Test(expected = IllegalStateException.class)
    public void addWeaponThrowsFull() {
        tester.addWeapon(new Weapon("Weapon to add", 5, 5, 5, 0.1,
                0.1));
        tester.addWeapon(new Weapon("Weapon to add", 5, 5, 5, 0.1,
                0.1));
        tester.addWeapon(new Weapon("Weapon to add", 5, 5, 5, 0.1,
                0.1));
    }

    @Test
    public void addUpgrade() {
        RoomUpgrade upgrade = new RoomUpgrade("up", 1, 0.1, RoomFunction.CREW_QUARTERS);
        tester.addUpgrade(upgrade);
        assertTrue("Upgrade should be added to appropriate room", Arrays.asList(tester.getRoom(
                RoomFunction.CREW_QUARTERS).getUpgrades()).contains(upgrade));
        assertFalse("Upgrade should not be added to any other room", Arrays.asList(tester.getRoom(
                RoomFunction.CROWS_NEST).getUpgrades()).contains(upgrade));
    }

//    TODO write test for adding upgrade once the upgrade slots are all full once the catch has been written (See
//    todo in Ship -> addUpgrade()

    @Test
    public void calculateShipAccuracyBase() {
        assertTrue("These tests rely on the BASE_SHIP_ACCURACY in constants being set to 1. If it is" +
                "changed either set it back or re-work the test.", BASE_SHIP_ACCURACY == 1);

        assertTrue("The plain starter ship with no upgrades is expected to have an accuracy of 1 (Base)",
                1 == tester2.calculateShipAccuracy());
    }

    @Test
    public void calculateShipAccuracyDamaged() {
        assertTrue("These tests rely on the BASE_SHIP_ACCURACY in constants being set to 1. If it is" +
                "changed either set it back or re-work the test.", BASE_SHIP_ACCURACY == 1);

        Room crowsNest = tester2.getRoom(RoomFunction.CROWS_NEST);

        crowsNest.damage(crowsNest.getBaseHP() / 2); // Half total health
        assertTrue("Damage to a room should affect its accuracy proportional to how much damage is taken.",
                0.5 == tester2.calculateShipAccuracy());
        crowsNest.damage(crowsNest.getBaseHP() / 4); // Quarter total health
        assertTrue("Damage to a room should affect its accuracy proportional to how much damage is taken.",
                0.25 == tester2.calculateShipAccuracy());
        crowsNest.damage(crowsNest.getBaseHP()); // 0 health
        assertTrue("Damage to a room should affect its accuracy proportional to how much damage is taken.",
                0 == tester2.calculateShipAccuracy());
    }

    @Test
    public void calculateShipAccuracyUpgrades() {
        assertTrue("These tests rely on the BASE_SHIP_ACCURACY in constants being set to 1. If it is" +
                "changed either set it back or re-work the test.", BASE_SHIP_ACCURACY == 1);

        tester2.addUpgrade(new RoomUpgrade("a", 1, 1.5, RoomFunction.HELM));
        tester2.addUpgrade(new RoomUpgrade("a", 1, 1.5, RoomFunction.GUN_DECK));
        assertTrue("Upgrades added to anything besides crows nest should have no effect on accuracy",
                1 == tester2.calculateShipAccuracy());

        tester2.addUpgrade(new RoomUpgrade("a", 1, 1.5, RoomFunction.CROWS_NEST));
        assertTrue("Upgrades added to the crows nest should affect accuracy",
                1.5 == tester2.calculateShipAccuracy());

//        Once I got to here I realised that this is actually all already tested when testing Room -> getMultipleir()
//        So I am leaving the tests I have already written here, but not doing any more
    }

    @Ignore
    @Test
    public void calculateShipEvade() {

    }

}