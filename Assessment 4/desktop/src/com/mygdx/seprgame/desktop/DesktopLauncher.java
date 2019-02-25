package com.mygdx.seprgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game_manager.GameManager;
import other.Difficulty;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "3.14rates";
		config.addIcon("icon128.png", Files.FileType.Internal);
		config.addIcon("icon32.png", Files.FileType.Internal);
		config.addIcon("icon16.png", Files.FileType.Internal);
		config.foregroundFPS = 60;
		config.backgroundFPS = config.foregroundFPS;
		config.forceExit = false;

		new LwjglApplication(new GameManager("Pirate", Difficulty.EASY), config);
	}
}
