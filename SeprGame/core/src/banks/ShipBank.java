package banks;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.DEFAULT_SHIP_CREW;
import static other.Constants.DEFAULT_SHIP_HP;

public enum ShipBank {
    //TODO replace new lists below with actual pre-made lists of rooms and weapons.
    STARTER_SHIP(DEFAULT_SHIP_CREW, RoomSetBank.STARTER_ROOMS.getRoomList(), new ArrayList<Weapon>(),
            (int) (DEFAULT_SHIP_HP * 0.75)),
    DEFAULT_BRIG(DEFAULT_SHIP_CREW, RoomSetBank.STARTER_ROOMS.getRoomList(), new ArrayList<Weapon>(), DEFAULT_SHIP_HP);

    private int crew;
    private List<Room> rooms;
    private List<Weapon> weapons;
    private int baseHullHP;

    ShipBank(int crew, List<Room> rooms, List<Weapon> weapons, int baseHullHP) {
        this.crew = crew;
        this.rooms = rooms;
        this.weapons = weapons;
        this.baseHullHP = baseHullHP;
    }

    public Ship getShip() {
        return new Ship(crew, rooms, weapons, baseHullHP);
    }
}
