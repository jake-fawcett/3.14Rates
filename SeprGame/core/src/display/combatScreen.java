package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import game_manager.GameManager;

public class combatScreen implements Screen {
    private boolean isCollegeBattle;

    public combatScreen(Boolean isCollegeBattle) {
        this.isCollegeBattle = isCollegeBattle;
    }

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    private Texture hpBackground = new Texture("background.png");
    private Texture hpDisabledBackground = new Texture("disabledBackground.png");
    private ProgressBar.ProgressBarStyle healthBarStyle = new ProgressBar.ProgressBarStyle();
    private ProgressBar healthBar;

    private int score;
    private String scoreName;
    private int gold;
    private String goldName;
    private int crew;
    private String crewName;
    private int food;
    private String foodName;
    private BitmapFont indicatorFont = new BitmapFont();

    private Texture battleBackground = new Texture("battleBackground.png");
    private Texture shipBackground = new Texture("shipBackground.png");
    private Sprite playerShipBackground = new Sprite(shipBackground);
    private Sprite enemyShipBackground = new Sprite(shipBackground);

    private TextureAtlas buttonAtlas = new TextureAtlas("roomSpriteSheet.txt");
    private Skin skin = new Skin();

    private ImageButton.ImageButtonStyle crewQuatersStyle = new ImageButton.ImageButtonStyle();
    private ImageButton.ImageButtonStyle crowsNestStyle = new ImageButton.ImageButtonStyle();
    private ImageButton.ImageButtonStyle gunDeckStyle = new ImageButton.ImageButtonStyle();
    private ImageButton.ImageButtonStyle helmStyle = new ImageButton.ImageButtonStyle();
    private ImageButton.ImageButtonStyle emptyRoomStyle = new ImageButton.ImageButtonStyle();

    private Sprite friendlyCrewQuaters = buttonAtlas.createSprite("crewQuaters");
    private Sprite friendlyCrowsNest = buttonAtlas.createSprite("crowsNest");
    private Sprite friendlyGunDeck = buttonAtlas.createSprite("gunDeck");
    private Sprite friendlyHelm = buttonAtlas.createSprite("helm");
    private Sprite friendlyEmptyRoom1 = buttonAtlas.createSprite("EmptyRoom");
    private Sprite friendlyEmptyRoom2 = buttonAtlas.createSprite("EmptyRoom");
    private Sprite friendlyEmptyRoom3 = buttonAtlas.createSprite("EmptyRoom");
    private Sprite friendlyEmptyRoom4 = buttonAtlas.createSprite("EmptyRoom");

    private ImageButton enemyCrewQuarters;
    private ImageButton enemyCrowsNest;
    private ImageButton enemyGunDeck;
    private ImageButton enemyHelm;
    private ImageButton enemyEmptyRoom1;
    private ImageButton enemyEmptyRoom2;
    private ImageButton enemyEmptyRoom3;
    private ImageButton enemyEmptyRoom4;

    private ButtonGroup roomButtonGroup;
    private String roomSelected;


    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle myTextButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas textButtonAtlas = new TextureAtlas("weaponButtonSpriteSheet.txt");
    private Skin textButtonSkin = new Skin();
    private TextButton weapon1;
    private TextButton weapon2;
    private TextButton weapon3;
    private TextButton weapon4;
    private TextButton weaponFire;
    private ButtonGroup weaponButtonGroup;

    private String weaponSelected;

    private GameManager game;

    //TODO Add button function, Track most recent room pressed

    @Override
    public void show() {

        //Health Bar
        healthBarStyle.background = new TextureRegionDrawable(new TextureRegion(hpBackground));
        healthBarStyle.disabledBackground = new TextureRegionDrawable(new TextureRegion(hpDisabledBackground));
        healthBar = new ProgressBar(0,1000,1,false, healthBarStyle);
        healthBar.setValue(500);
        healthBar.setWidth(256);
        healthBar.setHeight(64);
        healthBar.setPosition(50,950);

        stage.addActor(healthBar);

        score = 0;
        scoreName = "Score: 0";

        food = 0;
        foodName = "Food: 0";

        gold = 0;
        goldName = "Gold: 0";

        crew = 0;
        crewName = "Crew: 0";

        //Room Buttons
        skin.addRegions(buttonAtlas);

        crewQuatersStyle.up = skin.getDrawable("crewQuaters");
        crewQuatersStyle.checked = skin.getDrawable("crewQuatersTargetted");
        crowsNestStyle.up = skin.getDrawable("crowsNest");
        crowsNestStyle.checked = skin.getDrawable("crowsNestTargetted");
        gunDeckStyle.up = skin.getDrawable("gunDeck");
        gunDeckStyle.checked = skin.getDrawable("gunDeckTargetted");
        helmStyle.up = skin.getDrawable("helm");
        helmStyle.checked = skin.getDrawable("helmTargetted");
        emptyRoomStyle.up = skin.getDrawable("EmptyRoom");
        emptyRoomStyle.checked = skin.getDrawable("EmptyRoomTargetted");

        enemyCrewQuarters = new ImageButton(crewQuatersStyle);
        enemyCrowsNest = new ImageButton(crowsNestStyle);
        enemyGunDeck = new ImageButton(gunDeckStyle);
        enemyHelm = new ImageButton(helmStyle);
        enemyEmptyRoom1 = new ImageButton(emptyRoomStyle);
        enemyEmptyRoom2 = new ImageButton(emptyRoomStyle);
        enemyEmptyRoom3 = new ImageButton(emptyRoomStyle);
        enemyEmptyRoom4 = new ImageButton(emptyRoomStyle);

        stage.addActor(enemyCrewQuarters);
        stage.addActor(enemyCrowsNest);
        stage.addActor(enemyGunDeck);
        stage.addActor(enemyHelm);
        stage.addActor(enemyEmptyRoom1);
        stage.addActor(enemyEmptyRoom2);
        stage.addActor(enemyEmptyRoom3);
        stage.addActor(enemyEmptyRoom4);

        playerShipBackground.setPosition(100,256);
        friendlyCrewQuaters.setPosition(100,256);
        friendlyCrowsNest.setPosition(228,256);
        friendlyEmptyRoom1.setPosition(100,384);
        friendlyEmptyRoom2.setPosition(228,384);
        friendlyGunDeck.setPosition(100,512);
        friendlyEmptyRoom3.setPosition(228,512);
        friendlyEmptyRoom4.setPosition(100,640);
        friendlyHelm.setPosition(228,640);

        enemyShipBackground.setPosition(700,256);
        enemyEmptyRoom1.setPosition(700,256);
        enemyCrowsNest.setPosition(828,256);
        enemyEmptyRoom2.setPosition(700,384);
        enemyGunDeck.setPosition(828,384);
        enemyHelm.setPosition(700, 512);
        enemyEmptyRoom3.setPosition(828,512);
        enemyEmptyRoom4.setPosition(700,640);
        enemyCrewQuarters.setPosition(828, 640);

        roomButtonGroup = new ButtonGroup(enemyCrowsNest, enemyGunDeck, enemyHelm, enemyCrewQuarters, enemyEmptyRoom1, enemyEmptyRoom2, enemyEmptyRoom3, enemyEmptyRoom4);
        roomButtonGroup.setMaxCheckCount(1);

        enemyCrowsNest.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "crowsNest";
                return true;
            }
        });

        enemyGunDeck.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "gunDeck";
                return true;
            }
        });

        enemyHelm.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "helm";
                return true;
            }
        });

        enemyCrewQuarters.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "crewQuaters";
                return true;
            }
        });

        enemyEmptyRoom1.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "emptyRoom1";
                return true;
            }
        });

        enemyEmptyRoom2.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "emptyRoom2";
                return true;
            }
        });

        enemyEmptyRoom3.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "emptyRoom3";
                return true;
            }
        });

        enemyEmptyRoom4.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = "emptyRoom4";
                return true;
            }
        });

        //Weapon Buttons
        textButtonSkin.addRegions(textButtonAtlas);
        myTextButtonStyle.font = buttonFont;
        myTextButtonStyle.up = textButtonSkin.getDrawable("weaponButtonUp");
        myTextButtonStyle.down = textButtonSkin.getDrawable("weaponButtonDown");
        myTextButtonStyle.checked = textButtonSkin.getDrawable("weaponButtonChecked");

        weapon1 = new TextButton("Weapon 1",myTextButtonStyle);
        weapon2 = new TextButton("Weapon 2",myTextButtonStyle);
        weapon3 = new TextButton("Weapon 3",myTextButtonStyle);
        weapon4 = new TextButton("Weapon 4",myTextButtonStyle);
        weaponFire = new TextButton("Fire!",myTextButtonStyle);

        weaponButtonGroup = new ButtonGroup(weapon1, weapon2, weapon3, weapon4);
        weaponButtonGroup.setMaxCheckCount(1);

        weapon1.setTransform(true);
        weapon1.setScale(1,1.5f);
        weapon2.setTransform(true);
        weapon2.setScale(1,1.5f);
        weapon3.setTransform(true);
        weapon3.setScale(1, 1.5f);
        weapon4.setTransform(true);
        weapon4.setScale(1,1.5f);
        weaponFire.setTransform(true);
        weaponFire.setScale(1,1.5f);

        stage.addActor(weapon1);
        stage.addActor(weapon2);
        stage.addActor(weapon3);
        stage.addActor(weapon4);
        stage.addActor(weaponFire);

        weapon1.setPosition(50, 50);
        weapon2.setPosition(175, 50);
        weapon3.setPosition(300, 50);
        weapon4.setPosition(425, 50);
        weaponFire.setPosition(575,50);

        weapon1.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                weaponSelected = "weapon1";
                return true;
            }
        });

        weapon2.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                weaponSelected = "weapon2";
                return true;
            }
        });

        weapon3.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                weaponSelected = "weapon3";
                return true;
            }
        });

        weapon4.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                weaponSelected = "weapon4";
                return true;
            }
        });

        weaponFire.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                //TODO Add WeaponFiring Capability (Pass into CombatPlayer -> Combat Manager)
                weaponButtonGroup.uncheckAll();
                roomButtonGroup.uncheckAll();
                weaponFire.setChecked(false);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        //gold = game.getGold();
        //food = game.getFood();
        //score = game.getPoints();
        // TODO crew + above broken

        batch.begin();
        batch.draw(battleBackground,0,0);
        playerShipBackground.draw(batch);
        enemyShipBackground.draw(batch);

        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
        friendlyHelm.draw(batch);

        indicatorFont.setColor(1f,1f,1f,1f);
        indicatorFont.draw(batch, scoreName, 55, 970);
        indicatorFont.draw(batch, goldName, 120, 970);
        indicatorFont.draw(batch, crewName, 185, 970);
        indicatorFont.draw(batch, foodName, 250, 970);

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
        batch.dispose();
    }
}
