package uk.ac.york.sepr4.ahod2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import lombok.Data;
import uk.ac.york.sepr4.ahod2.node.Node;
import uk.ac.york.sepr4.ahod2.object.GameLevel;
import uk.ac.york.sepr4.ahod2.object.building.BuildingManager;
import uk.ac.york.sepr4.ahod2.object.card.CardManager;
import uk.ac.york.sepr4.ahod2.object.encounter.EncounterManager;
import uk.ac.york.sepr4.ahod2.object.entity.Player;
import uk.ac.york.sepr4.ahod2.screen.AHODScreen;
import uk.ac.york.sepr4.ahod2.screen.EndScreen;
import uk.ac.york.sepr4.ahod2.screen.ShipViewScreen;
import uk.ac.york.sepr4.ahod2.screen.TransitionScreen;
import uk.ac.york.sepr4.ahod2.screen.hud.AnimationHUD;
import uk.ac.york.sepr4.ahod2.screen.hud.MessageHUD;
import uk.ac.york.sepr4.ahod2.screen.hud.StatsHUD;
import uk.ac.york.sepr4.ahod2.screen.sail.SailScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class GameInstance {

    public static GameInstance INSTANCE;
    private AHOD2 game;
    //asset managers
    private CardManager cardManager;
    private BuildingManager buildingManager;
    private EncounterManager encounterManager;
    //persistent screens
    private SailScreen sailScreen;
    private ShipViewScreen shipViewScreen;
    //HUDs
    private StatsHUD statsHud;
    private MessageHUD messageHUD;
    private AnimationHUD AnimationHUD;

    private List<GameLevel> levels = new ArrayList<>();
    private Player player;

    public GameInstance(AHOD2 ahod2) {
        game = ahod2;
        INSTANCE = this;

        //Initialize Managers
        cardManager = new CardManager();
        buildingManager = new BuildingManager(this);
        encounterManager = new EncounterManager();

        //Initialize Screens and views
        statsHud = new StatsHUD(this);
        messageHUD = new MessageHUD(this);
        AnimationHUD = new AnimationHUD();
        sailScreen = new SailScreen(this);
        shipViewScreen = new ShipViewScreen(this);

        //load levels
        loadLevels();

        //get and set first level or abort
        Optional<GameLevel> firstLevel = getLevelByID(1);
        if (firstLevel.isPresent()) {
            player = new Player(firstLevel.get());
        } else {
            Gdx.app.error("GameInstance", "First Level could not be found! Exiting!");
            Gdx.app.exit();
        }
    }

    public void start() {
        Gdx.app.debug("GameInstance", "Starting Instance");
        switchScreen(sailScreen);
    }

    public GameLevel getCurrentLevel() {
        return getPlayer().getLevel();
    }

    public Optional<GameLevel> getLevelByID(Integer id) {
        Optional<GameLevel> gameLevel = Optional.empty();
        for (GameLevel gL : levels) {
            if (gL.getId().equals(id)) {
                gameLevel = Optional.of(gL);
            }
        }
        return gameLevel;
    }

    /***
     * Advance level.
     * If levels remain, move to next level and unset plakyer location.
     * If no levels left, player wins - move to EndScreen!
     */
    public void advanceLevel() {
        Integer currentLevelID = getCurrentLevel().getId();
        Optional<GameLevel> gameLevel = getLevelByID(currentLevelID + 1);
        if (gameLevel.isPresent()) {
            player.setLevel(gameLevel.get());
            player.setLocation(Optional.empty());
            getMessageHUD().addStatusMessage("Select Starting Node!", 3f);
            sailScreen.getOrthographicCamera().position.y = (Gdx.graphics.getHeight() / 2);
            //clear last levels nodes
            sailScreen.getStage().clear();
            fadeSwitchScreen(sailScreen);
        } else {
            //game won!
            fadeSwitchScreen(new EndScreen(this, true));
        }

    }

    /***
     * Util method - for quick access.
     * @param screen
     */
    public void switchScreen(Screen screen) {
        game.setScreen(screen);
    }

    /***
     * Fade switch screen. Fade from current screen to target screen.
     * @param fadeIn target screen
     * @param dispose whether to dispose current screen
     */
    public void fadeSwitchScreen(AHODScreen fadeIn, boolean dispose) {
        switchScreen(new TransitionScreen(this, (AHODScreen) game.getScreen(), fadeIn, dispose));
    }

    /***
     * Default fade switch screen - do not dispose.
     * @param fadeIn target screen
     */
    public void fadeSwitchScreen(AHODScreen fadeIn) {
        fadeSwitchScreen(fadeIn, false);
    }

    /***
     * Load GameLevels from JSON.
     */
    private void loadLevels() {
        Json json = new Json();
        Array<GameLevel> tempLevels = json.fromJson(Array.class, GameLevel.class, Gdx.files.internal("data/levels.json"));
        for (GameLevel level : tempLevels) {
            if (level.load(this)) {
                levels.add(level);
            }
        }
    }

    /***
     * Specified node clicked, perform node action.
     * @param node specified node
     */
    public void nodeAction(Node node) {
        //enter college/dept or get/enter encounter
        node.action(this);
        player.setLocation(Optional.of(node));
    }

}
