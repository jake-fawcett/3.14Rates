package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.List;

public class CombatPlayer extends CombatActor {
    public CombatPlayer(Ship ship) {
        super(ship);
    }

    @Override
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport) {
        return null;
//        TODO Write me
    }
}
