package combat;

import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;

public class CombatManager {
    private CombatPlayer player;
    private CombatEnemy enemy;
    private Boolean isPlayersTurn;

    public CombatManager(CombatPlayer player, CombatEnemy enemy, Boolean isPlayersTurn) {
        this.player = player;
        this.enemy = enemy;
        this.isPlayersTurn = isPlayersTurn;
    }

    //TODO Write methods
}
