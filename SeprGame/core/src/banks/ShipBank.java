package banks;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public enum ShipBank {
    //TODO Create Ships
    //TODO replace new lists below with actual pre-made lists of rooms and weapons.
    STARTER_SHIP(20, new ArrayList<Room>(), new ArrayList<Weapon>(), 100);

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
