import combat.ship.Ship;
import other.Difficulty;

public class GameManager {
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
}
