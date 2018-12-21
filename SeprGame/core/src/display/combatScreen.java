package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class combatScreen implements Screen {
    private boolean isCollegeBattle;

    public combatScreen(Boolean isCollegeBattle) {
        this.isCollegeBattle = isCollegeBattle;
    }

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

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

    private ImageButton enemyCrewQuarters = new ImageButton(crewQuatersStyle);
    private ImageButton enemyCrowsNest = new ImageButton(crowsNestStyle);
    private ImageButton enemyGunDeck = new ImageButton(gunDeckStyle);
    private ImageButton enemyHelm = new ImageButton(helmStyle);
    private ImageButton enemyEmptyRoom1 = new ImageButton(emptyRoomStyle);
    private ImageButton enemyEmptyRoom2 = new ImageButton(emptyRoomStyle);
    private ImageButton enemyEmptyRoom3 = new ImageButton(emptyRoomStyle);
    private ImageButton enemyEmptyRoom4 = new ImageButton(emptyRoomStyle);

    //TODO Add button function, Track most recent room pressed

    @Override
    public void show() {
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

        enemyEmptyRoom1.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.print("Hello");
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);


        batch.begin();
        batch.draw(battleBackground,0,0);
        playerShipBackground.draw(batch);
        enemyShipBackground.draw(batch);
        playerShipBackground.draw(batch);
        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
        friendlyHelm.draw(batch);
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
