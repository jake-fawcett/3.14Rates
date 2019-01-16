package location;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.RoomFunction;
import game_manager.GameManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import other.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static other.Constants.STORE_SELL_PRICE_MULTIPLIER;
import static testing_tools.SampleObjects.*;

public class DepartmentTest {
    private Department tester;
    private GameManager testGM;


    @Before
    public void setUp() {
        testGM = createSampleGameManager(1);
        tester = new Department(createSampleWeapons(2), createSampleUpgradeStock(1),
                createSampleResourceStock(1), testGM);
    }

    @Test public void buyWeaponBuysWeapon() {
        Weapon buying = tester.getWeaponStock().get(0);
        tester.buyWeapon(buying);
        int goldBefore = testGM.getGold();
        assertFalse("Weapon should be removed from stock", tester.getWeaponStock().contains(buying));
        assertTrue("Weapon should be added to ship", testGM.getPlayerShip().getWeapons().contains(buying));
        assertEquals("Gold should be deducted when a weapon is bought", goldBefore - buying.getCost(),
                testGM.getGold());
    }

    @Test public void buyWeaponBuysCorrectWeapon() {
        assertTrue("For this test to work weapon stock must be at least of length 5",
                tester.getWeaponStock().size() >= 5);
        Weapon buying = tester.getWeaponStock().get(2);
        tester.buyWeapon(buying);
        assertFalse("Correct weapon should be removed from stock", tester.getWeaponStock().contains(buying));
        assertTrue("Correct weapon should be added to ship", testGM.getPlayerShip().getWeapons().contains(buying));
    }

    @Test public void buyUpgradeBuysUpgrade() {
        RoomUpgrade buying = tester.getUpgradeStock().get(0);
        tester.buyRoomUpgrade(buying);
        int goldBefore = testGM.getGold();

        assertFalse("Upgrade should be removed from stock", tester.getUpgradeStock().contains(buying));
        assertTrue("Upgrade should be added to ship",
                Arrays.asList(testGM.getPlayerShip().getRoom(RoomFunction.CROWS_NEST).getUpgrades()).contains(buying));
        assertEquals("Gold should be deducted when an upgrade is bought",
                goldBefore - buying.getCost(), testGM.getGold());
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBuyWeaponThatYouCantAfford() {
        Weapon buying = tester.getWeaponStock().get(3);
        assertTrue("For this test to work you must have less gold than the price of the weapon we are " +
                "testing with (1000000)", testGM.getGold() < buying.getCost());
        tester.buyWeapon(buying);
    }

    @Test
    public void buyUpgradeBuysCorrectUpgrade() {
        assertTrue("For this test to work upgrade stock must be at least of length 5",
                tester.getUpgradeStock().size() >= 5);
        RoomUpgrade buying = tester.getUpgradeStock().get(2);
        tester.buyRoomUpgrade(buying);

        assertFalse("Correct upgrade should be removed from stock", tester.getUpgradeStock().contains(buying));
        assertTrue("Correct upgrade should be added to ship",
                Arrays.asList(testGM.getPlayerShip().getRoom(RoomFunction.CROWS_NEST).getUpgrades()).contains(buying));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBuyUpgradeThatYouCantAfford() {
        RoomUpgrade buying = tester.getUpgradeStock().get(4);
        assertTrue("For this test to work you must have less gold than the price of the upgrade we are " +
                "testing with (1000000)", testGM.getGold() < buying.getCost());
        tester.buyRoomUpgrade(buying);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyResourceGoldReturnsError() {
//        Since you cannot buy gold an error should be thrown saying that you are trying to buy gold.
        tester.buyResource(Resource.GOLD, 10);
    }

    @Test
    public void buyResourceFood() {
        Map<Resource, Integer> resourceStock = tester.getResourceStock();
        int goldBefore = testGM.getGold();
        int foodBefore = testGM.getFood();
        tester.buyResource(Resource.FOOD, 5);
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
        tester.buyResource(Resource.CREW, 5);
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
        tester.buyResource(Resource.CREW, canAfford + 10);
    }

    @Test
    public void sellWeapon() {
        Weapon selling = new Weapon("Test Weapon", 100, 10, 4000,
                0.5, 0.5);
        testGM.getPlayerShip().addWeapon(selling);
        int goldBefore = testGM.getGold();
        List<Weapon> shipWeaponsBefore = testGM.getPlayerShip().getWeapons();

        assertTrue("Ship should start with the weapon in its weapon list for these tests to work properly",
                shipWeaponsBefore.contains(selling));

        tester.sellWeapon(selling);

        assertTrue("Weapon should be put into stock", tester.getWeaponStock().contains(selling));
        assertFalse("Weapon should be removed from ship weapons",
                testGM.getPlayerShip().getWeapons().contains(selling));
        assertEquals("Gold should be paid in relation to value of item and store price multiplier",
                goldBefore + Math.round(selling.getCost() * STORE_SELL_PRICE_MULTIPLIER),
                testGM.getGold());
    }

    @Test
    public void sellUpgrade() {
        RoomUpgrade selling = new RoomUpgrade("Test upgrade", 100, 1.75,
                RoomFunction.CREW_QUARTERS);
        testGM.getPlayerShip().addUpgrade(selling);
        int goldBefore = testGM.getGold();
        RoomUpgrade[] roomUpgradesBefore = testGM.getPlayerShip().getRoom(RoomFunction.CREW_QUARTERS).getUpgrades();

        assertTrue("Ship should start with the upgrade in its upgrade list for these tests to work properly",
                Arrays.asList(roomUpgradesBefore).contains(selling));

        tester.sellUpgrade(selling);

        assertTrue("Upgrade should be put into stock", tester.getUpgradeStock().contains(selling));
        assertFalse("Upgrade should be removed from ship upgrades",
                Arrays.asList(testGM.getPlayerShip().getRoom(RoomFunction.CREW_QUARTERS).getUpgrades())
                        .contains(selling));
        assertEquals("Gold should be paid in relation to value of item and store price multiplier",
                goldBefore + Math.round(selling.getCost() * STORE_SELL_PRICE_MULTIPLIER),
                testGM.getGold());
    }
}