package game_manager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import combat.ship.Ship;
import other.Difficulty;

public class GameManager extends Game {
    private int gold;
    private int food;
    private int points;
    private String playerName;
    private Ship playerShip;
    private Difficulty difficulty;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public void setPlayerShip(Ship playerShip) {
        this.playerShip = playerShip;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public GameManager(String playerName, Difficulty difficulty) {
//        TODO implement below
//        this.gold = ;
//        this.food = ;
//        this.points = ;
//        this.playerShip =
        this.playerName = playerName;
        this.difficulty = difficulty;
    }

    public GameManager() {
//        TODO write code to get this info
        String newPlayerName = null;
        Difficulty newDifficulty = null;
//        set rest of the info to defaults, preferably by using the other constructor somehow
    }

    @Override
    public void create() { //Called when the application is created

    }

    @Override
    public void render() { //Called when the Application should render, Called continuously

    }

    @Override
    public void dispose() { //Called when the application is destroyed, resources must be disposed of from Memory

    }

}
