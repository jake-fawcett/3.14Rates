package display;

import base.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.items.Weapon;
import combat.manager.CombatManager;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import game_manager.GameManager;
import location.College;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static combat.ship.RoomFunction.*;
import static location.College.colleges;
import static other.Constants.COOLDOWN_TICKS_PER_TURN;
import static other.Constants.EASY_SCORE_MULTIPLIER;

/**
 * CombatScreen is the class that mostly handles the visual elements of combat.
 * A big change to this class was the move from absolute positioning to Scene2D's table structure, this was a big change
 * as the layout had to be reworked, however the combat framework behind the scenes was retained.
 * Other changes we made to this class were mainly inside of the render loop in regards to how textures are rendered.
 * These changes were to improve performance.
 */
public class CombatScreen extends BaseScreen {

    /**
     * Sets up all required managers to access Methods and Cause Combat
     */
    private Ship playerShip;
    private CombatPlayer combatPlayer = game.getCombatPlayer();
    private Ship enemyShip;
    private CombatEnemy combatEnemy;
    private CombatManager combatManager = game.getCombatManager();

    private College college;

    /**
     * Used to Draw Assets on the Screen
     */
    private SpriteBatch batch = new SpriteBatch();

    /**
     * Main style used for buttons
     */
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle;

    /**
     * Tracks the Room and Weapon Selected
     */
    private Room roomSelected;
    private Weapon weaponSelected;

    /**
     * Groups used to control sets of Buttons and Track number Checked
     */
    private ButtonGroup weaponButtonGroup = new ButtonGroup();
    private ButtonGroup roomButtonGroup = new ButtonGroup();

    private List<TextButton> weaponButtons = new ArrayList<TextButton>();

    /**
     * Booleans used to track if the Game is over and won/lost, plus buttons to display the info
     */
    private Boolean gameOver = false;
    private Boolean gameWon;
    private TextButton youWin;
    private TextButton youLose;
    private int a = 0;

    /**
     * Buttons to Display if Hit or Missed, FeedbackTime measures how long this is displayed
     */
    private float hitFeedbackTime;
    private TextButton youHit;
    private TextButton youMissed;
    private TextButton enemyHit;
    private TextButton enemyMissed;

    /**
     * Used to set values to the same no. decimal places
     */
    private DecimalFormat df;

    /**
     * Initialising sounds used in combat
     */
    private Sound cannon_1;
    private Sound cannon_2;
    private Sound cannon_3;

    /**
     * Initialising tables
     */
    private Table fullTable;
    private Table playerHealthTable;
    private Table playerShipTable;
    private Table enemyShipTable;
    private Table attackTable;
    private Label titleLabel;

    /**
     * Constructor requiring instance of GameManager (to switch screen) and is college battle, also takes a college parameter
     */

    private Boolean isCollegeBattle;
    public CombatScreen(GameManager game, Boolean isCollegeBattle, College college){
        super(game);
        this.college = college;
        playerShip = game.getPlayerShip();
        this.isCollegeBattle = isCollegeBattle;
        TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");
        Stack groupEnemyBar = new Stack();

        // Table Set up
        fullTable = new Table();
        fullTable.setFillParent(true);

        playerHealthTable = new Table();
        playerShipTable = new Table();
        enemyShipTable = new Table();
        attackTable = new Table();

        Texture backgroundTex = new Texture("battleBackground.png");
        Image backgroundImg = new Image(backgroundTex);
        backgroundImg.setSize(viewwidth, viewheight);
        mainStage.addActor(backgroundImg);

        titleLabel = new Label("", skin, "title");

        drawCollege(college.getName());

        setUpTextures();
        groupEnemyBar.addActor(hpEnemyImage);
        groupEnemyBar.addActor(enemyHpBar);

        fullTable.add(titleLabel).colspan(2).center();
        fullTable.row().uniformX().width(viewwidth/3);
        fullTable.add(playerHealthTable);
        fullTable.add(groupEnemyBar).align(Align.top).width(hpImage.getPrefWidth());
        fullTable.row().uniform();
        fullTable.add(playerShipTable);
        fullTable.add(enemyShipTable);
        fullTable.row();
        fullTable.add(attackTable).colspan(2);

        mainStage.addActor(fullTable);
        drawHitMissButtons();

        fullTable.center();
        // ALL TABLE DEBUGGING
//        fullTable.debugAll();
    }

    @Override
    public void show() {
        //Sets the Appropriate ship for if a College or Standard battle are happening
        if (isCollegeBattle) {
            enemyShip = game.getCollegeShip();
            combatEnemy = game.getCombatCollege();
        } else {
            enemyShip = game.getEnemyShip();
            combatEnemy = game.getCombatEnemy();
        }
	    musicSetup("the-buccaneers-haul.mp3");
        cannon_1 = makeSound("cannon_1.mp3");
        cannon_2 = makeSound("cannon_2.mp3");
        cannon_3 = makeSound("cannon_3.mp3");

        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

    }

    private TextureAtlas roomSpriteAtlas;
    private Texture hpBackground;
    private Texture hpBar;

    private Image hpImage;
    private Image hpEnemyImage;
    private Image playerHpBar;
    private Image enemyHpBar;

    private Label pointsLabel;
    private Label goldLabel;
    private Label foodLabel;
    private Label crewLabel;

    /**
     * This method sets up the textures correctly, it is a change from the previous implementation where textures were creating each render loop call.
     */
    public void setUpTextures(){
        roomSpriteAtlas = new TextureAtlas("roomSpriteSheet.txt");
        hpBar = new Texture("background.png");
        playerHpBar = new Image(hpBar);
        enemyHpBar = new Image(hpBar);

        hpBackground = new Texture("disabledBackground.png");
        hpImage = new Image(hpBackground);
        hpEnemyImage = new Image(hpBackground);

        drawFriendlyShip();
        setupHealthBar();
        drawEnemyShip();
        drawRoomHPEffectivness();
        drawWeaponButtons();
        drawEndButtons();
    }

    @Override
    public void update(float delta){
        Gdx.input.setInputProcessor(mainStage);
        batch.begin();
        updateInfo();

        batch.end();

        //Checks if the Game is Over and Displays Message if You Won/Lost
        if (gameOver) {
            if (gameWon) {
                youWin.setVisible(true);

                if (isCollegeBattle) {
                    game.addPoints((int) (1000 * EASY_SCORE_MULTIPLIER));
                    game.addGold((int) (1000 * EASY_SCORE_MULTIPLIER));
                    college.setBossAlive(false);
                    colleges.remove(college);
                } else {
                    game.addPoints((int) (100 * EASY_SCORE_MULTIPLIER));
                    game.addGold((int) (100 * EASY_SCORE_MULTIPLIER));
                }
            } else {
                youLose.setVisible(true);
            }

            if (colleges.isEmpty()) {
                changeScreen(new VictoryScreen(game));
            } else {
                //Waits 5 Loops to ensure Above messages render, Sleeps, then returns to menu
                if (a == 5) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) { }
                    changeScreen(new SailingScreen(game, false));
                }
                a++;
            }
        }

        //Used to control how long the Hit/Miss messages are displayed, hides them after the time
        if (hitFeedbackTime >= 2) {
            youHit.setVisible(false);
            youMissed.setVisible(false);
            enemyHit.setVisible(false);
            enemyMissed.setVisible(false);
        }
        hitFeedbackTime = hitFeedbackTime + delta;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
        super.dispose();
        cannon_1.dispose();
        cannon_2.dispose();
        cannon_3.dispose();
        batch.dispose();
    }

    /**
     * Picks a Random integer
     */
    private int pickRandom(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }

    /**
     * Checks which college was chosen and rewrites the title, depending on if the battle is a boss battle
     */
    private void drawCollege(String college){
        if (isCollegeBattle){
            titleLabel.setText(college.substring(0,1).toUpperCase() + college.substring(1) + " Boss");
        }
        else{
            titleLabel.setText(college.substring(0,1).toUpperCase() + college.substring(1) + " Defender");
        }
    }

    // These labels are used to display the HP of specific rooms, and must not be local as they must be updated throughout.
    private Label hpLabelCQ;
    private Label hpLabelCN;
    private Label hpLabelGD;
    private Label hpLabelH;
    private Label hpLabelE1;
    private Label hpLabelE2;
    private Label hpLabelE3;
    private Label hpLabelE4;

    /**
     * Draws the friendly ship from room textures and constant coordinates
     */
    private void drawFriendlyShip(){

        hpLabelCQ = labelMaker(CREW_QUARTERS);
        hpLabelCN =  labelMaker(CROWS_NEST);
        hpLabelGD = labelMaker(GUN_DECK);
        hpLabelH = labelMaker(HELM);
        hpLabelE1 = labelMaker(NON_FUNCTIONAL);
        hpLabelE2 = labelMaker(NON_FUNCTIONAL);
        hpLabelE3 = labelMaker(NON_FUNCTIONAL);
        hpLabelE4 = labelMaker(NON_FUNCTIONAL);


        Stack groupCQ = roomSetup("crewQuaters", hpLabelCQ);
        Stack groupE1 = roomSetup("EmptyRoom", hpLabelE1);
        Stack groupCN = roomSetup("crowsNest", hpLabelCN);
        Stack groupGD = roomSetup("gunDeck", hpLabelGD);
        Stack groupE2 = roomSetup("EmptyRoom", hpLabelE2);
        Stack groupH = roomSetup("helm", hpLabelH);
        Stack groupE3 = roomSetup("EmptyRoom", hpLabelE3);
        Stack groupE4 = roomSetup("EmptyRoom", hpLabelE4);

        shipTableSetup(playerShipTable, groupE1, groupE2, groupE3, groupH, groupCN, groupGD, groupCQ, groupE4);
    }

    private Label labelMaker(RoomFunction room){
        return new Label("HP:" + playerShip.getRoom(room).getHp(), skin);
    }

    private Stack roomSetup(String roomName, Label label){
        Stack group = new Stack();
        group.addActor(new Image(roomSpriteAtlas.findRegion(roomName)));
        label.setAlignment(Align.bottomLeft);
        group.addActor(label);
        return group;
    }

    /**
     * Method that sets up a ship (reduces repeated code)
     * @param table
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     * @param a5
     * @param a6
     * @param a7
     * @param a8
     */

    private void shipTableSetup(Table table, Actor a1, Actor a2, Actor a3, Actor a4,
                                Actor a5, Actor a6, Actor a7, Actor a8){
        addTwoActors(table, a1, a2);
        addTwoActors(table, a3, a4);
        addTwoActors(table, a5, a6);
        addTwoActors(table, a7, a8);

    }

    /**
     * Method reduces repeated code
     * @param table
     * @param a1
     * @param a2
     */
    private void addTwoActors(Table table, Actor a1, Actor a2) {
        table.add(a1);
        table.add(a2);
        table.row();
    }

    /**
     * Draws Hp bars for both ships
     */
    private void setupHealthBar() {
        Stack groupPlayerBar = new Stack();

        pointsLabel = new Label("Score: " + game.getPoints() ,skin);
        goldLabel = new Label("Gold: " + game.getGold() ,skin);
        foodLabel = new Label("Food: " + game.getFood() ,skin);
        crewLabel = new Label("Crew: " + playerShip.getCrew() ,skin);

        groupPlayerBar.addActor(hpImage);
        groupPlayerBar.addActor(playerHpBar);


        playerHealthTable.row().left();
        playerHealthTable.add(groupPlayerBar).colspan(4).center();
        playerHealthTable.row().fill();
        playerHealthTable.add(pointsLabel);
        playerHealthTable.add(goldLabel).pad(0, viewwidth/100f,0,viewwidth/100f);
        playerHealthTable.add(foodLabel).pad(0, 0,0,viewwidth/100f);
        playerHealthTable.add(crewLabel);
    }

    private void updateInfo(){
        pointsLabel.setText("Score: " + game.getPoints());
        goldLabel.setText("Gold: " + game.getGold());
        foodLabel.setText("Food: " + game.getFood());
        crewLabel.setText("Crew: " + playerShip.getCrew());

        hpLabelE1.setText("HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp());
        hpLabelE2.setText("HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp());
        hpLabelE3.setText("HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp());
        hpLabelE4.setText("HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp());
        hpLabelH.setText("HP:" + playerShip.getRoom(HELM).getHp());
        hpLabelCQ.setText("HP:" + playerShip.getRoom(CREW_QUARTERS).getHp());
        hpLabelCN.setText("HP:" + playerShip.getRoom(CROWS_NEST).getHp());
        hpLabelGD.setText("HP:" + playerShip.getRoom(GUN_DECK).getHp());

        hpImage.setWidth(hpImage.getPrefWidth());
        playerHpBar.setWidth(hpImage.getPrefWidth() * playerShip.getHullHP()/playerShip.getBaseHullHP());
        enemyHpBar.setWidth(hpImage.getPrefWidth() * enemyShip.getHullHP()/enemyShip.getBaseHullHP());

        int i = 0;
        for  (Weapon weapon : playerShip.getWeapons()) {
            if (weapon.getCurrentCooldown() <= 0){
                cooldownList.get(i).setText("Ready!");
                weaponButtons.get(i).setTouchable(Touchable.enabled);
            } else {
                cooldownList.get(i).setText("Cooldown: " + (weapon.getCurrentCooldown() / COOLDOWN_TICKS_PER_TURN) + "Turns");
                weaponButtons.get(i).setTouchable(Touchable.disabled);
            }
            i++;
        }
        updateRoomStats(playerRoomList, playerShip);
        updateRoomStats(enemyRoomList, enemyShip);

    }

    private void updateRoomStats(ArrayList<Label> list, Ship ship){
        list.get(0).setText("Ship Functionality");
        list.get(1).setText("Evade: " + df.format(ship.calculateShipEvade() * 100) + "%");
        list.get(2).setText("Accuracy:" + df.format(ship.calculateShipAccuracy() * 100) + "%");
        list.get(3).setText("Weapon Effectiveness: " + df.format(ship.getRoom(RoomFunction.GUN_DECK).getMultiplier() * 100) + "%");
        list.get(4).setText("Repair % Per Turn: " + df.format(ship.calculateRepair()) + "%");
    }

    /**
     * Draws the Enemy Ship of buttons from Textures using scene2d tables, adds listeners which track the selected room
     */
    private void drawEnemyShip(){
        TextureAtlas roomButtonAtlas = new TextureAtlas("roomSpriteSheet.txt");
        skin.addRegions(roomButtonAtlas);

        ImageButton.ImageButtonStyle crewQuatersStyle = new ImageButton.ImageButtonStyle(), crowsNestStyle = new ImageButton.ImageButtonStyle(), gunDeckStyle = new ImageButton.ImageButtonStyle(), helmStyle = new ImageButton.ImageButtonStyle(), emptyStyle = new ImageButton.ImageButtonStyle();
        crewQuatersStyle.up = skin.getDrawable("crewQuaters");
        crewQuatersStyle.checked = skin.getDrawable("crewQuatersTargetted");
        crowsNestStyle.up = skin.getDrawable("crowsNest");
        crowsNestStyle.checked = skin.getDrawable("crowsNestTargetted");
        gunDeckStyle.up = skin.getDrawable("gunDeck");
        gunDeckStyle.checked = skin.getDrawable("gunDeckTargetted");
        helmStyle.up = skin.getDrawable("helm");
        helmStyle.checked = skin.getDrawable("helmTargetted");
        emptyStyle.up = skin.getDrawable("EmptyRoom");
        emptyStyle.checked = skin.getDrawable("EmptyRoomTargetted");

        ImageButton enemyCrewQuarters = new ImageButton(crewQuatersStyle), enemyCrowsNest = new ImageButton(crowsNestStyle), enemyGunDeck = new ImageButton(gunDeckStyle), enemyHelm = new ImageButton(helmStyle),
                enemyEmpty1 = new ImageButton(emptyStyle), enemyEmpty2 = new ImageButton(emptyStyle), enemyEmpty3 = new ImageButton(emptyStyle), enemyEmpty4 = new ImageButton(emptyStyle);

        roomButtonGroup.add(enemyCrewQuarters, enemyCrowsNest, enemyGunDeck, enemyHelm, enemyEmpty1, enemyEmpty2, enemyEmpty3, enemyEmpty4);
        roomButtonGroup.setMaxCheckCount(1);
        roomButtonGroup.uncheckAll();

        addButtonListener(CREW_QUARTERS, enemyCrewQuarters);
        addButtonListener(CROWS_NEST, enemyCrowsNest);
        addButtonListener(GUN_DECK, enemyGunDeck);
        addButtonListener(HELM, enemyHelm);
        addButtonListener(NON_FUNCTIONAL, enemyEmpty1);
        addButtonListener(NON_FUNCTIONAL, enemyEmpty2);
        addButtonListener(NON_FUNCTIONAL, enemyEmpty3);
        addButtonListener(NON_FUNCTIONAL, enemyEmpty4);

        shipTableSetup(enemyShipTable, enemyEmpty1, enemyHelm, enemyGunDeck, enemyEmpty2, enemyCrowsNest ,enemyEmpty3, enemyCrewQuarters, enemyEmpty4);
    }

    private void addButtonListener(final RoomFunction room, ImageButton button){
        button.addListener(new InputListener() {
                               public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                   roomSelected = enemyShip.getRoom(room);
                                   return true;
                               }
                           });
    }

    /**
     * Draws buttons for Each weapon the user has which can be pressed to fire
     * Also adds listeners to these controlling the weaponSelected by the user
     * This also contains the Fire button, when pressed this runs the main combat loop
     */
    private void drawWeaponButtons() {
        TextureAtlas weaponButtonAtlas = new TextureAtlas("weaponButtonSpriteSheet.txt");
        Skin weaponButtonSkin = new Skin();
        weaponButtonSkin.addRegions(weaponButtonAtlas);

        final TextButton.TextButtonStyle weaponButtonStyle = new TextButton.TextButtonStyle();
        weaponButtonStyle.up = weaponButtonSkin.getDrawable("weaponButtonUp");
        weaponButtonStyle.down = weaponButtonSkin.getDrawable("weaponButtonDown");
        weaponButtonStyle.checked = weaponButtonSkin.getDrawable("weaponButtonChecked");
        weaponButtonStyle.font = new BitmapFont();

        final List<Weapon> playerWeapons = playerShip.getWeapons();

        weaponButtonGroup.setMaxCheckCount(1);
        drawWeaponCooldowns();

        for (int i = 0; i < 4; i++){
            attackTable.add(cooldownList.get(i));
        }
        attackTable.row();

        int i = 0;
        while (i < 4) {
            final int j = i;
            TextButton button;
            try {
                button = new TextButton(playerWeapons.get(j).getName(), weaponButtonStyle);
            } catch (IndexOutOfBoundsException e) {
                button = new TextButton("Empty Slot", weaponButtonStyle);
                button.setDisabled(true);
            }

            button.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        weaponSelected = playerWeapons.get(j);
                    } catch (IndexOutOfBoundsException e) {
                    }
                    return true;
                }
            });
            weaponButtons.add(button);
            weaponButtonGroup.add(button);
            attackTable.add(button);
            i++;
        }



        final TextButton fire = new TextButton("Fire", weaponButtonStyle);
        attackTable.add(fire).padLeft(fire.getPrefWidth());


        fire.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fire.setChecked(true);
                if (weaponSelected instanceof Weapon && roomSelected instanceof  Room) {
                    if (weaponSelected.getCurrentCooldown() > 0) {
                        for (Weapon weapon : playerShip.getWeapons()) {
                            Gdx.app.log("Combat", "Cooldown decremented");
                            Gdx.app.log("Combat", weapon.getName());
                            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
                        }
                        playerShip.combatRepair();
                    } else {
                        //Runs the players Combat Loop
                        combatManager.combatLoop(combatPlayer, combatEnemy, roomSelected, weaponSelected);
                        playSound(cannon_1);
                        //Displays if the Player Hit or Missed
                        if (combatManager.getShotHit()){
                            Gdx.app.log("Combat", "Attack Hit");
                            hitFeedbackTime = 0;
                            youHit.setVisible(true);
                        } else {
                            Gdx.app.log("Combat", "Attack Missed");
                            hitFeedbackTime = 0;
                            youMissed.setVisible(true);
                        }
                    }

                    weaponButtonGroup.uncheckAll();
                    roomButtonGroup.uncheckAll();
                    weaponSelected = null;
                    roomSelected = null;

                    //Runs enemy Combat Loop
                    if (combatEnemy.hasWepaonsReady()){
                        combatManager.enemyCombatLoop(combatEnemy, combatPlayer);
                        //Displays if Enemy Hit or Missed
                        if (combatManager.getShotHit()){
                            hitFeedbackTime = 0;
                            enemyHit.setVisible(true);
                        } else {
                            hitFeedbackTime = 0;
                            enemyMissed.setVisible(true);
                        }
                    } else {
                        for (Weapon weapon : enemyShip.getWeapons()) {
                            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
                        }
                        enemyShip.combatRepair();
                    }

                    if (playerShip.getHullHP() <= 0) {
                        gameOver = true;
                        gameWon = false;
                    } else if (enemyShip.getHullHP() <= 0) {
                        gameOver = true;
                        gameWon = true;
                    }
                    hitFeedbackTime = 0;

                }
                return true;
            }
        });

        fire.setChecked(false);
        weaponButtonGroup.uncheckAll();
    }

    private ArrayList<Label> cooldownList;

    /**
     * Draws Text displaying weapon cooldowns to the user
     */
    private void drawWeaponCooldowns() {
        cooldownList = new ArrayList<Label>();
        for  (int i = 0; i < playerShip.getWeapons().size() + 1; i++) {
            cooldownList.add(new Label("", skin));
            Gdx.app.debug("Combat DEBUG", "Cooldown drawn for weapon "+i);
        }
    }

    private ArrayList<Label> playerRoomList;
    private ArrayList<Label> enemyRoomList;
    /**
     * Sets up the Player and Enemy Room HP and a Stats display of Room Effectiveness
     */
    private void drawRoomHPEffectivness(){
        playerRoomList = new ArrayList<Label>();
        enemyRoomList = new ArrayList<Label>();
        for (int i = 0; i < 5; i++){
            playerShipTable.row().colspan(2).left();
            playerRoomList.add(new Label("", skin));
            playerShipTable.add(playerRoomList.get(i));

            enemyShipTable.row().colspan(2).left();
            enemyRoomList.add(new Label("", skin));
            enemyShipTable.add(enemyRoomList.get(i));
        }

    }

    /**
     * Draws buttons which display if you Win or Lose
     */
    private void drawEndButtons(){
        youWin = new TextButton("You win!", textButtonStyle);
        youWin.setOrigin(Align.center);
        youWin.setPosition(viewwidth/2, viewheight/2);
        mainStage.addActor(youWin);
        youWin.setVisible(false);

        youLose = new TextButton("You Lose :(", textButtonStyle);
        youLose.setOrigin(Align.center);
        youLose.setPosition(viewwidth/2, viewheight/2);
        mainStage.addActor(youLose);
        youLose.setVisible(false);
    }

    /**
     * Draws buttons which display if the user/Enemy hit or missed
     */
    private void drawHitMissButtons(){
        youHit = createHitMissButtons("You Hit!",false);
        youMissed = createHitMissButtons("You Missed!", false);
        enemyHit = createHitMissButtons("Enemy Hit!", true);
        enemyMissed = createHitMissButtons("Enemy Missed!", true);
    }

    private TextButton createHitMissButtons(String message, boolean enemyMsg){
        TextButton button = new TextButton(message, textButtonStyle);
        if (!enemyMsg){
            button.setPosition(2*viewwidth/3, viewheight/2);
        }
        else{
            button.setPosition(viewwidth/3,viewheight/2);
        }
        mainStage.addActor(button);
        button.setVisible(false);
        return button;
    }
}
