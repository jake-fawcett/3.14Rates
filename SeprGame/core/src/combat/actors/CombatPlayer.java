package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.COOLDOWN_TICKS_PER_TURN;

public class CombatPlayer extends CombatActor {
    public CombatPlayer(Ship ship) {
        super(ship);
    }

    @Override
    /**
     * Takes a damage report telling you what has been attacked, decrements cooldowns of weapons, repairs the ship
     * and fires on enemy by returning an attack report.
     */
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy) {
        List<Pair<Room, Weapon>> attackReport = new ArrayList<Pair<Room, Weapon>>();
        for (Weapon weapon : getShip().getWeapons()) {
            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
        }
        getShip().combatRepair();



        //GENERATE ATTACK REPORT HERE



        return attackReport;
//        TODO Write me
    }
}
