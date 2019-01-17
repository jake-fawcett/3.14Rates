package display;

import banks.WeaponBank;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import combat.items.Weapon;
import game_manager.GameManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class departmentScreen implements Screen {
    private GameManager game = new GameManager(null, null);

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    private Boolean toggleShop = true;

    //Setting up button to return to Menu, buttonSpriteSheet used for all buttons
    private TextButton toMenu;
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle myTextButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas menuButtonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
    private Skin skin = new Skin();

    private Texture departmentBackground = new Texture("battleBackground.png");

    //Heath Bar
    private Texture hpBackground = new Texture("background.png");
    private Texture hpDisabledBackground = new Texture("disabledBackground.png");
    private ProgressBar.ProgressBarStyle healthBarStyle = new ProgressBar.ProgressBarStyle();
    private ProgressBar healthBar;

    private BitmapFont indicatorFont = new BitmapFont();

    //Friendly Ship Sprite Setup
    private Texture shipBackground = new Texture("shipBackground.png");
    private Sprite playerShipBackground = new Sprite(shipBackground);
    private TextureAtlas buttonAtlas = new TextureAtlas("roomSpriteSheet.txt");
    private Sprite friendlyCrewQuaters = buttonAtlas.createSprite("crewQuaters");
    private Sprite friendlyCrowsNest = buttonAtlas.createSprite("crowsNest");
    private Sprite friendlyGunDeck = buttonAtlas.createSprite("gunDeck");
    private Sprite friendlyHelm = buttonAtlas.createSprite("helm");
    private Sprite friendlyEmptyRoom1 = buttonAtlas.createSprite("EmptyRoom");
    private Sprite friendlyEmptyRoom2 = buttonAtlas.createSprite("EmptyRoom");
    private Sprite friendlyEmptyRoom3 = buttonAtlas.createSprite("EmptyRoom");
    private Sprite friendlyEmptyRoom4 = buttonAtlas.createSprite("EmptyRoom");

    //Department Sprites
    private Sprite computerScience = new Sprite (new Texture("ComputerScIsland.png"));
    private Sprite lawAndManagment = new Sprite (new Texture("LMI.png"));
    private int randDepartment;

    //Shop
    private TextButton openShop;
    private Sprite shopBackground = new Sprite (new Texture("shopBackground.png"));

    //Shop items
    private BitmapFont weaponTitleFont = new BitmapFont();
    private BitmapFont weaponDetailsFont = new BitmapFont();

    private List<Weapon> weapons = new ArrayList<Weapon>();
    private TextButton buyWeapon1;
    private TextButton buyWeapon2;
    private TextButton buyWeapon3;
    private int randWeapon1;
    private int randWeapon2;

    private List<Weapon> playerWeapons = new ArrayList();
    private TextButton sellWeapon1;
    private TextButton sellWeapon2;
    private TextButton sellWeapon3;
    private TextButton sellWeapon4;

    @Override
    public void show() {
        //Creates Shop Weapon List
        weapons.add(WeaponBank.SEPR.getWeapon());
        weapons.add(WeaponBank.LAWBRINGER.getWeapon());
        weapons.add(WeaponBank.CRITTER.getWeapon());
        weapons.add(WeaponBank.STORM.getWeapon());
        weapons.add(WeaponBank.BOOM.getWeapon());
        weapons.add(WeaponBank.GATLING.getWeapon());
        weapons.add(WeaponBank.MORTAR.getWeapon());
        weapons.add(WeaponBank.SCATTER.getWeapon());
        weapons.add(WeaponBank.TREB.getWeapon());
        weapons.add(WeaponBank.WATER.getWeapon());
        weapons.add(WeaponBank.WIN.getWeapon());

        //Sets up button textures
        skin.addRegions(menuButtonAtlas);
        myTextButtonStyle.font = buttonFont;
        myTextButtonStyle.up = skin.getDrawable("buttonUp");
        myTextButtonStyle.down = skin.getDrawable("buttonDown");

        //TODO switching back to main menu doesnt work
        toMenu = new TextButton("To Menu", myTextButtonStyle);
        toMenu.setPosition(880,980);
        toMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.getGame().setScreen(new menuScreen(game));
                return true;
            }
        });
        stage.addActor(toMenu);

        //Heath Bar
        healthBarStyle.background = new TextureRegionDrawable(new TextureRegion(hpBackground));
        healthBarStyle.disabledBackground = new TextureRegionDrawable(new TextureRegion(hpDisabledBackground));
        healthBar = new ProgressBar(0,1000,1,false, healthBarStyle);
        healthBar.setValue(500);
        healthBar.setWidth(256);
        healthBar.setHeight(64);
        healthBar.setPosition(50,950);
        stage.addActor(healthBar);

        //Set up Player Ship Sprite
        playerShipBackground.setPosition(100,256);
        friendlyCrewQuaters.setPosition(100,256);
        friendlyCrowsNest.setPosition(228,256);
        friendlyEmptyRoom1.setPosition(100,384);
        friendlyEmptyRoom2.setPosition(228,384);
        friendlyGunDeck.setPosition(100,512);
        friendlyEmptyRoom3.setPosition(228,512);
        friendlyEmptyRoom4.setPosition(100,640);
        friendlyHelm.setPosition(228,640);

        Random rand = new Random();
        randDepartment = rand.nextInt(2);

        openShop = new TextButton("Shop", myTextButtonStyle);
        openShop.setPosition(350,960);
        openShop.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (toggleShop) {
                    shopBackground.setAlpha(0.85f);
                    weaponTitleFont.setColor(1, 1, 1, 1);
                    weaponDetailsFont.setColor(1, 1, 1, 1);
                    buyWeapon1.setColor(1, 1, 1, 1f);
                    buyWeapon2.setColor(1, 1, 1, 1f);
                    buyWeapon3.setColor(1, 1, 1, 1f);
                    toggleShop = false;
                } else {
                    shopBackground.setAlpha(0);
                    weaponTitleFont.setColor(1, 1, 1, 0);
                    weaponDetailsFont.setColor(1, 1, 1, 0);
                    buyWeapon1.setColor(1, 1, 1, 0);
                    buyWeapon2.setColor(1, 1, 1, 0);
                    buyWeapon3.setColor(1, 1, 1, 0);
                    toggleShop = true;
                }
                return true;
            }
        });
        stage.addActor(openShop);

        shopBackground.setScale(1.5f,1.5f);
        shopBackground.setAlpha(0);
        shopBackground.setPosition(256,256);

        //Shop Items
        weaponTitleFont.setColor(1f,1f,1f,0f);
        weaponTitleFont.getData().setScale(1.5f);
        weaponDetailsFont.setColor(1f,1f,1f,0f);
        weaponDetailsFont.getData().setScale(1f);

        randWeapon1 = rand.nextInt(9) + 2;
        randWeapon2 = rand.nextInt(9) + 2;

        buyWeapon1 = new TextButton("Buy (" + weapons.get(randDepartment).getCost() + "g)", myTextButtonStyle);
        buyWeapon1.setPosition(160, 740);
        stage.addActor(buyWeapon1);
        buyWeapon1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (game.getGold() >= weapons.get(randDepartment).getCost()){ //TODO Check empty weapons slots > 0
                    buyWeapon1.setDisabled(true);
                    buyWeapon1.setText("Purchased!");
                    game.deductGold(weapons.get(randDepartment).getCost());
                    game.getPlayerShip().addWeapon(weapons.get(randDepartment));
                } else {
                    buyWeapon1.setText("Insufficient Gold!");
                }
                return true;
            }
        });

        buyWeapon2 = new TextButton("Buy (" + weapons.get(randWeapon1).getCost() + "g)", myTextButtonStyle);
        buyWeapon2.setPosition(160, 590);
        stage.addActor(buyWeapon2);
        buyWeapon2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (game.getGold() >= weapons.get(randWeapon1).getCost()){
                    buyWeapon2.setDisabled(true);
                    buyWeapon2.setText("Purchased!");
                    game.deductGold(weapons.get(randWeapon1).getCost());
                    game.getPlayerShip().addWeapon(weapons.get(randWeapon1));
                } else {
                    buyWeapon2.setText("Insufficient Gold!");
                }
                return true;
            }
        });

        buyWeapon3 = new TextButton("Buy (" + weapons.get(randWeapon2).getCost() + "g)", myTextButtonStyle);
        buyWeapon3.setPosition(160, 450);
        stage.addActor(buyWeapon3);
        buyWeapon3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (game.getGold() >= weapons.get(randWeapon2).getCost()){
                    buyWeapon3.setDisabled(true);
                    buyWeapon3.setText("Purchased!");
                    game.deductGold(weapons.get(randWeapon2).getCost());
                    game.getPlayerShip().addWeapon(weapons.get(randWeapon2));
                } else {
                    buyWeapon3.setText("Insufficient Gold!");
                }
                return true;
            }
        });

        buyWeapon1.setColor(1,1,1,0f);
        buyWeapon2.setColor(1,1,1,0f);
        buyWeapon3.setColor(1,1,1,0f);

        playerWeapons = game.getPlayerShip().getWeapons();

        sellWeapon1 = new TextButton("Sell (" + playerWeapons.get(0).getCost() / 2 + "g)", myTextButtonStyle);
        sellWeapon1.setPosition(160, 450);
        stage.addActor(sellWeapon1);
        buyWeapon3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if (playerWeapons.get(0) instanceof Weapon){
                    sellWeapon1.setDisabled(true);
                    sellWeapon1.setText("Sold!");
                    game.addGold(playerWeapons.get(0).getCost() / 2);
                    //game.getPlayerShip().del; TODO Method to check if a ship has a weapon and delete weapon
                } else {
                    buyWeapon3.setText("Insufficient Gold!");
                }
                return true;
            }
        });

        }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        batch.draw(departmentBackground,0,0);

        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
        friendlyHelm.draw(batch);

        switch (randDepartment) {
            case 0:
                computerScience.draw(batch);
                computerScience.setPosition(500,256);

                shopBackground.draw(batch);

                weaponTitleFont.draw(batch, weapons.get(0).getName(), 160,880);
                weaponDetailsFont.draw(batch, "Damage: " + weapons.get(0).getBaseDamage(), 160, 850);
                weaponDetailsFont.draw(batch, "Cooldown: " + weapons.get(0).getCurrentCooldown(), 160,830);
                weaponDetailsFont.draw(batch, "Crit Chance: " + weapons.get(0).getBaseCritChance(), 160, 810);
                weaponDetailsFont.draw(batch, "Hit Chance: " + weapons.get(0).getBaseChanceToHit(), 160, 790);
                break;
            case 1:
                lawAndManagment.draw(batch);
                lawAndManagment.setPosition(400,256);

                shopBackground.draw(batch);

                weaponTitleFont.draw(batch, weapons.get(1).getName(), 160,880);
                weaponDetailsFont.draw(batch, "Damage: " + weapons.get(1).getBaseDamage(), 160, 850);
                weaponDetailsFont.draw(batch, "Cooldown: " + weapons.get(1).getCurrentCooldown(), 160,830);
                weaponDetailsFont.draw(batch, "Crit Chance: " + weapons.get(1).getBaseCritChance(), 160, 810);
                weaponDetailsFont.draw(batch, "Hit Chance: " + weapons.get(1).getBaseChanceToHit(), 160, 790);
                break;
        }

        indicatorFont.setColor(1f,1f,1f,1f);
        indicatorFont.draw(batch, "Score: " + game.getPoints(), 55, 970);
        indicatorFont.draw(batch, "Gold: " + game.getGold(), 120, 970);
        indicatorFont.draw(batch, "Crew: " + game.getPlayerShip().getCrew(), 185, 970);
        indicatorFont.draw(batch, "Food: " + game.getFood(), 250, 970);

        weaponTitleFont.draw(batch, weapons.get(randWeapon1).getName(), 160,730);
        weaponDetailsFont.draw(batch, "Damage: " + weapons.get(randWeapon1).getBaseDamage(), 160, 700);
        weaponDetailsFont.draw(batch, "Cooldown: " + weapons.get(randWeapon1).getCurrentCooldown(), 160,680);
        weaponDetailsFont.draw(batch, "Crit Chance: " + weapons.get(randWeapon1).getBaseCritChance(), 160, 660);
        weaponDetailsFont.draw(batch, "Hit Chance: " + weapons.get(randWeapon1).getBaseChanceToHit(), 160, 640);

        weaponTitleFont.draw(batch, weapons.get(randWeapon2).getName(), 160,580);
        weaponDetailsFont.draw(batch, "Damage: " + weapons.get(randWeapon2).getBaseDamage(), 160, 560);
        weaponDetailsFont.draw(batch, "Cooldown: " + weapons.get(randWeapon2).getCurrentCooldown(), 160,540);
        weaponDetailsFont.draw(batch, "Crit Chance: " + weapons.get(randWeapon2).getBaseCritChance(), 160, 520);
        weaponDetailsFont.draw(batch, "Hit Chance: " + weapons.get(randWeapon2).getBaseChanceToHit(), 160, 500);

        if (playerWeapons.get(0) != null) {
            weaponTitleFont.draw(batch, playerWeapons.get(0).getName(), 500, 880);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(0).getBaseDamage(), 500, 850);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(0).getCurrentCooldown(), 500,830);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(0).getBaseCritChance(), 500, 810);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(0).getBaseChanceToHit(), 500, 790);
        } else {
            weaponTitleFont.draw(batch, "Empty Slot", 500, 880);
        }

        if (playerWeapons.get(1) != null) {
            weaponTitleFont.draw(batch, playerWeapons.get(1).getName(), 500, 730);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(1).getBaseDamage(), 500, 700);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(1).getCurrentCooldown(), 500,680);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(1).getBaseCritChance(), 500, 660);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(1).getBaseChanceToHit(), 500, 640);
        } else {
            weaponTitleFont.draw(batch, "Empty Slot", 500, 730);
        }

        if (playerWeapons.get(2) != null) {
            weaponTitleFont.draw(batch, playerWeapons.get(2).getName(), 500, 580);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(2).getBaseDamage(), 500, 550);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(2).getCurrentCooldown(), 500,530);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(2).getBaseCritChance(), 500, 510);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(2).getBaseChanceToHit(), 500, 490);
        } else {
            weaponTitleFont.draw(batch, "Empty Slot", 500, 580);
        }

        if (playerWeapons.get(3) != null) {
            weaponTitleFont.draw(batch, playerWeapons.get(3).getName(), 500, 430);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(3).getBaseDamage(), 500, 400);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(3).getCurrentCooldown(), 500,380);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(3).getBaseCritChance(), 500, 360);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(3).getBaseChanceToHit(), 500, 340);
        } else {
            weaponTitleFont.draw(batch, "Empty Slot", 500, 430);
        }

        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
