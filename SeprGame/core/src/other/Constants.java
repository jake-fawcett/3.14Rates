package other;

    public class Constants {
        /**
         * The reduction applied to the value of an item when a store buys it from you. Eg. if this is 0.5 and the item
         * costs 100, the shop will offer you 50.
         */
        public static final double STORE_SELL_PRICE_MULTIPLIER = 0.66;
        public static final int DEFAULT_SHIP_HP = 1000;
        public static final int DEFAULT_SHIP_CREW = 25;
        public static final int DEFAULT_UPGRADE_COST = 100;
        public static final int DEFAULT_WEAPON_COST = 100;
        public static final int DEFAULT_WEAPON_DAMAGE = 100;
        public static final int DEFAULT_WEAPON_COOLDOWN = 10000;
        public static final double DEFAULT_WEAPON_CRIT_CHANCE = 0.2;
        public static final double DEFAULT_WEAPON_HIT_CHANCE = 0.9;
        public static final int STARTING_GOLD = 250;
        public static final int STARTING_FOOD = 100;
        public static final int DEFAULT_ROOM_HP = 1000;
        public static final int CREW_COST = 50;
        public static final int REPAIR_COST = 25;
        public static final int FOOD_COST = 10;
        public static final double BASE_SHIP_ACCURACY = 1;
        public static final double BASE_SHIP_EVADE = 1;
        public static final double EASY_SCORE_MULTIPLIER = 1.0;
        public static final double MED_SCORE_MULTIPLIER = 2.0;
        public static final double DIFF_SCORE_MULTIPLIER = 3.0;
        /** A number from 0 to 1 which decides the maximum percent of health a ship can have before you can board it.
         *
         *  E.g. if you want ships to be boardable after removing 60% of their health, set this to 0.4)
         */
        public static final double SHIP_BOARD_PERCENTAGE = 0.25;
}
