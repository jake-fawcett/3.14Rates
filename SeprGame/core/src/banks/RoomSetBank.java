package banks;

import combat.items.RoomUpgrade;
import combat.ship.Room;
import combat.ship.RoomFunction;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.DEFAULT_ROOM_HP;

public enum RoomSetBank {
    TEST_SET(new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.CROWS_NEST),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.HELM),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.GUN_DECK),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.CREW_QUARTERS),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.NON_FUNCTIONAL),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.NON_FUNCTIONAL));

    private Room room1;
    private Room room2;
    private Room room3;
    private Room room4;
    private Room room5;
    private Room room6;

    RoomSetBank(Room room1, Room room2, Room room3, Room room4, Room room5, Room room6) {
        this.room1 = room1;
        this.room2 = room2;
        this.room3 = room3;
        this.room4 = room4;
        this.room5 = room5;
        this.room6 = room6;
    }

    public List<Room> getRoomList() {
        List<Room> out = new ArrayList<Room>();
        out.add(room1);
        out.add(room2);
        out.add(room3);
        out.add(room4);
        out.add(room5);
        out.add(room6);
        return out;
    }
}
