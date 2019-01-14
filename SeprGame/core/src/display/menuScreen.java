package display;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import game_manager.GameManager;

public class menuScreen extends InputListener implements Screen {
    private SpriteBatch batch = new SpriteBatch();

    private Texture menuBackground = new Texture("menuBackground.png");
    private BitmapFont titleFont = new BitmapFont(); //Sets titleFont to Libgdx default font
    private Color titleColor = new Color(226F/255F, 223F/255F,164F/255F, 1);

    //Stage used to display buttons
    private Stage stage = new Stage();

    //Menu buttons, their font, style and texture
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle myTextButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
    private Skin skin = new Skin();
    private TextButton runCombat;
    private TextButton runCollege;
    private TextButton runDepartment;
    private TextButton exitGame;

    private GameManager gameManager = new GameManager();

    private Game game;
    public menuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        titleFont.setColor(titleColor);
        titleFont.getData().setScale(4);

        //Adds textures to the Skin, sets Skin for Button Up and Down
        skin.addRegions(buttonAtlas);
        myTextButtonStyle.font = buttonFont;
        myTextButtonStyle.up = skin.getDrawable("buttonUp");
        myTextButtonStyle.down = skin.getDrawable("buttonDown");

        runCombat = new TextButton("Run Combat", myTextButtonStyle);
        runCollege = new TextButton("Run College", myTextButtonStyle);
        runDepartment = new TextButton("Run Department", myTextButtonStyle);
        exitGame = new TextButton("Exit Game", myTextButtonStyle);

        stage.addActor(runCombat);
        runCombat.setPosition(Gdx.graphics.getWidth()/2f - 175, 700);
        runCombat.setTransform(true); //Allows the Button to be Scaled
        runCombat.setScale(3);
        runCombat.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new combatScreen(false));
                return true;
            }
        });

        stage.addActor(runCollege);
        runCollege.setPosition(Gdx.graphics.getWidth()/2f - 175, 580);
        runCollege.setTransform(true); //Allows the Button to be Scaled
        runCollege.setScale(3);
        runCollege.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new combatScreen(true));
                return true;
            }
        });

        stage.addActor(runDepartment);
        runDepartment.setPosition(Gdx.graphics.getWidth()/2f - 175, 460);
        runDepartment.setTransform(true); //Allows the Button to be Scaled
        runDepartment.setScale(3);
        runDepartment.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new departmentScreen());
                return true;
            }
        });

        stage.addActor(exitGame);
        exitGame.setPosition(Gdx.graphics.getWidth()/2f - 175, 340);
        exitGame.setTransform(true); //Allows the Button to be Scaled
        exitGame.setScale(3);
        exitGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.exit(0);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        batch.begin();
        batch.draw(menuBackground,0,0);
        titleFont.draw(batch, "SEPR GAME", Gdx.graphics.getWidth()/2f - 160, 900);
        batch.end();

        stage.draw();

        gameManager.render();
    }

    @Override
    public void resize(int width, int height) {

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
        menuBackground.dispose();
        titleFont.dispose();
        buttonFont.dispose();
        skin.dispose();
        buttonAtlas.dispose();
        batch.dispose();
        stage.dispose();
    }
}
