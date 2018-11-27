package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
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
//        FIXME Write me once we have decided on the final rooms
    return new ArrayList<Room>();
    }

    private List<Weapon> createSampleWeapons(){
        List<Weapon> weapons = new ArrayList<Weapon>();
        weapons.add(new Weapon("Weapon1", 50, 15, 2000, 0.05,
                0.8, 0));
        weapons.add(new Weapon("Weapon2", 50, 15, 2000, 0.05,
                0.8, 0));
        weapons.add(new Weapon("Weapon3", 10, 3, 1500, 0.05,
                0.8, 0));
        weapons.add(new Weapon("Weapon4", 100, 30, 4000, 0.05,
                0.9, 0));

        return new ArrayList<Weapon>();
    }

    @Test
    public void takeTurn() {

    }
}