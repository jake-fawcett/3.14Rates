package uk.ac.york.sepr4.ahod2.object.entity;

import lombok.Data;
import uk.ac.york.sepr4.ahod2.node.Node;
import uk.ac.york.sepr4.ahod2.object.GameLevel;

import java.util.Optional;

/***
 * Class used to represent the player.
 * Holds data relating to player's progress through the game such as their level and location.
 */
@Data
public class Player {

    private Ship ship;
    private Optional<Node> location = Optional.empty();
    private Integer gold = 100;
    private GameLevel level;
    private Integer BossCounter;

    public Player(GameLevel gameLevel) {
        ship = new Ship();
        this.level = gameLevel;
        //set higher than default ship health
        ship.setMaxHealth(20);
        ship.setHealth(20);
        BossCounter = 0;
    }

    //TODO: WIP
    public Integer getScore() {
        return 0;
    }

    public void takeGold(Integer gold) {
        this.gold -= gold;
    }

    public void addGold(Integer gold) {
        this.gold += gold;
    }

    public Ship getShip(){
        return ship;
    }

    public Integer getBossCounter(){
        return BossCounter;
    }

    public void incBoss(){
        BossCounter++;
    }

}
