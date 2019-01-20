package location;

import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.manager.CombatManager;
import combat.ship.Ship;


public class College {
    String name;
    Ship opponent;

    public College(String name, Ship opponent, Ship player) {
        this.name = name;
        this.opponent = opponent;
        this.player = player;
    }

    Ship player;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ship getOpponent() {
        return opponent;
    }

    public void setOpponent(Ship opponent) {
        this.opponent = opponent;
    }


    public College(String name, Ship opponent) {
        this.name = name;
        this.opponent = opponent;
    }

    public void arrive() {
        CombatManager cm = new CombatManager(new CombatPlayer(player), new CombatEnemy(opponent));
        cm.combatLoop();
    }
}
