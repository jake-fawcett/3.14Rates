package combat.ship;

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

    public int getCrew() {
        return crew;
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
}
