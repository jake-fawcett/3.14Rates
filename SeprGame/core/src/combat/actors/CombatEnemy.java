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

/**
 * The "AI" which controls an opponent in combat.
 */
public class CombatEnemy extends CombatActor {
    public CombatEnemy(Ship ship) {
        super(ship);
    }

    @Override
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy) {
        List<Pair<Room, Weapon>> attackReport = new ArrayList<Pair<Room, Weapon>>();

//        Setting up turn
        for (Weapon weapon : getShip().getWeapons()) {
            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
        }
        getShip().combatRepair();

//        Firing at enemy
        if (hasWepaonsReady()) {
            Weapon weaponFired = pickRandChargedWeapon();
            weaponFired.fire();
            attackReport.add(new Pair<Room, Weapon>(pickRandRoom(enemy), weaponFired));
        }

        return attackReport;
    }

    /**
     * @return Boolean - Ship has weapon ready to fire.
     */
    private boolean hasWepaonsReady() {
        for (Weapon weapon : getShip().getWeapons()) {
            if (weapon.getCurrentCooldown() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return A randomly picked weapon which is ready to fire.
     */
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

    /**
     * @param ship The enemy ship
     * @return A random room on the enemy ship
     */
    private Room pickRandRoom(Ship ship) {
        Random rand = new Random();
        List<Room> rooms = ship.getRooms();
        return rooms.get(rand.nextInt(rooms.size()));
    }
}
