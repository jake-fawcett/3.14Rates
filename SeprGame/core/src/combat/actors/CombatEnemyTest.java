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
    CombatEnemy tester;

    @Before
    public void setUp() throws Exception {
        // TODO Scott working here
        tester = new CombatEnemy(createSampleShip());
    }

    @After
    public void tearDown() throws Exception {

    }

    private Ship createSampleShip(){
        int crew = 5;
        List<Room> rooms = createSampleRooms();
        List<Weapon> weapons = createSampleWeapons();
        int baseHP = 100;
        int currentHP = baseHP;

        Ship myShip = new Ship(crew, rooms, weapons, baseHP, currentHP);
        return myShip;
    }

    private List<Room> createSampleRooms(){
//        FIXME Write me once we have decided on the final rooms
    return new ArrayList<Room>();
    }

    private List<Weapon> createSampleWeapons(){
//        FIXME Write me
        return new ArrayList<Weapon>();
    }

    @Test
    public void takeTurn() {

    }
}