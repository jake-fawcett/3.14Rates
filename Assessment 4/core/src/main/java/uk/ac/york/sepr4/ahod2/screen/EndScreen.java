package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.graphics.Color;
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

public class EndScreen extends AHODScreen {

    private GameInstance gameInstance;
    private boolean win;

    public EndScreen(GameInstance gameInstance, boolean win) {
        super(new Stage(new ScreenViewport()), FileManager.menuScreenBG);

        this.gameInstance = gameInstance;
        this.win = win;

        setupScreen();
    }

    /***
     * Create table for screen elements.
     * Label for win/lose.
     * Exit button to menu.
     */
    private void setupScreen() {
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //set win/lose label
        if (win) {
            table.add(new Label("You Won!", StyleManager.generateLabelStyle(50, Color.GOLD)));
        } else {
            table.add(new Label("You Lost!", StyleManager.generateLabelStyle(50, Color.RED)));

        }
        table.row();

        //exit to menu button
        TextButton textButton = new TextButton("Exit to Menu!", StyleManager.generateTBStyle(40, Color.RED, Color.GRAY));
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                gameInstance.getGame().getMenuScreen().newGame();
                gameInstance.fadeSwitchScreen(gameInstance.getGame().getMenuScreen());
            }
        });
        table.add(textButton);

        getStage().addActor(table);
    }

    @Override
    public void renderInner(float delta) {
    }
}
