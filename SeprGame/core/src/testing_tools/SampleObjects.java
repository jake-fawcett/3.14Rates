package testing_tools;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import game_manager.GameManager;
import other.Difficulty;
import other.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SampleObjects {
    public static Ship createSampleShip(int set) {
        int crew = 0;
        List<Room> rooms = new ArrayList<Room>();
        List<Weapon> weapons = new ArrayList<Weapon>();
        int baseHP = 0;
        int currentHP = 0;

        switch (set) {
            case 0:
                crew = 5;
                rooms = createSampleRooms(0);
                weapons = createSampleWeapons(1);
                baseHP = 100;
                currentHP = 100;
                break;
            case 1:
                crew = 5;
                rooms = createSampleRooms(1);
                weapons = createSampleWeapons(1);
                baseHP = 100;
                currentHP = 100;
                break;
        }

        return new Ship(crew, rooms, weapons, baseHP, currentHP);
    }

    public static List<Room> createSampleRooms(int set) {
        List<Room> rooms = new ArrayList<Room>();
        RoomFunction func;

        switch (set) {
            case 0:
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
            case 1:
                func = RoomFunction.HELM;
                rooms.add(new Room(100, 100, createSampleUpgrades(func, 0), func));
                func = RoomFunction.CROWS_NEST;
                rooms.add(new Room(150, 100, createSampleUpgrades(func, 0), func));
                func = RoomFunction.CREW_QUARTERS;
                rooms.add(new Room(150, 100, createSampleUpgrades(func, 0), func));
                func = RoomFunction.NON_FUNCTIONAL;
                rooms.add(new Room(200, 100, createSampleUpgrades(func, 0), func));
                func = RoomFunction.NON_FUNCTIONAL;
                rooms.add(new Room(200, 100, createSampleUpgrades(func, 0), func));
        }

        return rooms;
    }

    /**
     * Creates a sample upgrade list. Creates one of a set.
     *
     * @param set The set of upgrades that you want to create. See cases in switch statement for possible sets.
     * @return A list of upgrades.
     */
    public static RoomUpgrade[] createSampleUpgrades(RoomFunction room, int set) {
        RoomUpgrade[] upgrades = new RoomUpgrade[3];
        switch (set) {
            case 0:
//              Case zero returns no upgrades
                break;
            case 1:
                upgrades[0] = new RoomUpgrade("Upgrade1", 100, 1.5, room);
                upgrades[1] = new RoomUpgrade("Upgrade2", 50, 2, room);
                break;
            case 2:
                upgrades[0] = new RoomUpgrade("Upgrade1", 100, 1.5, room);
                upgrades[1] = new RoomUpgrade("Upgrade2", 50, 2, room);
                upgrades[2] = new RoomUpgrade("Upgrade3", 50, 1.25, room);
                break;
        }
        return upgrades;
    }

    public static List<Weapon> createSampleWeapons(int set) {
        List<Weapon> stock = new ArrayList<Weapon>();
        switch (set) {
            case 0:
                //Case 0 returns no stock
                break;
            case 1:
                stock.add(new Weapon("Weapon1", 50, 15, 2000, 0.05,
                        0.8));
                stock.add(new Weapon("Weapon2", 50, 15, 2000, 0.05,
                        0.8));
                stock.add(new Weapon("Weapon3", 10, 3, 1500, 0.05,
                        0.8));
                break;
            case 2:
                stock.add(new Weapon("Weapon1", 5000, 15, 2000, 0.05,
                        0.8));
                stock.add(new Weapon("Weapon2", 5000, 15, 2000, 0.05,
                        0.8));
                stock.add(new Weapon("Weapon3", 5000, 3, 1500, 0.05,
                        0.8));
                stock.add(new Weapon("Weapon4", 5000, 30, 4000, 0.05,
                        0.9));

        }
        return stock;
    }

    public static Map<Resource, Integer> createSampleResourceStock(int set) {
        Map<Resource, Integer> stock = new HashMap<Resource, Integer>();
        switch (set) {
            case 1:
                stock.put(Resource.CREW, 5);
                stock.put(Resource.FOOD, 2);
                break;
        }
        return stock;
    }

    public static List<RoomUpgrade> createSampleUpgradeStock(int set) {
        List<RoomUpgrade> stock = new ArrayList<RoomUpgrade>();
        switch (set) {
            case 0:
//              Case zero returns no upgrades
                break;
            case 1:
                stock.add(new RoomUpgrade("Upgrade1", 100, 1.5, RoomFunction.CROWS_NEST));
                stock.add(new RoomUpgrade("Upgrade2", 150, 1.25, RoomFunction.GUN_DECK));
                stock.add(new RoomUpgrade("Upgrade3", 120, 1.75, RoomFunction.HELM));
                stock.add(new RoomUpgrade("Upgrade4", 150, 1.25, RoomFunction.CROWS_NEST));
                stock.add(new RoomUpgrade("Upgrade5", 1000000, 1.75, RoomFunction.CREW_QUARTERS));
                break;
        }
        return stock;
    }

    public static GameManager createSampleGameManager(int set){
        GameManager gm = new GameManager(null, null);
        switch (set) {
            case 1:
                gm = new GameManager("Test", Difficulty.EASY);
                break;
        }
        return gm;
    }
}
