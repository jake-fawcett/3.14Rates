package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.List;

/**
 * An abstract class which defines the architecture of CombatEnemy and CombatPlayer
 */
public abstract class CombatActor {
    private Ship ship;

    public Ship getShip() {
        return ship;
    }

    CombatActor(Ship ship) {
        this.ship = ship;
    }

    /**
     * Handles the entire process of taking a turn. From decrementing cooldowns and repairing damage from the opponent's
     * turn to ending the turn and sending targets and weapons firing on them to the combat manager.
     *
     * @param damageReport A list of pairs telling the CombatActor which of their rooms were hit and how much damage for
     *                    eg: [[Helm, 5], [Sails,2]]
     * @return A list of pairs of Rooms and Weapons telling the CombatManager which rooms were fired at and with which
     * weapons
     */
    public abstract List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy);
}
