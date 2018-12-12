package location;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import game_manager.GameManager;
import org.junit.Before;
import org.junit.Test;
import other.Resource;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static testing_tools.SampleObjects.*;

public class DepartmentTest {
    //FIXME Scott working here

    private Department tester;
    private Ship testShip;
    private GameManager testGM;


    @Before
    public void setUp() {
        tester = new Department(createSampleWeapons(2), createSampleUpgradeStock(1),
                createSampleResourceStock(1));
        testShip = createSampleShip(0);
        testGM = createSampleGameManager(1);
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
        assertFalse("Correct weapon should be removed from stock", tester.getWeaponStock().contains(buying));
        assertTrue("Correct weapon should be added to ship", testShip.getWeapons().contains(buying));
    }

    @Test
    public void buyUpgradeMovesUpgrade() {
        RoomUpgrade buying = tester.getUpgradeStock().get(0);
        tester.buyRoomUpgrade(testShip, 0);

        assertFalse("Upgrade should be removed from stock", tester.getUpgradeStock().contains(buying));
        assertTrue("Upgrade should be added to ship",
                Arrays.asList(testShip.getRoom(RoomFunction.CROWS_NEST).getUpgrades()).contains(buying));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBuyWeaponThatYouCantAfford() {
        assertTrue("For this test to work you must have less gold than the price of the weapon we are " +
                "testing with (1000000)", testGM.getGold() < tester.getWeaponStock().get(4).getCost());
        tester.buyWeapon(testShip, 4);
    }

    @Test
    public void buyUpgradeBuysCorrectUpgrade() {
        assertTrue("For this test to work upgrade stock must be at least of length 5",
                tester.getUpgradeStock().size() >= 5);
        RoomUpgrade buying = tester.getUpgradeStock().get(2);
        tester.buyRoomUpgrade(testShip, 2);

        assertFalse("Correct upgrade should be removed from stock", tester.getUpgradeStock().contains(buying));
        assertTrue("Correct upgrade should be added to ship",
                Arrays.asList(testShip.getRoom(RoomFunction.CROWS_NEST).getUpgrades()).contains(buying));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBuyUpgradeThatYouCantAfford() {
        assertTrue("For this test to work you must have less gold than the price of the upgrade we are " +
                "testing with (1000000)", testGM.getGold() < tester.getUpgradeStock().get(4).getCost());
        tester.buyRoomUpgrade(testShip, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyResourceGoldReturnsError() {
//        Since you cannot buy gold an error should be thrown saying that you are trying to buy gold.
        tester.buyResource(testGM, Resource.GOLD, 10);
    }

    @Test
    public void buyResourceFood() {
        Map<Resource, Integer> resourceStock = tester.getResourceStock();
        int goldBefore = testGM.getGold();
        int foodBefore = testGM.getFood();
        tester.buyResource(testGM, Resource.FOOD, 5);
        assertEquals("Resource stock should not change since resources are not consumed from shop",
                resourceStock, tester.getResourceStock());
        assertEquals("Food should be added to game manager's food count", foodBefore + 5,
                testGM.getFood());
        assertEquals("Gold should be deducted from game manager's gold count",
                goldBefore - (5 * resourceStock.get(Resource.FOOD)), testGM.getGold());
    }

    @Test
    public void buyResourceCrew() {
        Map<Resource, Integer> resourceStock = tester.getResourceStock();
        int goldBefore = testGM.getGold();
        int crewBefore = testGM.getPlayerShip().getCrew();
        tester.buyResource(testGM, Resource.CREW, 5);
        assertEquals("Resource stock should not change since resources are not consumed from shop",
                resourceStock, tester.getResourceStock());
        assertEquals("Crew should be added to ship's crew count", crewBefore + 5,
                testGM.getPlayerShip().getCrew());
        assertEquals("Gold should be deducted from game manager's gold count",
                goldBefore - (5 * resourceStock.get(Resource.CREW)), testGM.getGold());
    }

    @Test(expected = IllegalStateException.class)
    public void cantBuyMoreResourceThanCanAfford() {
//    You should not be able to buy more of a resource than you can afford
        int canAfford = (testGM.getGold() / tester.getResourceStock().get(Resource.CREW));
        tester.buyResource(testGM, Resource.CREW, canAfford + 10);
    }
}