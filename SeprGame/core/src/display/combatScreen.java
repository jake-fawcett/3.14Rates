package display;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class combatScreen implements Screen {
    private boolean isCollegeBattle;

    private SpriteBatch batch = new SpriteBatch();
    private Texture battleBackground = new Texture("battleBackground.png");


    public combatScreen(Boolean isCollegeBattle) {
        this.isCollegeBattle = isCollegeBattle;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(battleBackground,0,0);
        batch.end();
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
}
