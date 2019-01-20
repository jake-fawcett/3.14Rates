package game_manager;

import com.badlogic.gdx.Game;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.manager.CombatManager;
import combat.ship.Ship;
import other.Difficulty;
import other.Screen;
import display.*;

import static banks.ShipBank.COLLEGE_SHIP;
import static banks.ShipBank.DEFAULT_BRIG;
import static banks.ShipBank.STARTER_SHIP;
import static other.Constants.STARTING_FOOD;
import static other.Constants.STARTING_GOLD;

/**
 * Controls the overall process of the game, handing control to various sub-managers (eg combat manager) as necessary.
 * It also stores the information about the game which will be needed in lots of places, eg the amount of gold the
 * player has or the points.
 */
public class GameManager extends Game {
    /**
     * Currency of the game
     */
    private int gold;
    /**
     * A resource tied to crew and travelling. As you travel you use up food. The more crew you have, the faster you use
     * it. This stops you ending up with a massive crew and means that you cant stay at sea for ever, progressing the
     * game.
     */
    private int food;
    /**
     * Points are accumulated by killing ships etc. They go toward recording the high scores.
     */
    private int points;
    /**
     * The name of the current player for saves and hi-scores
     */
    private String playerName;

    /**
     * The difficulty that the player is playing on.
     */
    private Difficulty difficulty;

    /**
     * Instance of LIBGDX Game used to allow setScreen to be used
     */
    private Game game;

    /**
     * Creates Instances of enemyShip, playerShip and their Actors to be used in the game
     */
    private Ship playerShip = STARTER_SHIP.getShip();
    private CombatPlayer combatPlayer = new CombatPlayer(playerShip);

    private Ship enemyShip = STARTER_SHIP.getShip();
    private CombatEnemy combatEnemy = new CombatEnemy(enemyShip);

    private Ship collegeShip = COLLEGE_SHIP.getShip();
    private CombatEnemy combatCollege = new CombatEnemy(collegeShip);

    private CombatManager combatManager = new CombatManager(combatPlayer, combatEnemy);

    public Game getGame() { return game; }


    public CombatManager getCombatManager() { return combatManager; }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public void deductGold(int amount) {
        this.gold -= amount;
        if (gold < 0) {
            gold = 0;
        }
    }

    public int getFood() {
        return food;
    }

    public void addFood(int amount) {
        this.food += amount;
    }

    public void deductFood(int amount) {
        food -= amount;
        if (food < 0) {
            food = 0;
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Ship getPlayerShip() {
        return playerShip;
    }

    public Ship getEnemyShip() {
        return enemyShip;
    }

    public Ship getCollegeShip() { return collegeShip; }

    public CombatPlayer getCombatPlayer(){return combatPlayer; }

    public CombatEnemy getCombatEnemy() {return combatEnemy; }

    public CombatEnemy getCombatCollege() { return combatCollege; }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameManager(String playerName, Difficulty difficulty) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.gold = STARTING_GOLD;
        this.food = STARTING_FOOD;
        this.points = 0;
        game = this;
    }

    /**
     * Creates an Instance of Screen and all the different Screens used
     */
    private menuScreen menuScreen;

    @Override
    public void create() { //Called when the application is created
        combatScreen defaultCombatScreen = new combatScreen(game,false);
        combatScreen collegeCombatScreen = new combatScreen(game,true);
        departmentScreen departmentScreen = new departmentScreen(game);
        menuScreen =  new menuScreen(game);
        this.setScreen(menuScreen);


    }

    @Override
    public void render() { //Called when the Application should render, Called continuously
        super.render();
    }

    @Override
    public void dispose() { //Called when the application is destroyed, resources must be disposed of from Memory

    }


}
