package banks;

import combat.items.RoomUpgrade;
import combat.ship.Room;
import combat.ship.RoomFunction;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.DEFAULT_ROOM_HP;

/**
 * A set of possible rooms for ships.
 */
public enum RoomSetBank {
    //NEW_ROOM_SET_TEMPLATE(room1, room2, room3, ... room8)
    STARTER_ROOMS(new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.CROWS_NEST),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.HELM),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.GUN_DECK),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.CREW_QUARTERS),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.NON_FUNCTIONAL),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.NON_FUNCTIONAL),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.NON_FUNCTIONAL),
            new Room(DEFAULT_ROOM_HP, DEFAULT_ROOM_HP, new RoomUpgrade[3], RoomFunction.NON_FUNCTIONAL));

    // Internal workings of the enum
    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;
    private Room r5;
    private Room r6;
    private Room r7;
    private Room r8;

    // Internal workings of the enum
    RoomSetBank(Room r1, Room r2, Room r3, Room r4, Room r5, Room r6, Room r7, Room r8) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.r5 = r5;
        this.r6 = r6;
        this.r7 = r7;
        this.r8 = r8;
    }

    /**
     * Called when you want an instance from the enum.
     * @return A list of rooms which can be used in a ship.
     */
    public List<Room> getRoomList() {
        List<Room> out = new ArrayList<Room>();
        Room[] rooms = new Room[]{r1, r2, r3, r4, r5, r6, r7, r8};

        for (Room r : rooms) {
            out.add(new Room(r.getBaseHP(), r.getHp(), r.getUpgrades(), r.getFunction()));
        }

        return out;
    }
}
