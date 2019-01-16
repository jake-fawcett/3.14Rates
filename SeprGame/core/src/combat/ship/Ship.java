package combat.ship;

import combat.items.RoomUpgrade;
import combat.items.Weapon;

import java.util.Hashtable;
import java.util.List;

import static other.Constants.BASE_SHIP_ACCURACY;
import static other.Constants.BASE_SHIP_EVADE;

@SuppressWarnings("ALL")
public class Ship {
    private int crew;
    private List<Room> rooms;
    private List<Weapon> weapons;
    private int baseHullHP;
    private int hullHP;

    public Ship(int crew, List<Room> rooms, List<Weapon> weapons, int baseHullHP, int hullHP) {
        this.crew = crew;
        this.rooms = rooms;
        this.weapons = weapons;
        this.baseHullHP = baseHullHP;
        this.hullHP = hullHP;
    }

    public Ship(int crew, List<Room> rooms, List<Weapon> weapons, int baseHullHP) {
        this.crew = crew;
        this.rooms = rooms;
        this.weapons = weapons;
        this.baseHullHP = baseHullHP;
        this.hullHP = baseHullHP;
    }

    public int getCrew() {
        return crew;
    }

    /**
     * Gets a room of a given function in a ship
     *
     * @param function The function of the room you want to get
     * @return The room on that ship with that function.
     * @throws IllegalArgumentException when the room does not exist.
     */
    public Room getRoom(RoomFunction function) {
        for (Room room : rooms) {
            if (room.getFunction() == function) {
                return room;
            }
        }
        throw new IllegalArgumentException("A room with this function does not exist on this ship.");
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public int getBaseHullHP() {
        return baseHullHP;
    }

    public int getHullHP() {
        return hullHP;
    }

    public void damage(int damage) {
        hullHP -= damage;
        if (hullHP < 0) {
            hullHP = 0;
        }
    }

    public void repair(int damage) {
        hullHP += damage;
        if (hullHP > baseHullHP) {
            hullHP = baseHullHP;
        }
    }

    public void addWeapon(Weapon weapon) {
        if (weapons.size() > 4) {
            throw new IllegalStateException("Weapon Slots full");
        } else {
            weapons.add(weapon);
        }
    }

    public void addUpgrade(RoomUpgrade upgrade) {
        try {
            this.getRoom(upgrade.getAffectsRoom()).addUpgrade(upgrade);

        } catch (IllegalStateException ex) {
            if (ex.getMessage().equals("Room Upgrades full")) {
                /* TODO Write catch so that a message is displayed on the screen warning you if room upgrades are full.
                     - This catch should somehow warn the player that they have to get rid of one upgrade and make them
                     choose which. STORM test also needs to be written for this once its done*/
                throw ex;
            }
        }
    }

    public void addCrew(int amount) {
        crew += amount;
    }

    public double calculateShipAccuracy() {
        return BASE_SHIP_ACCURACY * this.getRoom(RoomFunction.CROWS_NEST).getMultiplier();
    }

    public double calculateShipEvade() {
        return BASE_SHIP_EVADE * this.getRoom(RoomFunction.HELM).getMultiplier();
    }

    public boolean hasUpgrade(RoomUpgrade upgrade) {
        Room room = getRoom(upgrade.getAffectsRoom());
        for (RoomUpgrade i : room.getUpgrades()) {
            if (upgrade == i) {
                return true;
            }
        }
        return false;
    }

    public void delUpgrade(RoomUpgrade upgrade) {
        if (this.hasUpgrade(upgrade)) {
            getRoom(upgrade.getAffectsRoom()).delUpgrade(upgrade);
        } else {
            throw new IllegalArgumentException("Cannot delete upgrade that you do not have");
        }
    }
}