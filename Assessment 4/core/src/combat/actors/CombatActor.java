package combat.actors;

import combat.ship.Ship;

/**
 * An abstract class which defines the architecture of CombatEnemy and CombatPlayer
 * Had to implement Serializable due to encoding needed for saving game data
 */
public abstract class CombatActor implements java.io.Serializable {
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
     * @return A list of pairs of Rooms and Weapons telling the CombatManager which rooms were fired at and with which
     * weapons
     */
    //public abstract List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy);
}
