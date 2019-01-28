package combat.ship;

/**
 * The functions the rooms can take.
 */
public enum RoomFunction {
    /**
     * Affects weapon damage
     */
    GUN_DECK,
    /**
     * Affects weapon accuracy
     */
    CROWS_NEST,
    /**
     * Affects evade chance
     */
    HELM,
    /**
     * Affects room repair speed
     */
    CREW_QUARTERS,
    /**
     * Does double damage to the hull when hit
     */
    NON_FUNCTIONAL
}
