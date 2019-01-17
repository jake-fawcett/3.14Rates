package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import combat.items.Weapon;
import game_manager.GameManager;
import location.Department;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static banks.RoomUpgradeSetBank.COMP_SCI_UPGRADES;
import static banks.RoomUpgradeSetBank.LMB_UPGRADES;
import static banks.WeaponSetBank.COMP_SCI_WEPS;
import static banks.WeaponSetBank.LMB_WEPS;
import static other.Constants.STORE_SELL_PRICE_MULTIPLIER;

public class departmentScreen implements Screen {
    private GameManager game = new GameManager(null, null);

    private Department compSci = new Department(COMP_SCI_WEPS.getWeaponList(), COMP_SCI_UPGRADES.getRoomUpgradeList(), game);
    private Department lmb = new Department(LMB_WEPS.getWeaponList(), LMB_UPGRADES.getRoomUpgradeList(), game);
    private List<Department> departmentList = new ArrayList();

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
    private Sprite computerScience = new Sprite(new Texture("ComputerScIsland.png"));
    private Sprite lawAndManagment = new Sprite(new Texture("LMI.png"));
    private int randDepartment;

    //Shop
    private TextButton openShop;
    private Sprite shopBackground = new Sprite(new Texture("shopBackground.png"));

    //Shop items
    private BitmapFont weaponTitleFont = new BitmapFont();
    private BitmapFont weaponDetailsFont = new BitmapFont();

    private List<Weapon> weapons = new ArrayList<Weapon>();
    private TextButton buyWeapon1;
    private TextButton buyWeapon2;
    private TextButton buyWeapon3;

    private List<Weapon> playerWeapons = new ArrayList();
    private TextButton sellWeapon1;
    private TextButton sellWeapon2;
    private TextButton sellWeapon3;
    private TextButton sellWeapon4;

    @Override
    public void show() {
        //Sets up button textures
        skin.addRegions(menuButtonAtlas);
        myTextButtonStyle.font = buttonFont;
        myTextButtonStyle.up = skin.getDrawable("buttonUp");
        myTextButtonStyle.down = skin.getDrawable("buttonDown");

        //TODO switching back to main menu doesnt work
        toMenu = new TextButton("To Menu", myTextButtonStyle);
        toMenu.setPosition(880, 980);
        toMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getGame().setScreen(new menuScreen(game));
                return true;
            }
        });
        stage.addActor(toMenu);

        //Heath Bar
        healthBarStyle.background = new TextureRegionDrawable(new TextureRegion(hpBackground));
        healthBarStyle.disabledBackground = new TextureRegionDrawable(new TextureRegion(hpDisabledBackground));
        healthBar = new ProgressBar(0, 1000, 1, false, healthBarStyle);
        healthBar.setValue(500);
        healthBar.setWidth(256);
        healthBar.setHeight(64);
        healthBar.setPosition(50, 950);
        stage.addActor(healthBar);

        //Set up Player Ship Sprite
        playerShipBackground.setPosition(100, 256);
        friendlyCrewQuaters.setPosition(100, 256);
        friendlyCrowsNest.setPosition(228, 256);
        friendlyEmptyRoom1.setPosition(100, 384);
        friendlyEmptyRoom2.setPosition(228, 384);
        friendlyGunDeck.setPosition(100, 512);
        friendlyEmptyRoom3.setPosition(228, 512);
        friendlyEmptyRoom4.setPosition(100, 640);
        friendlyHelm.setPosition(228, 640);

        departmentList.add(compSci);
        departmentList.add(lmb);
        Random rand = new Random();
        randDepartment = rand.nextInt(2);
        System.out.print(randDepartment);


        openShop = new TextButton("Shop", myTextButtonStyle);
        openShop.setPosition(350, 960);
        openShop.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (toggleShop) {
                    shopBackground.setAlpha(0.85f);
                    weaponTitleFont.setColor(1, 1, 1, 1);
                    weaponDetailsFont.setColor(1, 1, 1, 1);
                    buyWeapon1.setColor(1, 1, 1, 1f);
                    buyWeapon2.setColor(1, 1, 1, 1f);
                    buyWeapon3.setColor(1, 1, 1, 1f);
                    if (sellWeapon1 != null) {
                        sellWeapon1.setColor(1, 1, 1, 1f);
                    }
                    if (sellWeapon2 != null) {
                        sellWeapon1.setColor(1, 1, 1, 1f);
                    }
                    if (sellWeapon3 != null) {
                        sellWeapon1.setColor(1, 1, 1, 1f);
                    }
                    if (sellWeapon4 != null) {
                        sellWeapon1.setColor(1, 1, 1, 1f);
                    }
                    toggleShop = false;
                } else {
                    shopBackground.setAlpha(0);
                    weaponTitleFont.setColor(1, 1, 1, 0);
                    weaponDetailsFont.setColor(1, 1, 1, 0);
                    buyWeapon1.setColor(1, 1, 1, 0);
                    buyWeapon2.setColor(1, 1, 1, 0);
                    buyWeapon3.setColor(1, 1, 1, 0);
                    if (sellWeapon1 != null) {
                        sellWeapon1.setColor(1, 1, 1, 0);
                    }
                    if (sellWeapon2 != null) {
                        sellWeapon1.setColor(1, 1, 1, 0);
                    }
                    if (sellWeapon3 != null) {
                        sellWeapon1.setColor(1, 1, 1, 0);
                    }
                    if (sellWeapon4 != null) {
                        sellWeapon1.setColor(1, 1, 1, 0);
                    }
                    toggleShop = true;
                }
                return true;
            }
        });
        stage.addActor(openShop);

        shopBackground.setScale(1.5f, 1.5f);
        shopBackground.setAlpha(0);
        shopBackground.setPosition(256, 256);

        //Shop Items
        weaponTitleFont.setColor(1f, 1f, 1f, 0f);
        weaponTitleFont.getData().setScale(1.5f);
        weaponDetailsFont.setColor(1f, 1f, 1f, 0f);
        weaponDetailsFont.getData().setScale(1f);


        buyWeapon1 = new TextButton("Buy (" + departmentList.get(randDepartment).getWeaponStock().get(0).getCost() + "g)", myTextButtonStyle);
        buyWeapon1.setPosition(160, 740);
        stage.addActor(buyWeapon1);
        buyWeapon1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyWeapon1.setTouchable(Touchable.disabled);
                buyWeapon1.setText("Purchased!");

                try {
                    departmentList.get(randDepartment).buyWeapon(departmentList.get(randDepartment).getWeaponStock().get(0));
                } catch (IllegalStateException e) {
                    buyWeapon1.setTouchable(Touchable.disabled);
                    buyWeapon1.setText("Insufficient Gold!");
                }
                return true;
            }
        });

        buyWeapon2 = new TextButton("Buy (" + departmentList.get(randDepartment).getWeaponStock().get(1).getCost() + "g)", myTextButtonStyle);
        buyWeapon2.setPosition(160, 590);
        stage.addActor(buyWeapon2);
        buyWeapon2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyWeapon2.setTouchable(Touchable.disabled);
                buyWeapon2.setText("Purchased!");

                departmentScreen test = new departmentScreen();
                game.setScreen(test);

                try {
                    departmentList.get(randDepartment).buyWeapon(departmentList.get(randDepartment).getWeaponStock().get(1));
                } catch (IllegalStateException e) {
                    buyWeapon2.setTouchable(Touchable.disabled);
                    buyWeapon2.setText("Insufficient Gold!");
                } catch (IllegalArgumentException e) {
                    if (e.getMessage() == "Not enough gold") {
                        buyWeapon2.setTouchable(Touchable.disabled);
                        buyWeapon2.setText("Insufficient Gold!");
                    } else if (e.getMessage() == "Weapon does not exist") {

                    }
                }
                return true;
            }
        });

        buyWeapon3 = new TextButton("Buy (" + departmentList.get(randDepartment).getWeaponStock().get(2).getCost() + "g)", myTextButtonStyle);
        buyWeapon3.setPosition(160, 450);
        stage.addActor(buyWeapon3);
        buyWeapon3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyWeapon3.setTouchable(Touchable.disabled);
                buyWeapon3.setText("Purchased!");

                try {
                    departmentList.get(randDepartment).buyWeapon(departmentList.get(randDepartment).getWeaponStock().get(2));
                } catch (IllegalStateException e) {
                    buyWeapon3.setTouchable(Touchable.disabled);
                    buyWeapon3.setText("Insufficient Gold!");
                }
                return true;
            }
        });

        buyWeapon1.setColor(1, 1, 1, 0f);
        buyWeapon2.setColor(1, 1, 1, 0f);
        buyWeapon3.setColor(1, 1, 1, 0f);

        playerWeapons = game.getPlayerShip().getWeapons();

        if (playerWeapons.size() >= 1) {
            sellWeapon1 = new TextButton("Sell (" + (int) (playerWeapons.get(0).getCost() * STORE_SELL_PRICE_MULTIPLIER) + "g)", myTextButtonStyle);
            sellWeapon1.setPosition(500, 740);
            stage.addActor(sellWeapon1);
            sellWeapon1.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    sellWeapon1.setTouchable(Touchable.disabled);
                    sellWeapon1.setText("Sold!");
                    departmentList.get(randDepartment).sellWeapon(playerWeapons.get(0));
                    return true;
                }
            });
        }

        if (playerWeapons.size() >= 2) {
            sellWeapon2 = new TextButton("Sell (" + (int) (playerWeapons.get(1).getCost() * STORE_SELL_PRICE_MULTIPLIER) + "g)", myTextButtonStyle);
            sellWeapon2.setPosition(500, 590);
            stage.addActor(sellWeapon2);
            sellWeapon2.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    sellWeapon2.setTouchable(Touchable.disabled);
                    sellWeapon2.setText("Sold!");
                    departmentList.get(randDepartment).sellWeapon(playerWeapons.get(1));
                    return true;
                }
            });
        }

        if (playerWeapons.size() >= 3) {
            sellWeapon3 = new TextButton("Sell (" + (int) (playerWeapons.get(2).getCost() * STORE_SELL_PRICE_MULTIPLIER) + "g)", myTextButtonStyle);
            sellWeapon3.setPosition(500, 440);
            stage.addActor(sellWeapon3);
            sellWeapon3.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    sellWeapon3.setTouchable(Touchable.disabled);
                    sellWeapon3.setText("Sold!");
                    departmentList.get(randDepartment).sellWeapon(playerWeapons.get(2));
                    return true;
                }
            });
        }

        if (playerWeapons.size() >= 4) {
            sellWeapon4 = new TextButton("Sell (" + playerWeapons.get(3).getCost() * STORE_SELL_PRICE_MULTIPLIER + "g)", myTextButtonStyle);
            sellWeapon4.setPosition(500, 390);
            stage.addActor(sellWeapon4);
            sellWeapon4.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    sellWeapon4.setTouchable(Touchable.disabled);
                    sellWeapon4.setText("Sold!");
                    departmentList.get(randDepartment).sellWeapon(playerWeapons.get(3));
                    return true;
                }
            });
        }

        if (sellWeapon1 != null) {
            sellWeapon1.setColor(1, 1, 1, 0);
        }
        if (sellWeapon2 != null) {
            sellWeapon1.setColor(1, 1, 1, 0);
        }
        if (sellWeapon3 != null) {
            sellWeapon1.setColor(1, 1, 1, 0);
        }
        if (sellWeapon4 != null) {
            sellWeapon1.setColor(1, 1, 1, 0);
        }
    }

    public void drawButtons() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        batch.draw(departmentBackground, 0, 0);

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
                computerScience.setPosition(500, 256);
                break;
            case 1:
                lawAndManagment.draw(batch);
                lawAndManagment.setPosition(400, 256);
                break;
        }

        indicatorFont.setColor(1f, 1f, 1f, 1f);
        indicatorFont.draw(batch, "Score: " + game.getPoints(), 55, 970);
        indicatorFont.draw(batch, "Gold: " + game.getGold(), 120, 970);
        indicatorFont.draw(batch, "Crew: " + game.getPlayerShip().getCrew(), 185, 970);
        indicatorFont.draw(batch, "Food: " + game.getFood(), 250, 970);

        shopBackground.draw(batch);

        if (departmentList.get(randDepartment).getWeaponStock().get(0) instanceof Weapon) {
            weaponTitleFont.draw(batch, departmentList.get(randDepartment).getWeaponStock().get(0).getName(), 160, 880);
            weaponDetailsFont.draw(batch, "Damage: " + departmentList.get(randDepartment).getWeaponStock().get(0).getBaseDamage(), 160, 850);
            weaponDetailsFont.draw(batch, "Cooldown: " + departmentList.get(randDepartment).getWeaponStock().get(0).getCurrentCooldown(), 160, 830);
            weaponDetailsFont.draw(batch, "Crit Chance: " + departmentList.get(randDepartment).getWeaponStock().get(0).getBaseCritChance(), 160, 810);
            weaponDetailsFont.draw(batch, "Hit Chance: " + departmentList.get(randDepartment).getWeaponStock().get(0).getBaseChanceToHit(), 160, 790);
        }

        if (departmentList.get(randDepartment).getWeaponStock().get(1) instanceof Weapon) {
            weaponTitleFont.draw(batch, departmentList.get(randDepartment).getWeaponStock().get(1).getName(), 160, 730);
            weaponDetailsFont.draw(batch, "Damage: " + departmentList.get(randDepartment).getWeaponStock().get(1).getBaseDamage(), 160, 700);
            weaponDetailsFont.draw(batch, "Cooldown: " + departmentList.get(randDepartment).getWeaponStock().get(1).getCurrentCooldown(), 160, 680);
            weaponDetailsFont.draw(batch, "Crit Chance: " + departmentList.get(randDepartment).getWeaponStock().get(1).getBaseCritChance(), 160, 660);
            weaponDetailsFont.draw(batch, "Hit Chance: " + departmentList.get(randDepartment).getWeaponStock().get(1).getBaseChanceToHit(), 160, 640);
        }

        if (departmentList.get(randDepartment).getWeaponStock().get(2) instanceof Weapon) {
            weaponTitleFont.draw(batch, departmentList.get(randDepartment).getWeaponStock().get(2).getName(), 160, 580);
            weaponDetailsFont.draw(batch, "Damage: " + departmentList.get(randDepartment).getWeaponStock().get(2).getBaseDamage(), 160, 560);
            weaponDetailsFont.draw(batch, "Cooldown: " + departmentList.get(randDepartment).getWeaponStock().get(2).getCurrentCooldown(), 160, 540);
            weaponDetailsFont.draw(batch, "Crit Chance: " + departmentList.get(randDepartment).getWeaponStock().get(2).getBaseCritChance(), 160, 520);
            weaponDetailsFont.draw(batch, "Hit Chance: " + departmentList.get(randDepartment).getWeaponStock().get(2).getBaseChanceToHit(), 160, 500);
        }

        if (playerWeapons.size() >= 1) {
            weaponTitleFont.draw(batch, playerWeapons.get(0).getName(), 500, 880);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(0).getBaseDamage(), 500, 850);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(0).getCurrentCooldown(), 500, 830);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(0).getBaseCritChance(), 500, 810);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(0).getBaseChanceToHit(), 500, 790);
        }

        if (playerWeapons.size() >= 2) {
            weaponTitleFont.draw(batch, playerWeapons.get(1).getName(), 500, 730);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(1).getBaseDamage(), 500, 700);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(1).getCurrentCooldown(), 500, 680);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(1).getBaseCritChance(), 500, 660);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(1).getBaseChanceToHit(), 500, 640);
        }

        if (playerWeapons.size() >= 3) {
            weaponTitleFont.draw(batch, playerWeapons.get(2).getName(), 500, 580);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(2).getBaseDamage(), 500, 550);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(2).getCurrentCooldown(), 500, 530);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(2).getBaseCritChance(), 500, 510);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(2).getBaseChanceToHit(), 500, 490);
        }

        if (playerWeapons.size() >= 4) {
            weaponTitleFont.draw(batch, playerWeapons.get(3).getName(), 500, 430);
            weaponDetailsFont.draw(batch, "Damage: " + playerWeapons.get(3).getBaseDamage(), 500, 400);
            weaponDetailsFont.draw(batch, "Cooldown: " + playerWeapons.get(3).getCurrentCooldown(), 500, 380);
            weaponDetailsFont.draw(batch, "Crit Chance: " + playerWeapons.get(3).getBaseCritChance(), 500, 360);
            weaponDetailsFont.draw(batch, "Hit Chance: " + playerWeapons.get(3).getBaseChanceToHit(), 500, 340);
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
