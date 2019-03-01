package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import uk.ac.york.sepr4.ahod2.GameInstance;

/***
 * Screen class used to fade between AHODScreens.
 */
public class TransitionScreen implements Screen {

    private AHODScreen fadeIn, fadeOut;
    private GameInstance gameInstance;

    //fading variables
    private boolean fading = true, dispose;
    private float fadeTime = 0.4f, fadeTimer;

    public TransitionScreen(GameInstance gameInstance, AHODScreen fadeOut, AHODScreen fadeIn, boolean dispose) {
        this.gameInstance = gameInstance;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        this.dispose = dispose;

        fadeIn.setFading(true);
        fadeIn.setFade(0);
        fadeOut.setFade(0);
        fadeOut.setFading(true);
    }

    @Override
    public void render(float delta) {

        if (fading) {
            //fading out
            fadeTimer += delta;
            fadeOut.setFade(fadeTimer / fadeTime);
            fadeOut.render(delta);

        } else {
            //fading in
            fadeTimer -= delta;
            fadeIn.setFade(fadeTimer / fadeTime);
            fadeIn.render(delta);
        }

        //finished fading in or out
        if (fadeTimer >= fadeTime || fadeTimer < 0) {
            if (fading) {
                fading = false;
            } else {
                //switch to fadeIn screen
                fadeOut.setFading(false);
                fadeIn.setFading(false);
                gameInstance.switchScreen(fadeIn);
                if (dispose) {
                    //dispose of fadeOut screen
                    fadeOut.dispose();
                }
                return;
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
    }
}
