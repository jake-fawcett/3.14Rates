package display;

import banks.CoordBank;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sun.org.apache.xpath.internal.operations.Bool;
import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Ship;
import game_manager.GameManager;
import location.Department;
import other.Resource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class combatScreen implements Screen {

    private Game game;
    private Boolean isCollegeBattle;
    public combatScreen(Game game, Boolean isCollegeBattle){
        this.game = game;
        this.isCollegeBattle = isCollegeBattle;
    }

    private GameManager gameManager = new GameManager(null, null);
    private Ship playerShip = gameManager.getPlayerShip();

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas;
    private Skin skin = new Skin();

    private String roomSelected;

    @Override
    public void show() {
        buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        drawBackground();
        drawFriendlyShip();
        drawEnemyShip();

        buttonToMenu(textButtonStyle);

        drawHealthBar();
        drawIndicators();

        batch.end();

        stage.draw();
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

    }

    public void drawBackground() {
        Texture background = new Texture("battleBackground.png");
        batch.draw(background, 0, 0);
    }

    public void drawFriendlyShip(){
        TextureAtlas roomSpriteAtlas = new TextureAtlas("roomSpriteSheet.txt");

        Sprite friendlyCrewQuaters = roomSpriteAtlas.createSprite("crewQuaters");
        friendlyCrewQuaters.setPosition(CoordBank.FRIENDLY_ROOM1.getX(),CoordBank.FRIENDLY_ROOM1.getY());

        Sprite friendlyEmptyRoom1 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom1.setPosition(CoordBank.FRIENDLY_ROOM2.getX(),CoordBank.FRIENDLY_ROOM2.getY());

        Sprite friendlyCrowsNest = roomSpriteAtlas.createSprite("crowsNest");
        friendlyCrowsNest.setPosition(CoordBank.FRIENDLY_ROOM3.getX(),CoordBank.FRIENDLY_ROOM3.getY());

        Sprite friendlyGunDeck = roomSpriteAtlas.createSprite("gunDeck");
        friendlyGunDeck.setPosition(CoordBank.FRIENDLY_ROOM4.getX(),CoordBank.FRIENDLY_ROOM4.getY());

        Sprite friendlyEmptyRoom2 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom2.setPosition(CoordBank.FRIENDLY_ROOM5.getX(),CoordBank.FRIENDLY_ROOM5.getY());

        Sprite friendlyHelm = roomSpriteAtlas.createSprite("helm");
        friendlyHelm.setPosition(CoordBank.FRIENDLY_ROOM6.getX(),CoordBank.FRIENDLY_ROOM6.getY());

        Sprite friendlyEmptyRoom3 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom3.setPosition(CoordBank.FRIENDLY_ROOM7.getX(),CoordBank.FRIENDLY_ROOM7.getY());

        Sprite friendlyEmptyRoom4 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom4.setPosition(CoordBank.FRIENDLY_ROOM8.getX(),CoordBank.FRIENDLY_ROOM8.getY());

        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyHelm.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
    }

    public void drawHealthBar() {
        Texture hpBar = new Texture("background.png");
        Texture hpBackground = new Texture("disabledBackground.png");

        double defaultWidth = 320;
        int width = (int)(defaultWidth * ((double)playerShip.getHullHP() / (double)playerShip.getBaseHullHP()));

        batch.draw(hpBackground,25, 970, 320, 16);
        batch.draw(hpBar,25, 970, width, 16);
    }

    public void drawIndicators(){
        BitmapFont indicatorFont = new BitmapFont();
        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + gameManager.getPoints(), 25, 965);
        indicatorFont.draw(batch, "Gold: " + gameManager.getGold(), 110, 965);
        indicatorFont.draw(batch, "Food: " + gameManager.getFood(), 195, 965);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), 280, 965);
    }

    public void buttonToMenu(TextButton.TextButtonStyle textButtonStyle){
        TextButton toMenu = new TextButton("To Menu", textButtonStyle);
        toMenu.setPosition(880, 980);
        toMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new menuScreen(game));
                return true;
            }
        });
        stage.addActor(toMenu);
    }

    public void drawEnemyShip(){
        TextureAtlas roomButtonAtlas = new TextureAtlas("roomSpriteSheet.txt");
        Skin roomButtonSkin = new Skin();
        roomButtonSkin.addRegions(roomButtonAtlas);

        ImageButton.ImageButtonStyle crewQuatersStyle = new ImageButton.ImageButtonStyle(), crowsNestStyle = new ImageButton.ImageButtonStyle(), gunDeckStyle = new ImageButton.ImageButtonStyle(), helmStyle = new ImageButton.ImageButtonStyle(), emptyStyle = new ImageButton.ImageButtonStyle();
        crewQuatersStyle.up = roomButtonSkin.getDrawable("crewQuaters");
        crewQuatersStyle.checked = roomButtonSkin.getDrawable("crewQuatersTargetted");
        crowsNestStyle.up = roomButtonSkin.getDrawable("crowsNest");
        crowsNestStyle.checked = roomButtonSkin.getDrawable("crowsNestTargetted");
        gunDeckStyle.up = roomButtonSkin.getDrawable("gunDeck");
        gunDeckStyle.checked = roomButtonSkin.getDrawable("gunDeckTargetted");
        helmStyle.up = roomButtonSkin.getDrawable("helm");
        helmStyle.checked = roomButtonSkin.getDrawable("helmTargetted");
        emptyStyle.up = roomButtonSkin.getDrawable("EmptyRoom");
        emptyStyle.checked = roomButtonSkin.getDrawable("EmptyRoomTargetted");

        ImageButton enemyCrewQuarters = new ImageButton(crewQuatersStyle), enemyCrowsNest = new ImageButton(crowsNestStyle), enemyGunDeck = new ImageButton(gunDeckStyle), enemyHelm = new ImageButton(helmStyle),
                enemyEmpty1 = new ImageButton(emptyStyle), enemyEmpty2 = new ImageButton(emptyStyle), enemyEmpty3 = new ImageButton(emptyStyle), enemyEmpty4 = new ImageButton(emptyStyle);

        ButtonGroup roomButtonGroup = new ButtonGroup(enemyCrewQuarters, enemyCrowsNest, enemyGunDeck, enemyHelm, enemyEmpty1, enemyEmpty2, enemyEmpty3, enemyEmpty4);
        roomButtonGroup.setMaxCheckCount(1);

        final List<ImageButton> roomButtonList = new ArrayList<ImageButton>();
        roomButtonList.add(enemyCrewQuarters);
        roomButtonList.add(enemyCrowsNest);
        roomButtonList.add(enemyGunDeck);
        roomButtonList.add(enemyHelm);
        roomButtonList.add(enemyEmpty1);
        roomButtonList.add(enemyEmpty2);
        roomButtonList.add(enemyEmpty3);
        roomButtonList.add(enemyEmpty4);

        int i = 0;
        while (i < roomButtonList.size()){
            stage.addActor(roomButtonList.get(i));
            i++;
        }

        enemyCrewQuarters.setPosition(700, 256);
        enemyEmpty1.setPosition(828, 256);
        enemyCrowsNest.setPosition(700, 384);
        enemyEmpty2.setPosition(828, 384);
        enemyGunDeck.setPosition(700, 512);
        enemyEmpty3.setPosition(828, 512);
        enemyEmpty4.setPosition(700, 640);
        enemyHelm.setPosition(828, 640);


        roomButtonList.get(i).addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            roomSelected =
            return true;
            }
        });


    }
}
