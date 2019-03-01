package uk.ac.york.sepr4.ahod2.node;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.object.GameLevel;
import uk.ac.york.sepr4.ahod2.object.ShipFactory;
import uk.ac.york.sepr4.ahod2.object.building.College;
import uk.ac.york.sepr4.ahod2.object.entity.Ship;
import uk.ac.york.sepr4.ahod2.screen.BattleScreen;

@Getter
public class CollegeNode extends Node {

    private College college;

    /***
     * Node type which creates a college (boss) battle.
     * @param node
     */
    public CollegeNode(Node node, College college) {
        super(node.getId(), node.getRow(), node.getCol());
        setConnected(node.getConnected());
        this.college = college;
        this.setTexture(new TextureRegionDrawable(new TextureRegion(FileManager.collegeNodeIcon)));
    }

    /***
     * Switch current screen to college (boss) battle.
     * @param gameInstance
     */
    @Override
    public void action(GameInstance gameInstance) {
        GameLevel gameLevel = gameInstance.getCurrentLevel();
        //generate boss ship
        Ship ship = ShipFactory.generateEnemyShip(college.getBossDifficulty());
        ship.setBoss(true);
        //create battle screen
        BattleScreen battleScreen = new BattleScreen(gameInstance,
                ship,
                college.getBossDifficulty(),
                gameLevel.getLevelGold());
        //switch to battle screen
        gameInstance.fadeSwitchScreen(battleScreen);
    }
}
