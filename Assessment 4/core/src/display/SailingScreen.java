package display;

import base.BaseActor;
import base.BaseScreen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import game_manager.GameManager;
import location.College;
import sailing.SailingShip;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static game_manager.GameManager.*;
import static location.College.*;

/**
 * SailingScreen is the main class that deals with sailing
 *
 */
public class SailingScreen extends BaseScreen {

    /**
     * An instance of SailingShip, Derwent is used as the home college by default
     */
    private SailingShip playerShip = new SailingShip(Derwent);

    /**
     * Lists to hold all the objects retrieved from the tiled map
     */
    private ArrayList<BaseActor> obstacleList;
    private ArrayList<BaseActor> regionList;

    /**
     * Lists to hold all the objects to be removed from the game
     */
    private ArrayList<BaseActor> removeList;

    /**
     * Variables to keep track of the size and dimension of the tile map
     */
    private int tileSize = 64;
    private int tileCountWidth = 120;
    private int tileCountHeight = 120;

    /**
     * Game world dimensions
     */
    private final int mapWidth = tileSize * tileCountWidth;
    private final int mapHeight = tileSize * tileCountHeight;

    /**
     * An object used to reference to the tile map
     */
    private TiledMap tiledMap;

    /**
     * The renderer and Camera for the tile map
     */
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera tiledCamera;

    /**
     * Specifying layers in order of rendering, background layers are rendered first in ascending order
     */
    private int[] backgroundLayers = {0,1,2};
    private int[] foregroundLayers = {3};

    /**
     * UI elements on the screen
     */
    private Label pointsLabel;
    private Label goldLabel;
    private Label mapMessage;
    private Label hintMessage;

    /**
     * Timer used for adding point through passage of time
     */
    private Float timer;

    /**
     * @param isFirstSailingInstance check if this SailingScreen is generated for the first time in a new game from MainMenu
     *                               or if it is generated from save game or from another screen outside of MainMenu
     */
    public SailingScreen(GameManager game, boolean isFirstSailingInstance) {
        super(game);

        Gdx.app.debug("Sailing DEBUG", playerShip.getName());

        mainStage.addActor(playerShip);
        Gdx.app.debug("Sailing DEBUG", "playerShip added");

        Table uiTable = new Table();

        Label pointsTextLabel = new Label("Points: ", skin, "default_black");
        pointsLabel = new Label(Integer.toString(game.getPoints()), skin, "default_black");
        pointsLabel.setAlignment(Align.left);

        Label goldTextLabel = new Label("Gold:", skin, "default_black");
        goldLabel = new Label(Integer.toString(game.getGold()), skin, "default_black");
        goldLabel.setAlignment(Align.left);

        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldLabel).fill();

        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        mapMessage = new Label("", skin, "default_black");
        hintMessage = new Label("", skin,"default_black");

        Table messageTable = new Table();
        messageTable.add(mapMessage);
        messageTable.row();
        messageTable.add(hintMessage);

        messageTable.setFillParent(true);
        messageTable.top();

        uiStage.addActor(messageTable);

        obstacleList = new ArrayList<BaseActor>();
        removeList = new ArrayList<BaseActor>();
        regionList = new ArrayList<BaseActor>();

        // set up tile map, renderer and camera
        tiledMap = new TmxMapLoader().load("game_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewwidth, viewheight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get("ObjectData").getObjects();
        for (MapObject object : objects) {
            String name = object.getName();

            // all object data assumed to be stored as rectangles
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle r = rectangleObject.getRectangle();

            if (name.equals("player") && isFirstSailingInstance){
                playerShip.setPosition(r.x, r.y);
            } else if (name.equals("player") && !isFirstSailingInstance) {
                playerShip.setPosition(game.getSailingShipX(), game.getSailingShipY());
                playerShip.setRotation(game.getSailingShipRotation());
            } else{
                System.err.println("Unknown tilemap object: " + name);
            }
        }

        objects = tiledMap.getLayers().get("PhysicsData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor solid = new BaseActor();
                solid.setPosition(r.x, r.y);
                solid.setSize(r.width, r.height);
                solid.setName(object.getName());
                solid.setRectangleBoundary();
                String objectName = object.getName();

                if (objectName.equals("derwent")) solid.setCollege(Derwent);
                else if (objectName.equals("james")) solid.setCollege(James);
                else if (objectName.equals("vanbrugh")) solid.setCollege(Vanbrugh);
                else if (objectName.equals("alcuin")) solid.setCollege(Alcuin);
                else if (objectName.equals("langwith")) solid.setCollege(Langwith);
                else if (objectName.equals("goodricke")) solid.setCollege(Goodricke);
                else if (objectName.equals("computerscience"))solid.setDepartment(ComputerScience);
                else if (objectName.equals("physics")) solid.setDepartment(Physics);
                else if (objectName.equals("lawandmanagement")) solid.setDepartment(LawAndManagement);
                else{
                    Gdx.app.debug("Sailing DEBUG", "Not college/department: " + solid.getName());
                }
                obstacleList.add(solid);
            } else {
                System.err.println("Unknown PhysicsData object.");
            }
        }

        objects = tiledMap.getLayers().get("RegionData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor region = new BaseActor();
                region.setPosition(r.x, r.y);
                region.setSize(r.width, r.height);
                region.setRectangleBoundary();
                region.setName(object.getName());

                if (object.getName().equals("derwentregion")) region.setCollege(Derwent);
                else if (object.getName().equals("jamesregion")) region.setCollege(James);
                else if (object.getName().equals("vanbrughregion")) region.setCollege(Vanbrugh);
                else if (object.getName().equals("alcuinregion")) region.setCollege(Alcuin);
                else if (object.getName().equals("langwithregion")) region.setCollege(Langwith);
                else if (object.getName().equals("goodrickeregion")) region.setCollege(Goodricke);
                regionList.add(region);
            } else {
                System.err.println("Unknown RegionData object.");
            }
        }

        timer = 0f;

        InputMultiplexer im = new InputMultiplexer(uiStage, mainStage);
        Gdx.input.setInputProcessor(im);

        musicSetup("glorious-morning.mp3");
    }
    
    @Override
    public void update(float delta) {
        removeList.clear();
        goldLabel.setText(Integer.toString(game.getGold()));
        this.playerShip.playerMove(delta);

        Boolean x = false;
        for (BaseActor region : regionList) {
            String name = region.getName();
            if (playerShip.overlaps(region, false)) {
                x = true;

                mapMessage.setText(capitalizeFirstLetter(name.substring(0, name.length() - 6)) + " Territory");

                int enemyChance = ThreadLocalRandom.current().nextInt(0, 10001);
                if (enemyChance <= 10) {
                    Gdx.app.log("Sailing", "Enemy Found in " + name);
                    College college = region.getCollege();
                    if (college.isBossAlive() && !playerShip.getCollege().equals(college)) {
                        game.setSailingShipX(this.playerShip.getX());
                        game.setSailingShipY(this.playerShip.getY());
                        game.setSailingShipRotation(this.playerShip.getRotation());
                        game.setScreen(new CombatScreen(game, false, college));
                    }
                }
            }
        }

        if (!x) {
            mapMessage.setText("Neutral Territory");
//            Gdx.app.debug("Sailing Location","Neutral Territory");
        }


        Boolean y = false;
        for (BaseActor obstacle : obstacleList) {
            String name = obstacle.getName();
            if (playerShip.overlaps(obstacle, true)) {
                y = true;
                if (!(obstacle.getDepartment() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press Enter to interact");

                    if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                        game.setSailingShipX(this.playerShip.getX());
                        game.setSailingShipY(this.playerShip.getY());
                        game.setSailingShipRotation(this.playerShip.getRotation());
                        game.setScreen(new DepartmentScreen(game, obstacle.getDepartment()));
                    }
                }
                // Obstacle must be a college if college not null
                else if (!(obstacle.getCollege() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press Enter to interact");
                    Gdx.app.debug("Sailing DEBUG","Encountered a College");
                    College college = obstacle.getCollege();
                    if (playerShip.getCollege().equals(college)) {
                        mapMessage.setText(capitalizeFirstLetter(name) + " Island (Home)");
                        hintMessage.setText("Press Enter to play Minigame");
                        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                            Gdx.app.debug("Sailing DEBUG","Interacted with College");
                            game.setSailingShipX(this.playerShip.getX());
                            game.setSailingShipY(this.playerShip.getY());
                            game.setSailingShipRotation(this.playerShip.getRotation());
                            game.setScreen(new MinigameScreen(game));
                        }
                    } else if (college.isBossAlive()) {
                        mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                        hintMessage.setText("Press Enter to interact");
                        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                            Gdx.app.debug("Sailing DEBUG","Interacted with College");
                            game.setSailingShipX(this.playerShip.getX());
                            game.setSailingShipY(this.playerShip.getY());
                            game.setSailingShipRotation(this.playerShip.getRotation());
                            game.setScreen(new CombatScreen(game, true, college));
                        }
                    } else {
                        mapMessage.setText(capitalizeFirstLetter(name) + " Island (Sacked)");
                        hintMessage.setText("");
                    }

                    Gdx.app.debug("Sailing DEBUG","Encountered a College");

                } else {
//                    Gdx.app.debug("Sailing DEBUG", "Pure obstacle");
                }
            }
            game.setSailingShipX(playerShip.getX());
            game.setSailingShipY(playerShip.getY());
            game.setSailingShipRotation(playerShip.getRotation());
        }

        if (!y) hintMessage.setText("");

        for (BaseActor object : removeList) {
            object.remove();
        }

        // camera adjustment
        Camera mainCamera = mainStage.getCamera();

        // center camera on player
        mainCamera.position.x = playerShip.getX() + playerShip.getOriginX();
        mainCamera.position.y = playerShip.getY() + playerShip.getOriginY();

        // bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewwidth / 2, mapWidth - viewwidth / 2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewheight / 2, mapHeight - viewheight / 2);
        mainCamera.update();

        // adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        timer += delta;
        if (timer > 1) {
	        game.addPoints(1);
            timer -= 1;
        }

        pointsLabel.setText(Integer.toString(game.getPoints()));
    }

    @Override
    public void render(float delta) {
        // render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();
        tiledMapRenderer.render(foregroundLayers);
        uiStage.draw();

        if (!gamePaused){
            uiStage.act(delta);
            mainStage.act(delta);
            update(delta);

            if (!playerShip.isAnchor()){
                playerShip.addAccelerationAS(playerShip.getRotation(), 10000);
            } else{
                playerShip.setAccelerationXY(0,0);
                playerShip.setDeceleration(100);
            }
            Gdx.input.setInputProcessor(mainStage);
        }
        else{
            pauseProcess();
        }
        super.inputForScreen();
    }

    @Override
    public void dispose () {
       super.dispose();
       playerShip.getSailingTexture().dispose();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
