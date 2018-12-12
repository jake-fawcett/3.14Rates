package combat.ship;

import combat.items.RoomUpgrade;
import combat.items.Weapon;

import java.util.List;

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
                /* FIXME Handle me please jake  ( ͡° ͜ʖ ͡°)
                   - The throw that is there now is just a placeholder and should be deleted.
                   - This catch should somehow warn the player that they have to get rid of one upgrade and make them
                     choose which. */
                throw ex;
            }
        }
    }
}
