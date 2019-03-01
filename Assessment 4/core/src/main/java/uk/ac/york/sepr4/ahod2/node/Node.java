package uk.ac.york.sepr4.ahod2.node;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Data;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.object.encounter.Encounter;
import uk.ac.york.sepr4.ahod2.screen.EncounterScreen;

import java.util.ArrayList;
import java.util.List;

/***
 * Base node type used when generating node map (before population with types).
 */
@Data
public class Node {

    private Integer id, row, col;
    private List<Node> connected = new ArrayList<>();
    private Drawable texture;

    /***
     * Create row with specified id at specified position: row, col
     * @param id
     * @param row
     * @param col
     */
    public Node(Integer id, Integer row, Integer col) {
        this.id = id;
        this.row = row;
        this.col = col;

        //JUnit Compatibility - Check Gdx elements loaded!
        //This means unit tests don't crash if Gdx assets can be loaded (that are present in FM).
        try {
            Class.forName("uk.ac.york.sepr4.ahod2.io.FileManager");
            texture = new TextureRegionDrawable(new TextureRegion(FileManager.randEncounterIcon));
        } catch (Exception ex) {
            //either not found or initializer exception (no gdx - for tests)
        } catch (Error error) {
        }
    }

    /***
     * Default node action. Generate random encounter and switch to encounter screen.
     * @param gameInstance
     */
    public void action(GameInstance gameInstance) {
        Encounter encounter = gameInstance.getEncounterManager().generateEncounter();
        gameInstance.fadeSwitchScreen(new EncounterScreen(gameInstance, encounter));
    }

    public void addConnectedNode(Node node) {
        connected.add(node);
    }


}
