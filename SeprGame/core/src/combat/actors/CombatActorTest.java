package combat.actors;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CombatActorTest {
    private CombatEnemy enemyTest;
    private CombatPlayer playerTest;

    @Before
    public void setUp() {
        enemyTest = new CombatEnemy(createSampleShip());
        playerTest = new CombatPlayer(createSampleShip());
    }

    private Ship createSampleShip() {
        int crew = 5;
        List<Room> rooms = createSampleRooms();
        List<Weapon> weapons = createSampleWeapons();
        int baseHP = 100;
        int currentHP = 100;

        return new Ship(crew, rooms, weapons, baseHP, currentHP);
    }

    private List<Room> createSampleRooms() {
        List<Room> rooms = new ArrayList<Room>();
        RoomFunction func;

        func = RoomFunction.HELM;
        rooms.add(new Room(100, 100, createSampleUpgrades(func, 0), func));
        func = RoomFunction.CROWS_NEST;
        rooms.add(new Room(150, 100, createSampleUpgrades(func, 0), func));
        func = RoomFunction.GUN_DECK;
        rooms.add(new Room(120, 100, createSampleUpgrades(func, 0), func));
        func = RoomFunction.CREW_QUARTERS;
        rooms.add(new Room(150, 100, createSampleUpgrades(func, 0), func));
        func = RoomFunction.NON_FUNCTIONAL;
        rooms.add(new Room(200, 100, createSampleUpgrades(func, 0), func));
        func = RoomFunction.NON_FUNCTIONAL;
        rooms.add(new Room(200, 100, createSampleUpgrades(func, 0), func));

        return new ArrayList<Room>();
    }

    /**
     * Creates a sample upgrade list. Creates one of a set.
     *
     * @param set The set of upgrades that you want to create. See cases in switch statement for possible sets.
     * @return A list of upgrades.
     */
    private RoomUpgrade[] createSampleUpgrades(RoomFunction room, int set) {
        RoomUpgrade[] upgrades = new RoomUpgrade[3];
        switch (set) {
            case 0:
//              Case zero returns no upgrades
                break;
            case 1:
                upgrades[0] = new RoomUpgrade("Upgrade1", 100, 1.5, room);
                upgrades[1] = new RoomUpgrade("Upgrade2", 50, 2, room);
                break;
        }
        return upgrades;
    }

    private List<Weapon> createSampleWeapons() {
        List<Weapon> weapons = new ArrayList<Weapon>();
        weapons.add(new Weapon("Weapon1", 50, 15, 2000, 0.05,
                0.8));
        weapons.add(new Weapon("Weapon2", 50, 15, 2000, 0.05,
                0.8));
        weapons.add(new Weapon("Weapon3", 10, 3, 1500, 0.05,
                0.8));
        weapons.add(new Weapon("Weapon4", 100, 30, 4000, 0.05,
                0.9));

        return new ArrayList<Weapon>();
    }

    private List<Pair<Room, Integer>> createSampleDamageReport(CombatActor actor) {
        List<Pair<Room, Integer>> report = new ArrayList<Pair<Room, Integer>>();
        try {
            report.add(new Pair<Room, Integer>(actor.getShip().getRoom(RoomFunction.HELM), 5));
            report.add(new Pair<Room, Integer>(actor.getShip().getRoom(RoomFunction.GUN_DECK), 10));
        } catch (IllegalArgumentException ex) {
            fail("Failed before test could be run due to error in setup. A room you tried to damage in the damage " +
                    "report does not exist.");
        }
        return report;
    }

    public void testActorTakeTurn(CombatActor actor) {
        //Initial room health
        int helmHP = actor.getShip().getRoom(RoomFunction.HELM).getHp();
        int gunHP = actor.getShip().getRoom(RoomFunction.GUN_DECK).getHp();
        int crowHP = actor.getShip().getRoom(RoomFunction.CROWS_NEST).getHp();
        int crewHP = actor.getShip().getRoom(RoomFunction.CREW_QUARTERS).getHp();

        List<Pair<Room, Weapon>> turnReport = actor.takeTurn(createSampleDamageReport(actor));
        //TODO Test that it outputs the correct thing using turnReport (above)

        // Rooms take damage
        assertEquals("Hit rooms should be damaged", helmHP - 5, actor.getShip().getRoom(
                RoomFunction.HELM).getHp());

        assertEquals("Hit rooms should be damaged", gunHP - 10, actor.getShip().getRoom(
                RoomFunction.GUN_DECK).getHp());

        assertEquals("Non-Hit rooms should not be damaged", crowHP, actor.getShip().getRoom(
                RoomFunction.CROWS_NEST).getHp());

        assertEquals("Non-Hit rooms should not be damaged", crewHP, actor.getShip().getRoom(
                RoomFunction.CREW_QUARTERS).getHp());


    }

    @Test
    @Ignore
    public void testEnemyTakeTurn() {
        testActorTakeTurn(enemyTest);
    }

    @Test
    @Ignore
    public void testPlayerTakeTurn() {
        testActorTakeTurn(playerTest);
    }
}
