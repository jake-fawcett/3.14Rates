/* ADDED FOR ASSESSMENT 4
Added Monster Node class extending Node which initialises the Monster Node to be added to the map
and allows for screen switching to monster screen
 */

package uk.ac.york.sepr4.ahod2.node;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.screen.MonsterScreen;

public class MonsterNode extends Node {

    //Initialises the Monster node and sets the texture
    public MonsterNode(Node node) {
        super(node.getId(), node.getRow(), node.getCol());
        setConnected(node.getConnected());
        this.setTexture(new TextureRegionDrawable(new TextureRegion(FileManager.seamonsterNodeIcon)));
    }

    //Used to switch screens when monster is encountered
    public void action(GameInstance gameInstance) {
        gameInstance.fadeSwitchScreen(new MonsterScreen(gameInstance));
    }
}
