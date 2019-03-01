package uk.ac.york.sepr4;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import lombok.Getter;
import lombok.Setter;
import uk.ac.york.sepr4.object.building.College;
import uk.ac.york.sepr4.object.building.ShopUI;
import uk.ac.york.sepr4.object.entity.*;
import uk.ac.york.sepr4.object.PirateMap;
import uk.ac.york.sepr4.object.building.BuildingManager;
import uk.ac.york.sepr4.object.item.Reward;
import uk.ac.york.sepr4.object.quest.QuestManager;
import uk.ac.york.sepr4.hud.HUD;
import uk.ac.york.sepr4.hud.HealthBar;
import uk.ac.york.sepr4.object.item.ItemManager;
import uk.ac.york.sepr4.object.projectile.Projectile;
import uk.ac.york.sepr4.utils.AIUtil;
import javax.naming.NameNotFoundException;

/**
 * GameScreen is main game class. Holds data related to current player including the
 * {@link BuildingManager}, {@link ItemManager},
 * {@link QuestManager} and {@link EntityManager}
 * <p>
 * Responds to keyboard and mouse input by the player. InputMultiplexer used to combine input processing in both
 * this class (mouse clicks) and {@link Player} class (key press).
 */
public class GameScreen implements Screen, InputProcessor {

    private PirateGame pirateGame;
    private Stage stage;
    private Stage hudStage;
    private SpriteBatch batch;
    public boolean paused;
    private boolean gameOver = false;
    @Getter @Setter
    private boolean inDerwentBeforeEnd;

    @Getter
    private OrthographicCamera orthographicCamera;

    @Getter
    PirateMap pirateMap;
    private TiledMapRenderer tiledMapRenderer;

    private ItemManager itemManager;
    @Getter
    private EntityManager entityManager;
    @Getter
    private QuestManager questManager;
    @Getter
    private BuildingManager buildingManager;

    private InputMultiplexer inputMultiplexer;

    private HUD hud;
    private ShopUI shopUI;
    private boolean inDepartment;
    @Getter @Setter

    private boolean nearDepartment, nearMinigame;
    private float timer = 0;

    private static GameScreen gameScreen;

    private ShapeRenderer shapeRenderer;

    public static boolean DEBUG = false;


    public static GameScreen getInstance() {
        return gameScreen;
    }

    /**
     * GameScreen Constructor
     * <p>
     * Sets base game parameters, sets up camera, map, view port and stage(s).
     * Initializes Item, Entity, Building and Quest managers and InputMultiplexer.
     * <p>
     * Adds the player as an actor to the stage.
     *
     * @param pirateGame
     */
    public GameScreen(PirateGame pirateGame) {
        this.pirateGame = pirateGame;
        gameScreen = this;

        // Debug options (extra logging, collision shape renderer (viewing tile object map))
        if(DEBUG) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            shapeRenderer = new ShapeRenderer();
        }

        // Local widths and heights.
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Set up camera.
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, w, h);
        orthographicCamera.update();

        // Set up stages (for entities and HUD).
        StretchViewport stretchViewport = new StretchViewport(w, h, orthographicCamera);
        batch = new SpriteBatch();
        stage = new Stage(stretchViewport, batch);
        hudStage = new Stage(new FitViewport(w, h, new OrthographicCamera()));

        // Locate and set up tile map.
        pirateMap = new PirateMap(new TmxMapLoader().load("PirateMap/PirateMap.tmx"));
        tiledMapRenderer = new OrthogonalTiledMapRenderer(pirateMap.getTiledMap(), 1 / 2f);

        // Initialize game managers
        this.itemManager = new ItemManager();
        this.entityManager = new EntityManager(this);
        this.questManager = new QuestManager(entityManager);
        this.buildingManager = new BuildingManager(this);

        // Create HUD (display for xp, gold, etc..)
        this.hud = new HUD(this);
        inDepartment = false;

        hudStage.addActor(this.hud.getTable());
	// Added for assessment 3: HUD improvements
        hudStage.addActor(this.hud.getDepartmentPromptTable());
        hudStage.addActor(this.hud.getMinigamePromptTable());
        hudStage.addActor(this.hud.getPausedTable());
        hudStage.addActor(this.hud.getGameoverTable());
        hudStage.addActor(this.hud.getInDerwentBeforeEndTable());
        hudStage.addActor(this.hud.getCollegeTable());

        // Set input processor and focus
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hudStage);
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(entityManager.getOrCreatePlayer());
        Gdx.input.setInputProcessor(inputMultiplexer);

        //create and spawn player
        startGame();
    }

    //Added for assessment 3: pause function
    public static boolean isPaused(){
        if(getInstance() != null) {
            return getInstance().paused;
        }
        return false;
    }

    private void startGame() {
        stage.addActor(entityManager.getOrCreatePlayer());

    }

    /**
     * Called when screen becomes active (is switched to).
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Method responsible for rendering the GameScreen on each frame. This clears the screen, updates the map and
     * visible entities, then calls the stage act. This causes actors (entities) on the stage to move (act).
     *
     * @param delta Time between last and current frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //if player dead, go to main menu
        Player player = entityManager.getOrCreatePlayer();
        if (player.isDead()) {
            Gdx.app.debug("GameScreen", "Player Died!");
            pirateGame.switchScreen(ScreenType.MENU);
            pirateGame.gameOver();
            return;
        }

        if(!player.isDying()) {

            //spawns/despawns entities, handles animations and projectiles
            entityManager.handleStageEntities(stage, delta);
        } else {
            //when the player is dying - only process animations
            getEntityManager().getAnimationManager().handleEffects(stage,delta);
        }
            if (pirateMap.isObjectsEnabled()) {
                buildingManager.spawnCollegeEnemies(delta);
                buildingManager.checkBossSpawn();
                buildingManager.departmentPrompt();
                buildingManager.minigamePrompt();
            }

            handleHealthBars();

            this.hud.update();

            checkCollisions();

            // Update camera and focus on player.
            orthographicCamera.position.set(player.getX() + player.getWidth() / 2f, player.getY() + player.getHeight() / 2f, 0);
            orthographicCamera.update();
            batch.setProjectionMatrix(orthographicCamera.combined);
            tiledMapRenderer.setView(orthographicCamera);
            tiledMapRenderer.render();

            //debug
            if (DEBUG) {
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);

                for (Polygon polygonMapObject : getPirateMap().getCollisionObjects()) {
                    shapeRenderer.polygon(polygonMapObject.getTransformedVertices());
                }
                shapeRenderer.end();
            }


        stage.act(delta);
        stage.draw();
	//Added for assessment 3: enabled changes to HUD for entering shops
        if(inDepartment) {
            shopUI.getStage().act();
            shopUI.getStage().draw();
        } else {
            hudStage.act();
            hudStage.draw();
        }
	//Added for assessment 3: added message if player tries to fight Derwent without defeating the other colleges
        if (inDerwentBeforeEnd) {
            timer += delta;
            if (timer > 5f) {

                inDerwentBeforeEnd = false;
                timer = 0f;
            }
        }
    }

    /**
     * Handles HealthBar elements for damaged actors.
     */
    private void handleHealthBars() {
        Player player = entityManager.getOrCreatePlayer();
        if (player.getHealth() < player.getMaxHealth()) {
            if (!stage.getActors().contains(player.getHealthBar(), true)) {
                stage.addActor(player.getHealthBar());
            }
        }

        for (uk.ac.york.sepr4.object.entity.NPCBoat NPCBoat : entityManager.getNpcList()) {
            if (NPCBoat.getHealth() < NPCBoat.getMaxHealth()) {
                if (!stage.getActors().contains(NPCBoat.getHealthBar(), true)) {
                    stage.addActor(NPCBoat.getHealthBar());
                }
            }
        }
        Array<Actor> toRemove = new Array<>();
        for (Actor actors : stage.getActors()) {
            if (actors instanceof HealthBar) {
                HealthBar healthBar = (HealthBar) actors;
                LivingEntity livingEntity = healthBar.getLivingEntity();
                if (livingEntity.getHealth() == livingEntity.getMaxHealth() || livingEntity.isDead() || livingEntity.isDying()) {
                    toRemove.add(actors);
                }
            }
        }
        stage.getActors().removeAll(toRemove, true);

    }

    /**
     * Checks whether actors have overlapped. In the instance where projectile and entity overlap, deal damage.
     */
    private void checkCollisions() {
        checkProjectileCollisions();
        checkLivingEntityCollisions();
    }

    public void checkLivingEntityCollisions() {
        //player/map collision check
        //TODO: Improve to make player a polygon
        for(LivingEntity lE : getEntityManager().getLivingEntities()) {
            //Between entity and map
            if(getPirateMap().isColliding(lE.getRectBounds())) {
                if (lE.getCollidedWithIsland() == 0) {
                    lE.collide(false, 0f);
                }
            }
            if(lE.getCollidedWithIsland() >= 1) {
                lE.setCollidedWithIsland(lE.getCollidedWithIsland() - 1);
            }

            //between living entities themselves
            //TODO: still a bit buggy
            for(LivingEntity lE2 : getEntityManager().getLivingEntities()) {
                if(!lE.equals(lE2)) {
                    if(lE.getRectBounds().overlaps(lE2.getRectBounds())) {
                        if(lE.getColliedWithBoat() == 0) {
                            lE.collide(true, AIUtil.normalizeAngle((float)(lE.getAngleTowardsEntity(lE2) - Math.PI)));
                        }
                        //Gdx.app.log("gs", ""+lE.getColliedWithBoat());
                    }
                }
                if(lE.getColliedWithBoat() >= 1) {
                    lE.setColliedWithBoat(lE.getColliedWithBoat() - 1);
                }
            }
        }
    }

    private void checkProjectileCollisions() {
        Player player = entityManager.getOrCreatePlayer();

        for (LivingEntity livingEntity : entityManager.getLivingEntities()) {
            for (Projectile projectile : entityManager.getProjectileManager().getProjectileList()) {
                if (projectile.getShooter() != livingEntity && projectile.getRectBounds().overlaps(livingEntity.getRectBounds())) {
                    //if bullet overlaps player and shooter not player
                    if (!(livingEntity.isDying() || livingEntity.isDead())) {
                        if (!livingEntity.damage(projectile.getDamage())) {
                            //is dead
                            if(livingEntity instanceof NPCBoat) {
                                Gdx.app.debug("GameScreen", "NPCBoat died.");
                                NPCBoat npcBoat = (NPCBoat) livingEntity;
                                Reward reward = itemManager.generateReward();
                                reward.setGold(reward.getGold() + (int)npcBoat.getDifficulty());
                                reward.setXp(reward.getXp() + (int)npcBoat.getDifficulty());
                                player.issueReward(reward);
                                //if dead NPC is a boss then player can capture its respective college
                                if(npcBoat.isBoss() && npcBoat.getAllied().isPresent()) {
                                    player.capture(npcBoat.getAllied().get());
                                }
                            } else {
                                Gdx.app.debug("GameScreen", "Player died.");
                            }
                        }
                        Gdx.app.debug("GameScreen", "LivingEntity damaged by projectile.");
                        //kill projectile
                        projectile.setActive(false);
                    }
                }
            }
        }
    }

    public PirateGame getGame() {
        return pirateGame;
    }


    //Added for assessment 3: Methods to enter and exit departments
    /**
     * Switch the interface to interact with a department
     * @param name the name of the department
     */
    public void enterDepartment(String name) {
        try {
            this.shopUI = new ShopUI(this, name);
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gdx.input.setInputProcessor(shopUI.getStage());
        inDepartment = true;
        paused = true;
    }

    /**
     * Exit department
     * Should only be called when in a department
     */
    public void exitDepartment() {
        shopUI.dispose();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inDepartment = false;
        paused = false;
    }





    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false, (float) width, (float) height);
        orthographicCamera.update();
    }

    @Override
    public void pause() {}

    //Added for Assessment 3: resume method
    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
   //Added for Assessment 3: code for pausing game
        if (isPaused()) {
            return false;
        }
        if (button == Input.Buttons.LEFT) {
            Player player = entityManager.getOrCreatePlayer();
            Vector3 clickLoc = orthographicCamera.unproject(new Vector3(screenX, screenY, 0));
            float fireAngle = (float) (-Math.atan2(player.getCentre().x - clickLoc.x, player.getCentre().y - clickLoc.y));
            Gdx.app.debug("GameScreen", "Firing: Click at (rad) " + fireAngle);
	    //Added for Assessment 3: Allow player to use triple shot
            if (player.isTripleShot()) {
                if (player.tripleFire(fireAngle, player.getBulletDamage())) {
                    Gdx.app.debug("GameScreen", "Firing: Error! (cooldown?)");
                }
            }
            else if (player.fire(fireAngle, player.getBulletDamage())) {
                Gdx.app.debug("GameScreen", "Firing: Error! (cooldown?)");
            }
            return true;
        }
        return false;
    }
    //Added for Assessment 3: key listener for game events
    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.SPACE) {
            if (gameScreen.getGameOver() != true) {
                paused = !paused;
                return true;
            }
        }

        if (keycode == Input.Keys.E) {
            if (nearDepartment) {
                nearDepartment = false;
                inDepartment = true;
                enterDepartment(gameScreen.getEntityManager().getPlayerLocation().get().getName());
                return true;
            }
            else if (nearMinigame) {
                nearMinigame = false;
                PirateGame pirateGame = GameScreen.getInstance().getGame();
                pirateGame.switchScreen(ScreenType.MINIGAME);
            }
        }

        if(keycode == Input.Keys.L){
            // DEBUG code used to test minigame easily!
            //pirateGame.switchScreen(ScreenType.MINIGAME);
        }

        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }

        return false;
    }

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

    //Added for Assessment 3: miscellaneous getters and setters
    public boolean getNearDepartment() {
        return nearDepartment;
    }

    public boolean getGameOver()    {
        return gameOver;
    }

    public void setGameOver(boolean newBool) {
        gameOver = newBool;

    }
}
