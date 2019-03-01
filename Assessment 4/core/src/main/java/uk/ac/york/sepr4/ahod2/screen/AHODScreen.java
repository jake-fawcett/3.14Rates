package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Data;
import uk.ac.york.sepr4.ahod2.GameInstance;

@Data
public abstract class AHODScreen implements Screen {

    //variables for use by implementing classes.
    private Stage stage;
    private Texture background;
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    //HUD-related variables
    private boolean enableMessageHUD = false, enableStatsHUD = false, enableAnimationsHUD = false;
    private GameInstance gameInstance;

    //transition variables
    private boolean fading = false;
    private float fade = 0;

    public AHODScreen(Stage stage, Texture background) {
        this.stage = stage;
        this.background = background;

        //set input multiplexer as processor (allows classes to add if required)
        inputMultiplexer.addProcessor(stage);
    }

    /***
     * Enable MessageHUD on this screen.
     * @param gameInstance instance with which to get message data from.
     */
    public void setMessageHUD(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        enableMessageHUD = true;
        inputMultiplexer.addProcessor(gameInstance.getMessageHUD().getHudStage());
    }

    /***
     * Enable StatsHUD on this screen.
     * @param gameInstance instance with which to get stats data from.
     */
    public void setStatsHUD(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        enableStatsHUD = true;
    }

    /***
     * Enable AnimationsHUD on this screen.
     * @param gameInstance instance with which to get animation data from.
     */
    public void setAnimationsHUD(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
        enableAnimationsHUD = true;
    }

    /***
     * Main screen render;
     * Clear screen and draw background.
     * Run renderInner (used by implementing classes).
     * Apply fade overlay if fading.
     * Update HUDs.
     * @param delta time since last render
     */
    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawBackground();
        renderInner(delta);
        if (isFading()) {
            applyFadeOverlay();
        }
        stage.act(delta);
        stage.draw();
        if (enableMessageHUD) {
            gameInstance.getMessageHUD().update(delta);
        }
        if (enableStatsHUD) {
            gameInstance.getStatsHud().update();
        }
        if (enableAnimationsHUD) {
            gameInstance.getAnimationHUD().update(delta);
        }
    }

    public abstract void renderInner(float delta);

    /***
     * Get lower edge of camera.
     * @return vector representing lower corner (bottom left) of camera pos
     */
    public Vector2 getCameraLowerBound() {
        Vector2 pos = new Vector2(stage.getCamera().position.x, stage.getCamera().position.y);
        pos.add(-stage.getWidth() / 2, -stage.getHeight() / 2);
        return pos;
    }

    /***
     * Apply fade overlay.
     * (Draw rectangle over screen)
     */
    public void applyFadeOverlay() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, fade));
        shapeRenderer.rect(0, getCameraLowerBound().y, stage.getWidth(), stage.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    /***
     * Draw screen's background.
     */
    private void drawBackground() {
        //sets background texture
        getBatch().begin();
        Texture texture = background;
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        getBatch().draw(texture, 0, 0, stage.getWidth(), stage.getHeight());
        getBatch().end();
    }

    public OrthographicCamera getOrthographicCamera() {
        return (OrthographicCamera) stage.getViewport().getCamera();
    }

    public Batch getBatch() {
        return stage.getBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void resize(int width, int height) {
        //resizing causes errors with sailscreen
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
