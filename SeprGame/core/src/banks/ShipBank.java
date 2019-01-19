package banks;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.DEFAULT_SHIP_CREW;
import static other.Constants.DEFAULT_SHIP_HP;

/**
 * A bank of ships to be used as the player ship or as opponents in a battle
 */
public enum ShipBank {
    //NEW_SHIP_TEMPLATE(crew, list of rooms, list of weapons, hp),
    STARTER_SHIP(DEFAULT_SHIP_CREW, RoomSetBank.STARTER_ROOMS.getRoomList(),
            WeaponSetBank.STARTER_WEAPONS.getWeaponList(), (int) (DEFAULT_SHIP_HP * 0.75)),
    DEFAULT_BRIG(DEFAULT_SHIP_CREW, RoomSetBank.STARTER_ROOMS.getRoomList(), new ArrayList<Weapon>(), DEFAULT_SHIP_HP);

    // Internal workings of the enum
    private int crew;
    private List<Room> rooms;
    private List<Weapon> weapons;
    private int baseHullHP;

    // Internal workings of the enum
    ShipBank(int crew, List<Room> rooms, List<Weapon> weapons, int baseHullHP) {
        this.crew = crew;
        this.rooms = rooms;
        this.weapons = weapons;
        this.baseHullHP = baseHullHP;
    }

    /**
     * @return An instance of a ship
     */
    public Ship getShip() {
//        The weird copying and loops in this method is necessary to avoid having two individual ships sharing the same
//        parameters (e.g. if you fire a cannon one one ship that same cannon will be on cooldown for every other ship
//        instantiated from the same entry in the bank. This method ensures that they are unique.
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
            if (w != null) {
                weaponsOut.add(new Weapon(w.getName(), w.getCost(), w.getBaseDamage(), w.getCooldown(), w.getCritChance(), w.getAccuracy()));
            }
        }
        return new Ship(crew, roomsOut, weaponsOut, baseHullHP);
    }
}
