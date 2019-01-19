package com.mygdx.seprgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game_manager.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.height = 1024;
		config.width = 1024;
		config.fullscreen = false;

		new LwjglApplication(new GameManager(null,null), config);
	}
}
