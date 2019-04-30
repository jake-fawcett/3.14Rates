// ADDED FOR ASSESSMENT 4: This entire class was added to show the player what the new crew member that they earned does

package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.StyleManager;

public class PerkScreen extends AHODScreen {
    private GameInstance gameInstance;



    public PerkScreen(GameInstance gameInstance, String text) {
        super(new Stage(new ScreenViewport()), FileManager.menuScreenBG);
        this.gameInstance = gameInstance;
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(new Label("You have gained a new crew member!", StyleManager.generateLabelStyle(50, Color.GOLD)));
        table.row();
        table.add(new Label(text, StyleManager.generateLabelStyle(50, Color.GOLD)));
        table.row();
        TextButton textButton = new TextButton("Return to Game", StyleManager.generateTBStyle(40, Color.GOLD, Color.GRAY));
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                gameInstance.advanceLevel();
            }
        });
        table.add(textButton);
        getStage().addActor(table);
    }


    @Override
    public void renderInner(float delta) {

    }
}
