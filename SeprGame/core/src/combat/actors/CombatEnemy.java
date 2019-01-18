package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatEnemy extends CombatActor {
    public CombatEnemy(Ship ship) {
        super(ship);
    }

    @Override
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy) {
        List<Pair<Room, Weapon>> attackReport = new ArrayList<Pair<Room, Weapon>>();

        getShip().combatRepair();
        attackReport.add(new Pair<Room, Weapon>(pickRandRoom(enemy), pickRandChargedWeapon()));

        return attackReport;
    }

    private Weapon pickRandChargedWeapon() {
        List<Weapon> chargedWeapons = new ArrayList<Weapon>();
        List<Weapon> weapons = getShip().getWeapons();
        Random rand = new Random();
        for (Weapon weapon : weapons) {
            if (weapon.getCurrentCooldown() == 0) {
                chargedWeapons.add(weapon);
            }
        }
        return chargedWeapons.get(rand.nextInt(chargedWeapons.size()));
    }


    private Room pickRandRoom(Ship ship) {
        Random rand = new Random();
        List<Room> rooms = ship.getRooms();
        return rooms.get(rand.nextInt(rooms.size()));
    }
}
