package uk.ac.york.sepr4;

import com.badlogic.gdx.Game;
import lombok.Getter;

public class PirateGame extends Game {

	private MenuScreen menuScreen;
	@Getter
	private GameScreen gameScreen;

	private MinigameScreen minigameScreen;
	public static PirateGame PIRATEGAME;
	public static PirateGame newpirategame;

	@Override
	public void create () {
		PIRATEGAME = this;
	    switchScreen(ScreenType.MENU);
	}

	public void gameOver() {
		gameScreen = null;
	}

	public void switchScreen(ScreenType screenType){
		switch (screenType) {
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
            case GAME:
                if(gameScreen == null) gameScreen = new GameScreen(this);
                this.setScreen(gameScreen);
                break;
			case MINIGAME: //added for assessment 3
				if(minigameScreen == null) minigameScreen = new MinigameScreen(this, gameScreen);
				this.setScreen(minigameScreen);
				break;
		}
	}

	//Added for Assessment 3
	public void restartGame(){
		PIRATEGAME = this;
		this.gameOver();
		switchScreen(ScreenType.GAME);
	}

	public void switchScreen(GameScreen activeGameScreen){
		this.setScreen(activeGameScreen);
	}
}
