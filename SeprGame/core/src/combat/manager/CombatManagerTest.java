package combat.manager;

import banks.ShipBank;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.ship.Ship;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CombatManagerTest {
    CombatManager tester;

    @Before
    public void setUp(){
        System.out.println("here");
        tester = new CombatManager(new CombatPlayer(ShipBank.STARTER_SHIP.getShip()),
                new CombatEnemy(ShipBank.STARTER_SHIP.getShip()));
    }

}