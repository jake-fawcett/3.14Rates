package uk.ac.york.sepr4.ahod2.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import uk.ac.york.sepr4.ahod2.screen.sail.SailScreen;

/***
 * Class responsible for handling user input on SailScreen.
 */
public class SailInputProcessor implements InputProcessor {

    private SailScreen sailScreen;

    private boolean up, down;
    private float scrollAmount = 10f;

    public SailInputProcessor(SailScreen sailScreen) {
        this.sailScreen = sailScreen;
    }

    /***
     * Called during SailScreen render to pan camera up and down the level's map.
     */
    public void scrollCamera() {
        //set map top and bottom relative to camera's position and screen height.
        float cameraYBottom = (sailScreen.getOrthographicCamera().position.y - Gdx.graphics.getHeight() / 2);
        float cameraYTop = (sailScreen.getOrthographicCamera().position.y + Gdx.graphics.getHeight() / 2);

        if (up) {
            if (cameraYTop + scrollAmount < sailScreen.getStage().getViewport().getWorldHeight()) {
                sailScreen.getOrthographicCamera().translate(0, scrollAmount);
            }
        }
        if (down) {
            if (cameraYBottom - scrollAmount > 0) {
                sailScreen.getOrthographicCamera().translate(0, -scrollAmount);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            up = false;
            down = true;
        }
        if (keycode == Input.Keys.UP) {
            down = false;
            up = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            down = false;
        }
        if (keycode == Input.Keys.UP) {
            up = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
