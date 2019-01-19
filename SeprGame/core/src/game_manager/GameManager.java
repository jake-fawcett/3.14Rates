package game_manager;

import com.badlogic.gdx.Game;
import combat.ship.Ship;
import other.Difficulty;
import other.Screen;
import display.*;

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
     * The ship that the player is using. Used in combat, departments etc.
     */
    private Ship playerShip;
    /**
     * The difficulty that the player is playing on.
     */
    private Difficulty difficulty;
//    FIXME JAKE what is this?
    private Game game;

//    FIXME Jake what is this?
    public Game getGame() { return game; }


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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public GameManager(String playerName, Difficulty difficulty) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.gold = STARTING_GOLD;
        this.food = STARTING_FOOD;
        this.points = 0;
        this.playerShip = STARTER_SHIP.getShip();
        game = this;
    }

    public GameManager() {
//        TODO Write me so that the screen asks the player for their information
//          (name and the difficulty they want to play)
    }

    private Screen screen = Screen.MENU;
    //FIXME Jake, do we still need these?
    //private combatScreen defaultCombatScreen;
    //private combatScreen collegeCombatScreen;
    private departmentScreen departmentScreen;
    private menuScreen menuScreen;

    @Override
    public void create() { //Called when the application is created
        //defaultCombatScreen = new combatScreen(false);
        //collegeCombatScreen = new combatScreen(true);
        departmentScreen = new departmentScreen(game);
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
