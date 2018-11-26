package combat.actors;

import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.List;

public class CombatEnemy extends CombatActor {
    public CombatEnemy(Ship ship) {
        super(ship);
    }

    @Override
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageTaken) {
        return null;
//        TODO Write me
    }
}
