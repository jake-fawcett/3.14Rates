package combat.ship;

import combat.items.RoomUpgrade;
import combat.items.Weapon;

import java.util.Hashtable;
import java.util.List;

import static java.lang.Math.log;
import static other.Constants.BASE_SHIP_ACCURACY;
import static other.Constants.BASE_SHIP_EVADE;
import static other.Constants.BASE_SHIP_REPAIR;

/**
 * The ship which has new weapons or upgrades applied to it. Also the ship which is damaged in combat.
 */
public class Ship {
    /**
     * The number of crew on the ship, affects room repair speed.
     */
    private int crew;

    /**
     * The rooms on the ship
     */
    private List<Room> rooms;

    /**
     * The weapons on the ship
     */
    private List<Weapon> weapons;
    /**
     * The hp of the ship
     */
    private int baseHullHP;
    private int hullHP;
    public Ship(int crew, List<Room> rooms, List<Weapon> weapons, int baseHullHP, int hullHP) {
        this.crew = crew;
        this.rooms = rooms;
        while (weapons.contains(null)) {
            weapons.remove(null);
        }
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

    public List<Room> getRooms() {
        return rooms;
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

    /**
     * Damage the hull of the ship
     * @param damage Amount of damage to be done
     */
    public void damage(int damage) {
        hullHP -= damage;
        if (hullHP < 0) {
            hullHP = 0;
        }
    }

    /**
     * Repair damage to the hull
     * @param damage Amount of damage to repair
     */
    public void repairHull(int damage) {
        hullHP += damage;
        if (hullHP > baseHullHP) {
            hullHP = baseHullHP;
        }
    }

    /**
     * Add a weapon to the weaponList
     * @param weapon The weapon to add
     */
    public void addWeapon(Weapon weapon) {
        if (weapons.size() >= 4) {
            throw new IllegalStateException("Weapon Slots full");
        } else {
            weapons.add(weapon);
        }
    }

    /**
     * Add an upgrade the the upgradeList
     * @param upgrade The upgrade to add
     */
    public void addUpgrade(RoomUpgrade upgrade) {
        try {
            this.getRoom(upgrade.getAffectsRoom()).addUpgrade(upgrade);

        } catch (IllegalStateException ex) {
            if (ex.getMessage().equals("Room Upgrades full")) {
                /* TODO Jake, Write catch so that a message is displayed on the screen warning you if room upgrades are full.
                     - This catch should somehow warn the player that they have to get rid of one upgrade and make them
                     choose which. A test also needs to be written for this once its done*/
                throw ex;
            }
        }
    }

    public void addCrew(int amount) {
        crew += amount;
    }

    /**
     * Calculates the accuracy of a shot.
     * @return A double denoting the chance to hit of a shot fired.
     */
    public double calculateShipAccuracy() {
        return BASE_SHIP_ACCURACY * this.getRoom(RoomFunction.CROWS_NEST).getMultiplier();
    }

    /**
     * Calculates the chance that the ship has to dodge an incoming shot.
     * @return A double denoting the dodge chance.
     */
    public double calculateShipEvade() {
        return BASE_SHIP_EVADE * this.getRoom(RoomFunction.HELM).getMultiplier();
    }

    /**
     * Checks to see if the ship has a given upgrade
     * @param upgrade The upgrade to look for
     * @return Ship has or does not have upgrade
     */
    public boolean hasUpgrade(RoomUpgrade upgrade) {
        Room room = getRoom(upgrade.getAffectsRoom());
        for (RoomUpgrade i : room.getUpgrades()) {
            if (upgrade == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an upgrade from the ship
     * @param upgrade The upgrade to be deleted
     */
    public void delUpgrade(RoomUpgrade upgrade) {
        if (this.hasUpgrade(upgrade)) {
            getRoom(upgrade.getAffectsRoom()).delUpgrade(upgrade);
        } else {
            throw new IllegalArgumentException("Cannot delete upgrade that you do not have");
        }
    }

    /**
     * @return Percentage of health to be repaired on the a room each turn of combat
     */
    public double calculateRepair() {
        return BASE_SHIP_REPAIR * calculateCrewEffectiveness() * getRoom(RoomFunction.CREW_QUARTERS).getMultiplier();
    }

    /**
     * Repairs every room by an amount given by calculated repair. Should be called every time a players turn starts.
     */
    public void combatRepair() {
        Double repairPercent = calculateRepair();
        for (Room room : rooms) {
            room.repair((int) (room.getBaseHP() * repairPercent / 100));
        }
    }

    /**
     * The more crew you have, the more effective your repairing is. However this tapers off as you get to higher
     * numbers of crew so that you can't fully repair every room
     * @return
     */
    private Double calculateCrewEffectiveness() {
        return 0.6666 * log(0.2 * crew + 1) + 0.67;
    }
}