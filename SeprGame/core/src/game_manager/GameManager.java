package game_manager;

import com.badlogic.gdx.Game;
import combat.ship.Ship;
import other.Difficulty;
import other.Screen;
import display.*;

import static banks.ShipBank.STARTER_SHIP;
import static other.Constants.STARTING_FOOD;
import static other.Constants.STARTING_GOLD;

public class GameManager extends Game {
    private int gold;
    private int food;
    private int points;
    private String playerName;
    private Ship playerShip;
    private Difficulty difficulty;
    private Game game;

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
    private combatScreen defaultCombatScreen;
    private combatScreen collegeCombatScreen;
    private departmentScreen departmentScreen;
    private menuScreen menuScreen;

    @Override
    public void create() { //Called when the application is created
        defaultCombatScreen = new combatScreen(game,false);
        collegeCombatScreen = new combatScreen(game,true);
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
