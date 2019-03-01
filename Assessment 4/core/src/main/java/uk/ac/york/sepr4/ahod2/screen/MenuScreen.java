package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.sepr4.ahod2.AHOD2;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.StyleManager;

public class MenuScreen extends AHODScreen {

    private AHOD2 game;
    private GameInstance gameInstance;

    //TODO: Change background
    public MenuScreen(AHOD2 game) {
        super(new Stage(new ScreenViewport()), FileManager.menuScreenBG);
        this.game = game;

        loadMenu();
    }

    /***
     * Create GameInstance if not exists.
     * @return
     */
    private GameInstance getOrCreateGameInstance() {
        if (gameInstance == null) {
            gameInstance = new GameInstance(game);
        }
        return gameInstance;
    }

    /***
     * Set game instance to null (after game end).
     */
    public void newGame() {
        this.gameInstance = null;
    }

    @Override
    public void renderInner(float delta) {
    }

    /***
     * Load menu items to screen.
     */
    private void loadMenu() {
        Gdx.app.debug("MenuScreen", "Loading Menu!");
        Table table = new Table();
        table.setFillParent(true);

        //create buttons
        TextButton newGame = new TextButton("New Game",
                StyleManager.generateTBStyle(50, Color.PURPLE, Color.GRAY));
        TextButton exit = new TextButton("Exit",
                StyleManager.generateTBStyle(50, Color.RED, Color.GRAY));

        //add buttons to table
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.row();
        table.add(exit).fillX().uniformX();

        // create button listeners
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getOrCreateGameInstance().start();
            }
        });

        getStage().addActor(table);
    }
}
