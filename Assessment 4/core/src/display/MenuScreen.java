package display;

import base.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game_manager.GameManager;

public class MenuScreen extends BaseScreen {
    private SpriteBatch batch = new SpriteBatch();

    private Texture menuBackground = new Texture("menuBackground.png");
    private BitmapFont titleFont = new BitmapFont(); //Sets titleFont to Libgdx default font
    private Color titleColor = new Color(226F/255F, 223F/255F,164F/255F, 1);

    private Image background = new Image(menuBackground);

    //Menu buttons, their font, style and texture
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle myTextButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");

    public MenuScreen(final GameManager game){
        super(game);
        titleFont.setColor(titleColor);
        titleFont.getData().setScale(4);

        //Adds textures to the Skin, sets Skin for Button Up and Down
        skin.addRegions(buttonAtlas);
        myTextButtonStyle.font = skin.getFont("buttonTitle");
        myTextButtonStyle.up = skin.getDrawable("buttonUp");
        myTextButtonStyle.down = skin.getDrawable("buttonDown");

        //Draws Menu Title and Background
        mainStage.addActor(background);
        this.background.setSize(viewwidth, viewheight);

        musicSetup("heroic-age.mp3", true);

        /**
         * Creates Text buttons for the menu, Sets them up and Adds listeners to switch to correct screen
         */
        TextButton newGame = new TextButton("New Game", myTextButtonStyle);
        TextButton loadGame = new TextButton("Load Game", myTextButtonStyle);
        TextButton exitGame = new TextButton("Exit Game", myTextButtonStyle);
        Table table = new Table();

        table.row().size(viewwidth/8f, viewheight/10f).padBottom(viewheight/24f);
        table.add(newGame);
        table.row().size(viewwidth/8f, viewheight/10f).padBottom(viewheight/24f);
        table.add(loadGame);
        table.row().size(viewwidth/8f, viewheight/10f).fill();
        table.add(exitGame);

        table.setFillParent(true);
        table.center();
        mainStage.addActor(table);

        newGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.debug("Menu DEBUG", "New button pressed");
                changeScreen(new SailingScreen(game, true));
            }
        });

        loadGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.debug("Menu DEBUG", "Load button pressed");
                changeScreen(new SailingScreen(loadGame(prefs), false));
            }
        });

        exitGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.debug("Menu DEBUG", "Exit button pressed");
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void update(float delta){ }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {


    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        menuBackground.dispose();
        titleFont.dispose();
        buttonFont.dispose();
        skin.dispose();
        buttonAtlas.dispose();
        batch.dispose();
    }
}
