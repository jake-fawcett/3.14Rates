package combat;

import combat.actors.CombatPlayer;

public class CombatManager {
    private CombatPlayer player;
    private CombatPlayer enemy;
    private Boolean isPlayersTurn;

    public CombatManager(CombatPlayer player, CombatPlayer enemy, Boolean isPlayersTurn) {
        this.player = player;
        this.enemy = enemy;
        this.isPlayersTurn = isPlayersTurn;
    }

    //TODO Write methods
}
