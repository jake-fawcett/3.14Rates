package combat.ship;

import combat.items.RoomUpgrade;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

    private Room myRoom;

    @Before
    public void setUp() {
        myRoom = new Room(100, 100, new RoomUpgrade[3], RoomFunction.CROWS_NEST);
    }

    @Test
    public void damage() {
        myRoom = new Room(100, 100, new RoomUpgrade[3], RoomFunction.CROWS_NEST);
        myRoom.damage(50);
        assertEquals("HP should be 50 after 50 damage taken on a 100HP ship", 50, myRoom.getHp());
        myRoom.damage(100);
        assertEquals("HP should be 0 after 150 damage taken on a 100HP ship", 0, myRoom.getHp());

    }

    @Test
    public void getMultiplier() {
        myRoom = new Room(100, 100, new RoomUpgrade[3], RoomFunction.CROWS_NEST);
        assertEquals("Initial multiplier should be 1.0", 1.0, myRoom.getMultiplier(), 0.001);

        myRoom.damage(25);
        assertEquals("After health is reduced by quarter, multiplier should be 0.75", 0.75,
                myRoom.getMultiplier(), 0.001);

        myRoom.damage(25);
        assertEquals("After health is halved, multiplier should be 0.5", 0.5, myRoom.getMultiplier(),
                0.001);

        myRoom.damage(50);
        assertEquals("After health is reduced to 0, multiplier should be 0.0", 0.0,
                myRoom.getMultiplier(), 0.001);
    }
}