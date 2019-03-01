package uk.ac.york.sepr4.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uk.ac.york.sepr4.PirateGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "A Pirate Game";
        config.width = 1024;
        config.height = 768;
		new LwjglApplication(new PirateGame(), config);
	}
}
