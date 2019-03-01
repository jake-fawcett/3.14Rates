package uk.ac.york.sepr4.ahod2.screen.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.StyleManager;
import uk.ac.york.sepr4.ahod2.screen.AHODScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * Class used to load and update on-screen elements related to messages (directing the player).
 * This class also holds the button that allows the player to switch to the ShipView screen.
 */
public class MessageHUD {

    private final Float resourceMessageTime = 4f;
    @Getter
    private Stage hudStage;
    private GameInstance gameInstance;
    private Label messageLabel;
    private String currentMessage = "";
    private float currentMessageTime = 0;
    private HashMap<Label, Float> goldMessages = new HashMap<>();

    public MessageHUD(GameInstance gameInstance) {
        this.gameInstance = gameInstance;

        // Local widths and heights.
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        hudStage = new Stage(new FitViewport(w, h, new OrthographicCamera()));

        createTable();
    }

    /***
     * Add sliding message showing increase in gold of specified value.
     * @param gold specified value of gold
     */
    public void addGoldMessage(Integer gold) {
        Label label = new Label("+ " + gold + " GOLD", StyleManager.generateLabelStyle(30, Color.GREEN));
        goldMessages.put(label, 0f);
    }

    /***
     * Add temporary status message to inform player
     * @param message message to display
     * @param time time to display
     */
    public void addStatusMessage(String message, float time) {
        currentMessage = message;
        currentMessageTime = time;
    }

    /***
     * Update visible messages.
     * Remove status and gold message if delta exceeds remaining time.
     * Add label actors to stage if not present.
     * @param delta time since last update.
     */
    public void update(float delta) {
        if (currentMessageTime != 0) {
            //not a perm message
            currentMessageTime -= delta;
            if (currentMessageTime <= 0) {
                currentMessage = "";
                currentMessageTime = 0;
            }
        }
        messageLabel.setText(currentMessage.toUpperCase());

        //slide gold message up the screen edge
        List<Label> toRemove = new ArrayList<>();
        for (Label label : goldMessages.keySet()) {
            Float time = (goldMessages.get(label));
            if (time + delta > resourceMessageTime) {
                //add label to be removed
                toRemove.add(label);
            } else {
                goldMessages.replace(label, time + delta);
                //add label to stage if not exists
                if (!hudStage.getActors().contains(label, false)) {
                    hudStage.addActor(label);
                }
                //slide label
                label.setPosition(5, 5 + (Gdx.graphics.getHeight() / 2) * (time / resourceMessageTime));

            }
        }
        //remove message here (avoid ConcurrentModificationException)
        for (Label remove : toRemove) {
            goldMessages.remove(remove);
            hudStage.getActors().removeValue(remove, false);
        }

        hudStage.act();
        hudStage.draw();
    }


    /***
     * Create table for (gold and status) messages and ShipView button
     */
    private void createTable() {

        //create table and set position
        Table messageTable = new Table();
        messageTable.setFillParent(true);
        messageTable.bottom();

        //create status message label
        messageLabel = new Label("", StyleManager.generateLabelStyle(30, Color.PINK));

        //create ShipView button
        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(FileManager.hudShipView));
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                if (gameInstance.getGame().getScreen() instanceof AHODScreen) {
                    gameInstance.getShipViewScreen().setPreviousScreen((AHODScreen) gameInstance.getGame().getScreen());

                }
                gameInstance.fadeSwitchScreen(gameInstance.getShipViewScreen());
            }
        });

        //put elements in table
        messageTable.add(messageLabel).expandX()
                .padBottom(Value.percentWidth(0.02f, messageTable))
                .padLeft(Value.percentWidth(0.02f, messageTable))
                .left();
        messageTable.add(imageButton).expandX()
                .padBottom(Value.percentWidth(0.02f, messageTable))
                .padRight(Value.percentWidth(0.02f, messageTable))
                .right()
                .bottom()
                .width(Value.percentWidth(0.04f, messageTable))
                .height(Value.percentWidth(0.04f, messageTable));

        hudStage.addActor(messageTable);

    }

}
