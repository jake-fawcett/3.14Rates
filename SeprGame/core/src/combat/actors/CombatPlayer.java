package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.List;

/**
 * Interacts with the player to get their move
 */
public class CombatPlayer extends CombatActor {
    public CombatPlayer(Ship ship) {
        super(ship);
    }

    @Override
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy) {
        return null;
//        TODO Write me
    }
}
