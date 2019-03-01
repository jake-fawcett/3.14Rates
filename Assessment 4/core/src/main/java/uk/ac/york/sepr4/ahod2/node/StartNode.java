package uk.ac.york.sepr4.ahod2.node;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;

import java.util.Optional;

public class StartNode extends Node {

    /***
     * Node type which set's players initial location for current level.
     * @param node
     */
    public StartNode(Node node) {
        super(node.getId(), node.getRow(), node.getCol());
        setConnected(node.getConnected());
        this.setTexture(new TextureRegionDrawable(new TextureRegion(FileManager.startNodeIcon)));
    }

    /***
     * Set player's location to this node.
     * @param gameInstance
     */
    @Override
    public void action(GameInstance gameInstance) {
        Optional<Node> loc = gameInstance.getPlayer().getLocation();
        if (!loc.isPresent()) {
            gameInstance.getPlayer().setLocation(Optional.of(this));
            gameInstance.getMessageHUD().addStatusMessage("Select Next Node!", 5f);
        }
    }
}
