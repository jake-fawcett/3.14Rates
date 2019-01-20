package other;

/**
 * A set of constants used in various places through the game. These you always be your first port of call for balancing
 * since they allow the easiest changes without editing any existing code.
 */
public class Constants {
    /**
     * The reduction applied to the value of an item when a store buys it from you. Eg. if this is 0.5 and the item
     * costs 100, the shop will offer you 50.
     */
    public static final double STORE_SELL_PRICE_MULTIPLIER = 0.66;
    /**
     * The default HP that a new ship is given
     */
    public static final int DEFAULT_SHIP_HP = 1000;
    /**
     * The default crew that a new ship is given
     */
    public static final int DEFAULT_SHIP_CREW = 25;
    /**
     * The default cost of an upgrade
     */
    public static final int DEFAULT_UPGRADE_COST = 100;
    /**
     * The default cost of a weapon
     */
    public static final int DEFAULT_WEAPON_COST = 100;
    /**
     * The default damage of a weapon
     */
    public static final int DEFAULT_WEAPON_DAMAGE = 100;
    /**
     * The default cooldown of a weapon
     */
    public static final int DEFAULT_WEAPON_COOLDOWN = 2000;
    /**
     * The rate at which weapons cool down
     */
    public static final int COOLDOWN_TICKS_PER_TURN = 500;
    /**
     * The default chance for a critical hit
     */
    public static final double DEFAULT_WEAPON_CRIT_CHANCE = 0.2;
    /**
     * The default chance for a hit
     */
    public static final double DEFAULT_WEAPON_HIT_CHANCE = 0.9;
    /**
     * The amount of gold a player starts with
     */
    public static final int STARTING_GOLD = 500;
    /**
     * The amount of food a player starts with
     */
    public static final int STARTING_FOOD = 100;
    /**
     * The default hp of a room on a new ship
     */
    public static final int DEFAULT_ROOM_HP = 200;
    /**
     * The cost of crew in shops
     */
    public static final int CREW_COST = 50;
    /**
     * Amount of Crew Recieve per Purchase
     */
    public static final int SHOP_CREW_AMOUNT = 1;
    /**
     * The cost of repairs in shops
     */
    public static final int REPAIR_COST = 25;
    /**
     * Amount of Repair Recieve per Purchase
     */
    public static final int SHOP_REPAIR_AMOUNT = 25;
    /**
     *  The cost of food in shops
     */
    public static final int FOOD_COST = 10;
    /**
     * Amount of Food Recieve per Purchase
     */
    public static final int SHOP_FOOD_AMOUNT = 10;
    /**
     * The base accuracy a ship has. Editing this is a good way to make large global edits to accuracy
     */
    public static final double BASE_SHIP_ACCURACY = 1;
    /**
     * The base chance a ship has of evading an incoming shot
     */
    public static final double BASE_SHIP_EVADE = 0.2;
    /**
     * The base amount of room health repaired each turn for a room
     */
    public static final double BASE_SHIP_REPAIR = 2.677;
    /**
     * The multiplier applied to points earned on easy difficulty
     */
    public static final double EASY_SCORE_MULTIPLIER = 1.0;
    /**
     * The multiplier applied to points earned on medium difficulty
     */
    public static final double MED_SCORE_MULTIPLIER = 2.0;
    /**
     * The multiplier applied to points earned on hard difficulty
     */
    public static final double DIFF_SCORE_MULTIPLIER = 3.0;
    /**
     * A number from 0 to 1 which decides the maximum percent of health a ship can have before you can board it.
     * <p>
     * E.g. if you want ships to be boardable after removing 60% of their health, set this to 0.4)
     */
    public static final double SHIP_BOARD_PERCENTAGE = 0.25;

    public static final double NON_FUNCTIONAL_ROOM_MULTIPLIER = 1.5;
}
