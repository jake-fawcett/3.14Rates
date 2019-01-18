package display;

import banks.CoordBank;
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
import combat.ship.Ship;
import game_manager.GameManager;

public class combatScreen2 implements Screen {
    private boolean isCollegeBattle;

    public combatScreen2(Boolean isCollegeBattle) {
        this.isCollegeBattle = isCollegeBattle;
    }

    private GameManager game = new GameManager(null, null);
    private Ship playerShip = game.getPlayerShip();

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        drawBackground();
        drawFriendlyShip();

        drawHealthBar();
        drawIndicators();

        batch.end();

        stage.act();
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
        Texture hpBackground = new Texture("background.png");
        Texture hpDisabledBackground = new Texture("disabledBackground.png");

        ProgressBar.ProgressBarStyle hpBarStyle = new ProgressBar.ProgressBarStyle();
        hpBarStyle.background = new TextureRegionDrawable( new TextureRegion(hpBackground));
        hpBarStyle.disabledBackground = new TextureRegionDrawable( new TextureRegion(hpDisabledBackground));

        ProgressBar hpBar = new ProgressBar(0,1000,10,false,hpBarStyle);
        hpBar.setWidth(320);
        hpBar.setHeight(64);
        hpBar.setPosition(25,950);

        stage.addActor(hpBar);

        hpBar.setValue(500);
    } //FIXME

    public void drawIndicators(){
        BitmapFont indicatorFont = new BitmapFont();
        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + game.getPoints(), 25, 965);
        indicatorFont.draw(batch, "Gold: " + game.getGold(), 110, 965);
        indicatorFont.draw(batch, "Food: " + game.getFood(), 195, 965);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), 280, 965);
    }

}
