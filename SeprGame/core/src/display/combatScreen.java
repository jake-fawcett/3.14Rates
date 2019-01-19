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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.manager.CombatManager;
import combat.ship.Room;
import combat.ship.Ship;
import game_manager.GameManager;
import javafx.util.Pair;
import location.Department;
import other.Resource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static banks.CoordBank.*;
import static combat.ship.RoomFunction.*;
import static other.Constants.COOLDOWN_TICKS_PER_TURN;

public class combatScreen implements Screen {

    private Game game;
    private Boolean isCollegeBattle;
    public combatScreen(Game game, Boolean isCollegeBattle){
        this.game = game;
        this.isCollegeBattle = isCollegeBattle;
    }

    private GameManager gameManager = new GameManager(null, null);
    private Ship playerShip = gameManager.getPlayerShip();
    private CombatPlayer combatPlayer = gameManager.getCombatPlayer();
    private Ship enemyShip = gameManager.getEnemyShip();
    private CombatEnemy combatEnemy = gameManager.getCombatEnemy();
    private CombatManager combatManager = gameManager.getCombatManager();

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas;
    private Skin skin = new Skin();

    private Room roomSelected;
    private Weapon weaponSelected;

    private ButtonGroup weaponButtonGroup = new ButtonGroup();
    private ButtonGroup roomButtonGroup = new ButtonGroup();

    private Boolean firePressed;

    @Override
    public void show() {
        buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");

        drawEnemyShip();

        drawWeaponButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        drawBackground();
        drawFriendlyShip();
        buttonToMenu(textButtonStyle);

        drawHealthBar();
        drawIndicators();

        drawRoomHP();

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

    private void drawBackground() {
        Texture background = new Texture("battleBackground.png");
        batch.draw(background, 0, 0);
    }

    private void drawFriendlyShip(){
        TextureAtlas roomSpriteAtlas = new TextureAtlas("roomSpriteSheet.txt");

        Sprite friendlyCrewQuaters = roomSpriteAtlas.createSprite("crewQuaters");
        friendlyCrewQuaters.setPosition(CoordBank.FRIENDLY_CREWQUATERS.getX(),CoordBank.FRIENDLY_CREWQUATERS.getY());

        Sprite friendlyEmptyRoom1 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom1.setPosition(CoordBank.FRIENDLY_EMPTYROOM1.getX(),CoordBank.FRIENDLY_EMPTYROOM1.getY());

        Sprite friendlyCrowsNest = roomSpriteAtlas.createSprite("crowsNest");
        friendlyCrowsNest.setPosition(CoordBank.FRIENDLY_CROWSNEST.getX(),CoordBank.FRIENDLY_CROWSNEST.getY());

        Sprite friendlyGunDeck = roomSpriteAtlas.createSprite("gunDeck");
        friendlyGunDeck.setPosition(CoordBank.FRIENDLY_GUNDECK.getX(),CoordBank.FRIENDLY_GUNDECK.getY());

        Sprite friendlyEmptyRoom2 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom2.setPosition(CoordBank.FRIENDLY_EMPTYROOM2.getX(),CoordBank.FRIENDLY_EMPTYROOM2.getY());

        Sprite friendlyHelm = roomSpriteAtlas.createSprite("helm");
        friendlyHelm.setPosition(CoordBank.FRIENDLY_HELM.getX(),CoordBank.FRIENDLY_HELM.getY());

        Sprite friendlyEmptyRoom3 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom3.setPosition(CoordBank.FRIENDLY_EMPTYROOM3.getX(),CoordBank.FRIENDLY_EMPTYROOM3.getY());

        Sprite friendlyEmptyRoom4 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom4.setPosition(CoordBank.FRIENDLY_EMPTYROOM4.getX(),CoordBank.FRIENDLY_EMPTYROOM4.getY());

        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyHelm.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
    }

    private void drawHealthBar() {
        Texture hpBar = new Texture("background.png");
        Texture hpBackground = new Texture("disabledBackground.png");

        double defaultWidth = 320;
        int playerWidth = (int)(defaultWidth * ((double)playerShip.getHullHP() / (double)playerShip.getBaseHullHP()));
        int enemyWidth = (int)(defaultWidth * ((double)enemyShip.getHullHP() / (double)enemyShip.getBaseHullHP()));

        batch.draw(hpBackground,25, 900, 320, 16);
        batch.draw(hpBar,25, 900, playerWidth, 16);

        batch.draw(hpBackground, 675, 900, 320, 16);
        batch.draw(hpBar,675, 900, enemyWidth, 16);
    }

    private void drawIndicators(){
        BitmapFont indicatorFont = new BitmapFont();
        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + gameManager.getPoints(), 25, 890);
        indicatorFont.draw(batch, "Gold: " + gameManager.getGold(), 110, 890);
        indicatorFont.draw(batch, "Food: " + gameManager.getFood(), 195, 890);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), 280, 890);
    }

    private void buttonToMenu(TextButton.TextButtonStyle textButtonStyle){
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

    //FIXME PeaShooter is Checked on Start, Fire stays checked after click
    private void drawEnemyShip(){
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


        roomButtonGroup.add(enemyCrewQuarters, enemyCrowsNest, enemyGunDeck, enemyHelm, enemyEmpty1, enemyEmpty2, enemyEmpty3, enemyEmpty4);
        roomButtonGroup.setMaxCheckCount(1);
        roomButtonGroup.uncheckAll();

        enemyCrewQuarters.setPosition(ENEMY_CREWQUATERS.getX(), ENEMY_CREWQUATERS.getY());
        enemyEmpty1.setPosition(ENEMY_EMPTYROOM1.getX(), ENEMY_EMPTYROOM1.getY());
        enemyCrowsNest.setPosition(ENEMY_CROWSNEST.getX(), ENEMY_CROWSNEST.getY());
        enemyEmpty2.setPosition(ENEMY_EMPTYROOM2.getX(), ENEMY_EMPTYROOM2.getY());
        enemyGunDeck.setPosition(ENEMY_GUNDECK.getX(), ENEMY_GUNDECK.getY());
        enemyEmpty3.setPosition(ENEMY_EMPTYROOM3.getX(), ENEMY_EMPTYROOM3.getY());
        enemyEmpty4.setPosition(ENEMY_EMPTYROOM4.getX(), ENEMY_EMPTYROOM4.getY());
        enemyHelm.setPosition(ENEMY_HELM.getX(), ENEMY_HELM.getY());

        enemyCrewQuarters.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(CREW_QUARTERS);
                return true;
            }
        });
        enemyCrowsNest.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(CROWS_NEST);
                return true;
            }
        });
        enemyGunDeck.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(GUN_DECK);
                return true;
            }
        });
        enemyHelm.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(HELM);
                return true;
            }
        });
        enemyEmpty1.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });
        enemyEmpty2.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });
        enemyEmpty3.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });
        enemyEmpty4.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = playerShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });

        stage.addActor(enemyCrewQuarters);
        stage.addActor(enemyCrowsNest);
        stage.addActor(enemyGunDeck);
        stage.addActor(enemyHelm);
        stage.addActor(enemyEmpty1);
        stage.addActor(enemyEmpty2);
        stage.addActor(enemyEmpty3);
        stage.addActor(enemyEmpty4);
    }

    private void drawWeaponButtons() {
        TextureAtlas weaponButtonAtlas = new TextureAtlas("weaponButtonSpriteSheet.txt");
        Skin weaponButtonSkin = new Skin();
        weaponButtonSkin.addRegions(weaponButtonAtlas);

        TextButton.TextButtonStyle weaponButtonStyle = new TextButton.TextButtonStyle();
        weaponButtonStyle.up = weaponButtonSkin.getDrawable("weaponButtonUp");
        weaponButtonStyle.down = weaponButtonSkin.getDrawable("weaponButtonDown");
        weaponButtonStyle.checked = weaponButtonSkin.getDrawable("weaponButtonChecked");
        weaponButtonStyle.font = new BitmapFont();

        final List<Weapon> playerWeapons = playerShip.getWeapons();
        List<TextButton> weaponButtons = new ArrayList<TextButton>();

        weaponButtonGroup.setMaxCheckCount(1);
        weaponButtonGroup.uncheckAll();

        int i = 0;
        while (i < 4) {
            try {
                weaponButtons.add(new TextButton(playerWeapons.get(i).getName(), weaponButtonStyle));
            } catch (IndexOutOfBoundsException e) {
                weaponButtons.add(new TextButton("Empty Slot", weaponButtonStyle));
                weaponButtons.get(i).setDisabled(true);
            }
            weaponButtons.get(i).setTransform(true);
            weaponButtons.get(i).setScale(1, 1.5f);

            weaponButtonGroup.add(weaponButtons.get(i));

            weaponButtons.get(i).setPosition(50 + (125 * i), 50);
            stage.addActor(weaponButtons.get(i));
            i++;
        }


        final TextButton fire = new TextButton("Fire", weaponButtonStyle);
        combatPlayer.setFireButton(fire);
        fire.setTransform(true);
        fire.setScale(1,1.5f);
        fire.setPosition(575,50);
        ButtonGroup fireGroup = new ButtonGroup(fire);
        fireGroup.setMaxCheckCount(0);

        stage.addActor(fire);

        weaponButtons.get(0).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(0);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });
        weaponButtons.get(1).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(1);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });
        weaponButtons.get(2).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(2);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });
        weaponButtons.get(3).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(3);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });

        fire.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                weaponButtonGroup.uncheckAll();
                roomButtonGroup.uncheckAll();
                fire.setChecked(false);

                combatManager.combatLoop(combatPlayer, combatEnemy);
                combatManager.combatLoop(combatEnemy, combatPlayer);
                return true;
            }
        });
    }

    private void drawRoomHP(){
        BitmapFont roomHealthFont = new BitmapFont();
        roomHealthFont.setColor(1,1,1,1);

        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_CREWQUATERS.getX() + 10,FRIENDLY_CREWQUATERS.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(HELM).getHp(),FRIENDLY_HELM.getX() + 10,FRIENDLY_HELM.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_CROWSNEST.getX() + 10,FRIENDLY_CROWSNEST.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_GUNDECK.getX() + 10,FRIENDLY_GUNDECK.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_EMPTYROOM1.getX() + 10,FRIENDLY_EMPTYROOM1.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_EMPTYROOM2.getX() + 10,FRIENDLY_EMPTYROOM2.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_EMPTYROOM3.getX() + 10,FRIENDLY_EMPTYROOM3.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_EMPTYROOM4.getX() + 10,FRIENDLY_EMPTYROOM4.getY() + 22);
    }

}
