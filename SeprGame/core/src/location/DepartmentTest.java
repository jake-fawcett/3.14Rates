package location;

import combat.items.Weapon;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static testing_tools.SampleObjects.*;

public class DepartmentTest {
    //FIXME Scott working here

    private Department tester;
    private Ship testShip;


    @Before
    public void setUp() {
        tester = new Department(createSampleWeapons(1), createSampleUpgradeStock(2),
                createSampleResourceStock(1));
        testShip = createSampleShip(0);
    }

    @Test
    public void buyWeaponMovesWeapon() {
        Weapon buying = tester.getWeaponStock().get(0);
        tester.buyWeapon(testShip, 0);
        assertFalse("Weapon should be removed from stock", tester.getWeaponStock().contains(buying));
        assertTrue("Weapon should be added to ship", testShip.getWeapons().contains(buying));
    }

    @Test
    public void buyWeaponBuysCorrectWeapon() {
        assertTrue("For this test to work weapon stock must be at least of length 5", 
                tester.getWeaponStock().size() >= 5);
        Weapon buying = tester.getWeaponStock().get(2);
        tester.buyWeapon(testShip, 2);
        assertFalse("Weapon should be removed from stock", tester.getWeaponStock().contains(buying));
        assertTrue("Weapon should be added to ship", testShip.getWeapons().contains(buying));
    }

    @Test
    public void buyRoomUpgrade() {
    }

    @Test
    public void buyResource() {
    }
}