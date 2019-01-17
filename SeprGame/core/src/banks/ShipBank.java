package banks;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.DEFAULT_SHIP_CREW;
import static other.Constants.DEFAULT_SHIP_HP;

public enum ShipBank {
    STARTER_SHIP(DEFAULT_SHIP_CREW, RoomSetBank.STARTER_ROOMS.getRoomList(),
            WeaponSetBank.STARTER_WEAPONS.getWeaponList(), (int) (DEFAULT_SHIP_HP * 0.75)),
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
        List<Room> roomsOut = new ArrayList<Room>();
        List<Weapon> weaponsOut = new ArrayList<Weapon>();
        for (Room r : rooms) {
            RoomUpgrade[] newUpgrades = new RoomUpgrade[3];
            for (int i = 0; i < 3; i++) {
                if (r.getUpgrades()[i] != null) {
                    newUpgrades[i] = new RoomUpgrade(r.getUpgrades()[i].getName(), r.getUpgrades()[i].getCost(),
                            r.getUpgrades()[i].getMultiplier(), r.getUpgrades()[i].getAffectsRoom());
                }
            }
            roomsOut.add(new Room(r.getBaseHP(), r.getHp(), newUpgrades, r.getFunction()));
        }
        for (Weapon w : weapons) {
            if (w != null)
            weaponsOut.add(w);
        }

        return new Ship(crew, roomsOut, weaponsOut, baseHullHP);
    }
}
