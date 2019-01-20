package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static other.Constants.COOLDOWN_TICKS_PER_TURN;

public class CombatEnemy extends CombatActor {
    public CombatEnemy(Ship ship) {
        super(ship);
    }

    public List<Pair<Room, Weapon>> takeTurn(Ship enemy) {
        List<Pair<Room, Weapon>> attackReport = new ArrayList<Pair<Room, Weapon>>();
        for (Weapon weapon : getShip().getWeapons()) {
            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
        }
        getShip().combatRepair();
        if (hasWepaonsReady()) {
            Weapon weaponFired = pickRandChargedWeapon();
            weaponFired.fire();
            attackReport.add(new Pair<Room, Weapon>(pickRandRoom(enemy), weaponFired));
        }
        return attackReport;
    }

    public boolean hasWepaonsReady() {
        for (Weapon weapon : getShip().getWeapons()) {
            if (weapon.getCurrentCooldown() == 0) {
                return true;
            }
        }
        return false;
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
        if (chargedWeapons.size() == 1) {
            return chargedWeapons.get(0);
        } else {
            return chargedWeapons.get(rand.nextInt(chargedWeapons.size() - 1));
        }
    }


    private Room pickRandRoom(Ship ship) {
        Random rand = new Random();
        List<Room> rooms = ship.getRooms();
        return rooms.get(rand.nextInt(rooms.size() - 1));
    }
}
