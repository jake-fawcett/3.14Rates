package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

    private Texture crewQuartersTexture = new Texture("crewQuaters.png");
    private TextureRegionDrawable crewQuartersDrawable = new TextureRegionDrawable(new TextureRegion(crewQuartersTexture));
    private ImageButton enemyCrewQuarters = new ImageButton(crewQuartersDrawable);
    private Sprite friendlyCrewQuaters = new Sprite(crewQuartersTexture);

    private Texture crowsNestTexture = new Texture("crowsNest.png");
    private TextureRegionDrawable crowsNestDrawable = new TextureRegionDrawable(new TextureRegion(crowsNestTexture));
    private ImageButton enemyCrowsNest = new ImageButton(crowsNestDrawable);
    private Sprite friendlyCrowsNest = new Sprite(crowsNestTexture);

    private Texture gunDeckTexture = new Texture("gunDeck.png");
    private TextureRegionDrawable gunDeckDrawable = new TextureRegionDrawable(new TextureRegion(gunDeckTexture));
    private ImageButton enemyGunDeck = new ImageButton(gunDeckDrawable);
    private Sprite friendlyGunDeck = new Sprite(gunDeckTexture);

    private Texture helmTexture = new Texture("helm.png");
    private TextureRegionDrawable helmDrawable = new TextureRegionDrawable(new TextureRegion(helmTexture));
    private ImageButton enemyHelm = new ImageButton(helmDrawable);
    private Sprite friendlyHelm = new Sprite(helmTexture);

    private Texture emptyRoomTexture = new Texture("emptyRoom.png");
    private TextureRegionDrawable emptyRoomDrawable = new TextureRegionDrawable(new TextureRegion(emptyRoomTexture));
    private ImageButton enemyEmptyRoom1 = new ImageButton(emptyRoomDrawable);
    private ImageButton enemyEmptyRoom2 = new ImageButton(emptyRoomDrawable);
    private ImageButton enemyEmptyRoom3 = new ImageButton(emptyRoomDrawable);
    private ImageButton enemyEmptyRoom4 = new ImageButton(emptyRoomDrawable);
    private Sprite friendlyEmptyRoom1 = new Sprite(emptyRoomTexture);
    private Sprite friendlyEmptyRoom2 = new Sprite(emptyRoomTexture);
    private Sprite friendlyEmptyRoom3 = new Sprite(emptyRoomTexture);
    private Sprite friendlyEmptyRoom4 = new Sprite(emptyRoomTexture);

    //TODO Add button function, Track most recent room pressed

    @Override
    public void show() {
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);



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
