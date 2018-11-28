package combat.actors;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CombatEnemyTest {
    private CombatEnemy tester;

    @Before
    public void setUp() {
        // TODO Scott working here
        tester = new CombatEnemy(createSampleShip());
    }

    @After
    public void tearDown() {

    }

    private Ship createSampleShip(){
        int crew = 5;
        List<Room> rooms = createSampleRooms();
        List<Weapon> weapons = createSampleWeapons();
        int baseHP = 100;
        int currentHP = 100;

        return new Ship(crew, rooms, weapons, baseHP, currentHP);
    }

    private List<Room> createSampleRooms(){
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(new Room(1.0, 100, 100, createSampleUpgrades(RoomFunction.HELM, 0),
                RoomFunction.HELM));
        rooms.add(new Room(1.0, 150, 100, createSampleUpgrades(RoomFunction.CROWS_NEST,0),
                RoomFunction.CROWS_NEST));
        rooms.add(new Room(1.0, 120, 100, createSampleUpgrades(RoomFunction.GUN_DECK,0),
                RoomFunction.GUN_DECK));
        rooms.add(new Room(1.0, 150, 100,
                createSampleUpgrades(RoomFunction.CREW_QUARTERS,0), RoomFunction.CREW_QUARTERS));
        rooms.add(new Room(1.0, 200, 100,
                createSampleUpgrades(RoomFunction.NON_FUNCTIONAL,0), RoomFunction.NON_FUNCTIONAL));
        rooms.add(new Room(1.0, 200, 100,
                createSampleUpgrades(RoomFunction.NON_FUNCTIONAL,0), RoomFunction.NON_FUNCTIONAL));


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
        switch (set){
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

    private List<Weapon> createSampleWeapons(){
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

    @Test
    public void takeTurn() {
        fail("Test not yet implemented");
    }
}