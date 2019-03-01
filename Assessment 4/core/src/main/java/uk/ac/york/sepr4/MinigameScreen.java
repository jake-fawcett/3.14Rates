package uk.ac.york.sepr4;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.sepr4.object.entity.Player;

import java.util.Random;


/**
 * Added for assessment 3, manages instances of the minigame.
 */
public class MinigameScreen implements Screen, InputProcessor {

	private PirateGame pirateGame;
	private GameScreen gameScreen;
	private Stage stage;
	private String state = "menu";
	private SpriteBatch spriteBatch;
	private InputMultiplexer inputMultiplexer;

	//minigame variables
	private boolean playerAlive = true;
	private boolean enemyAlive = true;
	private boolean playerWon = false;
	private boolean playerDisqualified = false;
	private boolean gameStarted = false;
	private float countdown;
	private float enemyShotCountdown;
	private String difficulty; //easy, medium, hard, very hard

	public MinigameScreen(PirateGame pirateGame, GameScreen gameScreen){
		this.pirateGame = pirateGame;
		this.gameScreen = gameScreen;
		this.spriteBatch = new SpriteBatch();

		// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());

		// use input multiplexer to enable scene2d inputs and keyboard inputs at the same time
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(this);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void show(){

		showMenu();

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	/**
	 * Display the minigame's main menu.
	 */
	private void showMenu(){
		// clear existing UI, initialise scene2d objects
		stage.clear();
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		Skin skin = new Skin(Gdx.files.internal("default_skin/uiskin.json"));

		// instantiate labels and buttons
		Label minigameText = new Label("How difficult do you want your minigame to be? Higher difficulty means higher rewards!", skin);
		minigameText.setColor(0f, 0f, 0f, 1f);
		Label instructionText = new Label("Wait for the signal, then use the Z key to shoot before your opponent does.", skin);
		instructionText.setColor(0f, 0f, 0f, 1f);

		TextButton quitMinigame = new TextButton("Go back to game", skin);
		TextButton easyMinigame = new TextButton("Easy", skin);
		Label easyText = new Label(" (1 gold)", skin);
		easyText.setColor(0f, 0f, 0f, 1f);
		TextButton mediumMinigame = new TextButton("Medium", skin);
		Label mediumText = new Label(" (10 gold)", skin);
		mediumText.setColor(0f, 0f, 0f, 1f);
		TextButton hardMinigame = new TextButton("Hard", skin);
		Label hardText = new Label(" (20 gold)", skin);
		hardText.setColor(0f, 0f, 0f, 1f);
		TextButton veryhardMinigame = new TextButton("Very Hard", skin);
		Label veryhardText = new Label(" (50 gold)", skin);
		veryhardText.setColor(0f, 0f, 0f, 1f);

		// used to display player balance and later to enable/disable minigame buttons
		Player player = gameScreen.getEntityManager().getOrCreatePlayer();
		Integer money = player.getBalance();

		Label currentBalance = new Label("Balance: " + money.toString(), skin);
		currentBalance.setColor(0f, 0f, 0f, 1f);

		// declare UI layout
		table.add(minigameText).fillX().uniformX();
		table.row().pad(20, 0, 0, 0);
		table.add(currentBalance);
		table.row().pad(20, 0, 0, 0);
		table.add(easyMinigame).fillX().uniformX();
		table.add(easyText);
		table.row();
		table.add(mediumMinigame).fillX().uniformX();
		table.add(mediumText);
		table.row();
		table.add(hardMinigame).fillX().uniformX();
		table.add(hardText);
		table.row();
		table.add(veryhardMinigame).fillX().uniformX();
		table.add(veryhardText);
		table.row().pad(20, 0, 10, 0);
		table.add(quitMinigame).fillX().uniformX();
		table.row().pad(20,0,0,0);
		table.add(instructionText).fillX().uniformX();
		table.row();

		// declare button functionality based on current balance
		quitMinigame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				pirateGame.switchScreen(gameScreen);
			}
		});


		easyMinigame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficulty = "easy";
				player.deduceBalance(1);
				setMinigameStateGame();
			}
		});

		// disable button if player doesn't have enough money
		if (money < 1){
			easyMinigame.setTouchable(Touchable.disabled);
		}

		mediumMinigame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficulty = "medium";
				player.deduceBalance(10);
				setMinigameStateGame();
			}
		});

		if(money < 10){
			mediumMinigame.setTouchable(Touchable.disabled);
		}

		hardMinigame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficulty = "hard";
				player.deduceBalance(20);
				setMinigameStateGame();
			}
		});

		if(money < 20){
			hardMinigame.setTouchable(Touchable.disabled);
		}

		veryhardMinigame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficulty = "very hard";
				player.deduceBalance(50);
				setMinigameStateGame();
			}
		});

		if(money < 50){
			veryhardMinigame.setTouchable(Touchable.disabled);
		}
	}

	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// renders player and enemy sprites on screen once the minigame is started
		if(state.equals("game")){
			// displays game interface when the minigame is started
			showGame();

			// counts down time from "Ready..." to "FIRE!"
			if(this.countdown > 0){
				this.countdown -= Gdx.graphics.getDeltaTime();
			}

			// if the game has started, counts down enemy's shot time
			if (gameStarted){
				if(enemyShotCountdown > 0){
					enemyShotCountdown -= Gdx.graphics.getDeltaTime();
				} else {
					if (!playerDisqualified){
						handleShot("enemy");
					}
				}
			}

			// selects sprite for player and enemy depending on game state and difficulty
			Texture player;
			Texture enemy;
			if (playerAlive  && !enemyAlive) {
				player = new Texture(Gdx.files.internal("shootpirate/pirate_shooting.png"));
				enemy = new Texture(Gdx.files.internal("shootpirate/pirate_defeated.png"));
			} else if (playerAlive && enemyAlive){
				player = new Texture(Gdx.files.internal("shootpirate/pirate_holstered.png"));

				if(difficulty.equals("easy")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right_easy.png"));
				} else if(difficulty.equals("medium")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right_medium.png"));
				} else if(difficulty.equals("very hard")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right_veryhard.png"));
				} else {
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right.png"));
				}

			} else if (!playerAlive && enemyAlive){
				player = new Texture(Gdx.files.internal("shootpirate/pirate_defeated.png"));

				if(difficulty.equals("easy")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_shooting_right_easy.png"));
				} else if (difficulty.equals("medium")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_shooting_right_medium.png"));
				} else if (difficulty.equals("very hard")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_shooting_right_veryhard.png"));
				} else {
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_shooting_right.png"));
				}

			} else {
				// failsafe condition to prevent null pointer exception
				player = new Texture(Gdx.files.internal("shootpirate/pirate_holstered.png"));

				if(difficulty.equals("easy")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right_easy.png"));
				} else if(difficulty.equals("medium")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right_medium.png"));
				} else if(difficulty.equals("very hard")){
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right_veryhard.png"));
				} else {
					enemy = new Texture(Gdx.files.internal("shootpirate/pirate_holstered_right.png"));
				}
			}

			// render sprites
			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();
			spriteBatch.begin();
			spriteBatch.draw(player, w*0.1f, h-player.getHeight()-(h*0.3f));
			spriteBatch.draw(enemy, w-enemy.getWidth()-(w*0.1f), h-enemy.getHeight()-(h*0.3f));
			spriteBatch.end();
		}

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	/**
	 * Used to display the scene2d elements of the game part of the minigame, like the "Ready..." and "FIRE!" text.
	 */
	private void showGame(){
		stage.clear();
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		Skin skin = new Skin(Gdx.files.internal("default_skin/uiskin.json"));

		// used to handle which text is displayed when
		if(!gameStarted){
			// displayed after the game is started, but before the player is ready
			Label readyText = new Label("Press SPACE when you're ready! Press Z to shoot!", skin);
			readyText.setColor(0, 0, 0, 1);

			table.add(readyText);
		} else {
			if(!playerWon && !playerDisqualified && playerAlive){
				// displayed during the countdown before the minigame has ended
				if(countdown > 0){
					Label readyText = new Label("Ready...", skin);
					readyText.setColor(0, 0, 0, 1);

					table.add(readyText);
				} else {
					Texture fireTextTexture;
					fireTextTexture = new Texture(Gdx.files.internal("shootpirate/fire_text_white.png"));

					Image fireText;
					fireText = new Image(fireTextTexture);
					fireText.setColor(1, 0, 0, 1);


					table.add(fireText);
				}
			} else if (playerDisqualified){
				// displayed when the player fires prematurely
				Label dqText = new Label("You shot early! Disqualified!", skin);
				dqText.setColor(0, 0, 0, 1);

				Label menuText = new Label("Press SPACE to go back to the menu.", skin);
				menuText.setColor(0, 0, 0, 1);

				table.add(dqText);
				table.row();
				table.add(menuText);
			} else if (playerWon){
				// displayed when the player wins
				Label winText = new Label("You win! Press SPACE to go back to the menu.", skin);
				winText.setColor(0, 0, 0, 1);

				table.add(winText);
			} else if (!playerAlive){
				// displayed when the player loses
				Label loseText = new Label("You lose! Press SPACE to go back to the menu.", skin);
				loseText.setColor(0, 0, 0, 1);

				table.add(loseText);
			}
		}
	}

	/**
	 * Displays the minigame menu screen.
	 */
	public void setMinigameStateMenu(){
		this.state = "menu";
		stage.clear();
		gameStarted = false;
		showMenu();
	}

	/**
	 * Displays the minigame game screen and starts an instance of a game.
	 */
	public void setMinigameStateGame(){
		this.state = "game";
		this.playerAlive = true;
		this.enemyAlive = true;
		this.playerDisqualified = false;
		this.playerWon = false;
		stage.clear();
		showGame();
	}

	@Override
	public boolean keyDown(int keycode){
		if(!gameStarted){
			// if the game hasn't been started, space starts the game
			if(keycode == Input.Keys.SPACE){
				startGame();
			}
		}
		else
		{
			if(keycode == Input.Keys.Z){
				if(!playerWon && !playerDisqualified && playerAlive) {
					// allow the player to shoot if they have not yet lost or already won
					handleShot("player");
				}
			}
			// if the game has been started, space takes the player back to the minigame menu
			if(keycode == Input.Keys.SPACE){
				if(!playerAlive || !enemyAlive || playerDisqualified){
					setMinigameStateMenu();
				}
			}
		}
		return false;
	}

	/**
	 * Method used to resolve shooting.
	 * @param shooter Has to be a string, "player" or "enemy". Other arguments do nothing.
	 */
	private void handleShot(String shooter){
		if(shooter.equals("player")){
			// Disqualify the player if they fire before the signal, have them win otherwise.
			if(countdown > 0) {
				playerDisqualified = true;
			} else {
				playerWon = true;
				enemyAlive = false;
				giveReward();
			}
		} else if (shooter.equals("enemy")){
			if(enemyAlive){
				playerAlive = false;
			}
		}

	}

	/**
	 * Gives the player a reward based on the difficulty of the minigame they selected.
	 * Has to be called AFTER the difficulty variable has been set.
	 */
	private void giveReward(){
		Player player = gameScreen.getEntityManager().getOrCreatePlayer();

		switch(difficulty){
			case "easy":
				player.addBalance(2);
				break;
			case "medium":
				player.addBalance(50);
				break;
			case "hard":
				player.addBalance(200);
				break;
			case "very hard":
				player.addBalance(500);
				break;
		}
	}

	/**
	 * Prepares a minigame by generating a countdown timer and the timer for the enemy's reaction.
	 */
	private void startGame(){
		gameStarted = true;
		Random randomiser = new Random();
		countdown = randomiser.nextInt(4)+1;

		switch(difficulty){
			case "easy":
				enemyShotCountdown = countdown+20;
				break;
			case "medium":
				enemyShotCountdown = countdown+0.3f;
				break;
			case "hard":
				enemyShotCountdown = countdown+0.26f;
				break;
			case "very hard":
				enemyShotCountdown = countdown+0.23f;
				break;
		}
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button){return false;}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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

	@Override
	public void resize(int width, int height) {
		// change the stage's viewport when teh screen size is changed
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(this);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
