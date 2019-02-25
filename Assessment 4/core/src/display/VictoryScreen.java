package display;

import base.BaseScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import game_manager.GameManager;

public class VictoryScreen extends BaseScreen {
	private Texture endBackground = new Texture("endBackground.png");
	private Image background = new Image(endBackground);

	public VictoryScreen(final GameManager game) {
		super(game);
		musicSetup("heroic-age.mp3", true);
	}

	@Override
	public void update(float delta) { }

	@Override
	public void show() {
		mainStage.addActor(background);
		this.background.setSize(viewwidth, viewheight);
	}

	@Override
	public void render(float delta) { super.render(delta); }

	@Override
	public void resize(int width, int height) { super.resize(width, height); }

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose() {
		super.dispose();
		endBackground.dispose();
		skin.dispose();
	}
}



