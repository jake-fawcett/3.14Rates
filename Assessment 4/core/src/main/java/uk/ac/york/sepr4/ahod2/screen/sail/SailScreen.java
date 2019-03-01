package uk.ac.york.sepr4.ahod2.screen.sail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.AHOD2;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.SailInputProcessor;
import uk.ac.york.sepr4.ahod2.node.Node;
import uk.ac.york.sepr4.ahod2.node.StartNode;
import uk.ac.york.sepr4.ahod2.object.entity.Player;
import uk.ac.york.sepr4.ahod2.screen.AHODScreen;

import java.util.Optional;

public class SailScreen extends AHODScreen {

    @Getter
    private GameInstance gameInstance;
    //input processor for screen (up/down map)
    private SailInputProcessor sailInputProcessor;

    public SailScreen(GameInstance gameInstance) {
        super(new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                new OrthographicCamera())), FileManager.sailScreenBG);

        this.gameInstance = gameInstance;

        //add SailInputProcessor to input multiplexer (set in AHODScreen)
        sailInputProcessor = new SailInputProcessor(this);
        getInputMultiplexer().addProcessor(sailInputProcessor);

        gameInstance.getMessageHUD().addStatusMessage("Select Starting Node!", 10f);

        //enable stats and message HUDs
        setStatsHUD(gameInstance);
        setMessageHUD(gameInstance);
    }

    /***
     * Poll for camera scroll and set world height based on max nodeview height.
     * @param delta
     */
    @Override
    public void renderInner(float delta) {
        sailInputProcessor.scrollCamera();
        //set world height to nodeview height (scrolling and background tiling)
        getStage().getViewport().setWorldHeight(gameInstance.getPlayer().getLevel().getNodeView().getHeight());
        gameInstance.getPlayer().getLevel().getNodeView().update();
    }

    /***
     * Called when player has clicked on specified node.
     * If specified node is above current node, run specified node action.
     * @param node specified node
     */
    public void nodeClick(Node node) {
        Player player = gameInstance.getPlayer();
        Optional<Node> loc = player.getLocation();
        //node action if player location is unset (starting) or node clicked is above current
        if (loc.isPresent()) {
            //if node is above (or in debug mode)
            if (node.getRow() == loc.get().getRow() + 1 || AHOD2.DEBUG) {
                gameInstance.nodeAction(node);
            } else {
                Gdx.app.debug("SailScreen", "Lower or current position node clicked!");
            }
        } else {
            //run node action if loc not present (allows StartNode to set location)
            if(node instanceof StartNode) {
                gameInstance.nodeAction(node);
            }
        }

    }

}
