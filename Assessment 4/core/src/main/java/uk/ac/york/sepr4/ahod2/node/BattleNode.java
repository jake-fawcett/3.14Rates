package uk.ac.york.sepr4.ahod2.node;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.screen.BattleScreen;

public class BattleNode extends Node {

    /***
     * Node type which creates a battle depending on level difficulty.
     * @param node
     */
    public BattleNode(Node node) {
        super(node.getId(), node.getRow(), node.getCol());
        setConnected(node.getConnected());
        this.setTexture(new TextureRegionDrawable(new TextureRegion(FileManager.battleNodeIcon)));
    }

    /***
     * Switch current screen to new instance of BattleScreen.
     * @param gameInstance
     */
    @Override
    public void action(GameInstance gameInstance) {
        BattleScreen battleScreen = new BattleScreen(gameInstance);
        gameInstance.fadeSwitchScreen(battleScreen);
    }
}
