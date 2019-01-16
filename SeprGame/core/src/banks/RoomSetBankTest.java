package banks;

import combat.ship.Room;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RoomSetBankTest {

    @Before
    public void testNotEmpty() {
        assertFalse("These tests will not work with an empty RoomSetBank",
                RoomSetBank.values().length == 0);
    }

    @Test
    public void getRoomListGetsRoomList() {
        List<Room> roomList = RoomSetBank.TEST_SET.getRoomList();
        assertEquals("You should be returned a list", java.util.ArrayList.class, roomList.getClass());
        assertFalse("The list should not be empty", roomList.size() == 0);
        assertEquals("You should be returned a list of rooms", Room.class, roomList.get(0).getClass());
    }

    @Test
    public void getRoomListCreatesNewInstances() {
        List<Room> roomList = RoomSetBank.TEST_SET.getRoomList();
        List<Room> roomList2 = RoomSetBank.TEST_SET.getRoomList();
        assertFalse("The list should not be empty", roomList.size() == 0);
        Room r1 = roomList.get(0);
        Room r2 = roomList2.get(0);
        System.out.println(r1.getHp());
        System.out.println(r2.getHp());
        r1.damage(1);
        System.out.println(r1.getHp());
        System.out.println(r2.getHp());

        assertNotEquals("Rooms created from the same set should still be independent.", r1.getHp(), r2.getHp());
    }

}