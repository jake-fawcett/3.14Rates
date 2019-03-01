package uk.ac.york.sepr4.ahod2.screen.sail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.node.Node;
import uk.ac.york.sepr4.ahod2.object.GameLevel;
import uk.ac.york.sepr4.ahod2.object.entity.Player;
import uk.ac.york.sepr4.ahod2.util.NodeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NodeView {

    //spacing variables (for screen placement)
    @Getter
    private static final float vertSpacing = 200f, nodeSize = 150f;
    //nodeview innate variables
    private SailScreen sailScreen;
    private GameLevel gameLevel;
    private List<Node> nodeMap;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    //map of node id with respective imagebuttons
    private HashMap<Integer, ImageButton> nodeButtons = new HashMap<>();
    //location of highest node generated
    @Getter
    private float height = 0;

    public NodeView(SailScreen sailScreen, GameLevel gameLevel) {
        this.sailScreen = sailScreen;
        this.gameLevel = gameLevel;

        createNodeMap();
    }

    /***
     * Draw location and node connections.
     * Make sure node buttons are on stage.
     */
    public void update() {
        //set line width (for thicker ShapeRenderer objects)
        Gdx.gl.glLineWidth(3);
        shapeRenderer.setProjectionMatrix(sailScreen.getBatch().getProjectionMatrix());
        //make sure nodebuttons are on stage.
        nodeButtons.values().forEach(btn -> {
            if (!sailScreen.getStage().getActors().contains(btn, false)) {
                sailScreen.getStage().addActor(btn);
            }
        });
        drawConnections();
        drawPlayerLocation();
    }

    /***
     * Creates NodeMap for current level.
     * Add node actors (imagebuttons) to stage with correct spacing.
     */
    private void createNodeMap() {
        nodeMap = NodeUtil.generateRandomNodeMap(gameLevel);
        for (int i = 0; i < nodeMap.size(); i++) {
            List<Node> row = getRow(nodeMap, i);
            float spacing = getNodeSpacings(row.size());
            for (Node node : row) {
                ImageButton btn = new ImageButton(node.getTexture());
                btn.setSize(nodeSize, nodeSize);
                //TODO: Works but cleanup
                Vector2 pos = new Vector2(((node.getCol() + 1) * spacing + (nodeSize * node.getCol())), ((node.getRow() + 1) * vertSpacing));
                btn.setPosition(pos.x, pos.y);
                if (pos.y + vertSpacing > height) {
                    height = pos.y + vertSpacing;
                }
                btn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        sailScreen.nodeClick(node);
                    }
                });
                nodeButtons.put(node.getId(), btn);
            }
        }


    }

    /***
     * Draw circle around player's current node.
     * (If player's location is set)
     */
    private void drawPlayerLocation() {
        Player player = sailScreen.getGameInstance().getPlayer();
        Optional<Node> node = player.getLocation();
        //if location is set (starting node selected)
        if (node.isPresent()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            ImageButton imageButton = nodeButtons.get(node.get().getId());
            Vector2 loc = getNodeButtonCenter(imageButton);
            shapeRenderer.circle(loc.x, loc.y, imageButton.getWidth() / 2);
            shapeRenderer.end();
        }
    }

    /***
     * Draw lines between connected nodes.
     */
    private void drawConnections() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        nodeButtons.keySet().forEach(nodeId -> {
            Node node = nodeMap.get(nodeId);
            node.getConnected().forEach(connection -> {
                ImageButton source = nodeButtons.get(nodeId);
                ImageButton target = nodeButtons.get(connection.getId());
                //draw rectline so line is thicker
                shapeRenderer.rectLine(getNodeButtonCenter(source), getNodeButtonCenter(target), 3);
            });
        });
        shapeRenderer.end();
    }

    /***
     * Get position of center of specified ImageButton.
     * @param imageButton specified ImageButton
     * @return vector represent position of center
     */
    private Vector2 getNodeButtonCenter(ImageButton imageButton) {
        float x = imageButton.getX(), y = imageButton.getY();
        x += imageButton.getWidth() / 2;
        y += imageButton.getHeight() / 2;
        return new Vector2(x, y);
    }

    //TODO: Not efficient

    /***
     * Get nodes from list of nodes that are in specified row
     * @param nodes list of nodes
     * @param id specified row
     * @return list of nodes in row
     */
    private List<Node> getRow(List<Node> nodes, Integer id) {
        List<Node> row = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getRow() == id) {
                row.add(node);
            }
        }
        return row;
    }

    /***
     * Get spacing between nodes based on number of nodes in row
     * @param no number of nodes in row
     * @return spacing between nodes
     */
    private float getNodeSpacings(Integer no) {
        float w = Gdx.graphics.getWidth();
        return (w - (no * nodeSize)) / (no + 1);
    }

}
