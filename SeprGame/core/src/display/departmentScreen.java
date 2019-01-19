package display;

import banks.CoordBank;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Ship;
import game_manager.GameManager;
import location.Department;
import other.Resource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static banks.RoomUpgradeSetBank.COMP_SCI_UPGRADES;
import static banks.RoomUpgradeSetBank.LMB_UPGRADES;
import static banks.WeaponSetBank.COMP_SCI_WEPS;
import static banks.WeaponSetBank.LMB_WEPS;
import static other.Constants.*;

public class departmentScreen implements Screen {
    private Game game;
    public departmentScreen(Game game){
        this.game = game;
    }

    private GameManager gameManager = new GameManager(null, null);
    private Ship playerShip = gameManager.getPlayerShip();

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    private DecimalFormat df;

    private int randInt = pickRandom(2);
    private Department department = assignDepartment(randInt);

    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas;
    private Skin skin = new Skin();

    private Boolean boolShowShop = false;
    private Sprite shopBackground;
    private BitmapFont titleFont = new BitmapFont();
    private BitmapFont bodyFont = new BitmapFont();

    @Override
    public void show() {
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");

        shopBackground = createShopBackground();
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        drawBackground();
        drawFriendlyShip();
        drawDepartment(randInt);

        buttonToMenu(textButtonStyle);

        drawHealthBar();
        drawIndicators();

        drawShopBackground(shopBackground, boolShowShop);
        drawBuyWeaponFeatures(titleFont, bodyFont, textButtonStyle);
        drawSellWeaponFeatures(titleFont, bodyFont, textButtonStyle);
        drawBuyRoomUpgradeFeatures(titleFont, bodyFont, textButtonStyle);
        drawBuyResourceFeatures(titleFont);

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
        stage.dispose();
        batch.dispose();
    }

    public int pickRandom(int max) {
        Random rand = new Random();
        int randInt = rand.nextInt(max);

        return randInt;
    }

    public void drawBackground() {
        Texture background = new Texture("battleBackground.png");
        batch.draw(background, 0, 0);
    }

    public void drawFriendlyShip(){
        TextureAtlas roomSpriteAtlas = new TextureAtlas("roomSpriteSheet.txt");

        Sprite friendlyCrewQuaters = roomSpriteAtlas.createSprite("crewQuaters");
        friendlyCrewQuaters.setPosition(CoordBank.FRIENDLY_CREWQUATERS.getX(),CoordBank.FRIENDLY_CREWQUATERS.getY());

        Sprite friendlyEmptyRoom1 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom1.setPosition(CoordBank.FRIENDLY_EMPTYROOM1.getX(),CoordBank.FRIENDLY_EMPTYROOM1.getY());

        Sprite friendlyCrowsNest = roomSpriteAtlas.createSprite("crowsNest");
        friendlyCrowsNest.setPosition(CoordBank.FRIENDLY_CROWSNEST.getX(),CoordBank.FRIENDLY_CROWSNEST.getY());

        Sprite friendlyGunDeck = roomSpriteAtlas.createSprite("gunDeck");
        friendlyGunDeck.setPosition(CoordBank.FRIENDLY_GUNDECK.getX(),CoordBank.FRIENDLY_GUNDECK.getY());

        Sprite friendlyEmptyRoom2 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom2.setPosition(CoordBank.FRIENDLY_EMPTYROOM2.getX(),CoordBank.FRIENDLY_EMPTYROOM2.getY());

        Sprite friendlyHelm = roomSpriteAtlas.createSprite("helm");
        friendlyHelm.setPosition(CoordBank.FRIENDLY_HELM.getX(),CoordBank.FRIENDLY_HELM.getY());

        Sprite friendlyEmptyRoom3 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom3.setPosition(CoordBank.FRIENDLY_EMPTYROOM3.getX(),CoordBank.FRIENDLY_EMPTYROOM3.getY());

        Sprite friendlyEmptyRoom4 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom4.setPosition(CoordBank.FRIENDLY_EMPTYROOM4.getX(),CoordBank.FRIENDLY_EMPTYROOM4.getY());

        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyHelm.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
    }

    public Department assignDepartment(int randInt) {
        switch (randInt) {
            case 0:
                return (new Department(COMP_SCI_WEPS.getWeaponList(), COMP_SCI_UPGRADES.getRoomUpgradeList(), gameManager));
            case 1:
                return (new Department(LMB_WEPS.getWeaponList(), LMB_UPGRADES.getRoomUpgradeList(), gameManager));
        }
        return null;
    }

    public void drawDepartment(int randInt) {
        switch (randInt) {
            case 0:
                Texture computerScienceTexture = new Texture("ComputerScIsland.png");
                Sprite computerScienceSprite = new Sprite(computerScienceTexture);
                batch.draw(computerScienceSprite,500,256);
                break;
            case 1:
                Texture lawAndManagementTexture = new Texture("LMI.png");
                Sprite lawAndManagementSprite = new Sprite(lawAndManagementTexture);
                batch.draw(lawAndManagementSprite,400,200);
                break;
        }
    }

    public void drawHealthBar() {
        Texture hpBar = new Texture("background.png");
        Texture hpBackground = new Texture("disabledBackground.png");

        double defaultWidth = 320;
        int width = (int)(defaultWidth * ((double)playerShip.getHullHP() / (double)playerShip.getBaseHullHP()));

        batch.draw(hpBackground,25, 970, 320, 16);
        batch.draw(hpBar,25, 970, width, 16);
    }

    public void drawIndicators(){
        BitmapFont indicatorFont = new BitmapFont();
        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + gameManager.getPoints(), 25, 965);
        indicatorFont.draw(batch, "Gold: " + gameManager.getGold(), 110, 965);
        indicatorFont.draw(batch, "Food: " + gameManager.getFood(), 195, 965);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), 280, 965);
    }

    public void buttonToMenu(TextButton.TextButtonStyle textButtonStyle){
        TextButton toMenu = new TextButton("To Menu", textButtonStyle);
        toMenu.setPosition(880, 980);
        toMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new menuScreen(game));
                return true;
            }
        });
        stage.addActor(toMenu);
    }

    public Sprite createShopBackground(){
        Texture shopBackgroundTexture = new Texture("shopBackground.png");
        Sprite shopBackgroundSprite = new Sprite(shopBackgroundTexture);

        return shopBackgroundSprite;
    }

    public void drawShopBackground(Sprite shopBackground,Boolean showShop) {
        shopBackground.draw(batch);
        shopBackground.setScale(1.5f, 1.5f);
        shopBackground.setPosition(256, 256);
        shopBackground.setAlpha(0.85f);
    }

    public void drawBuyWeaponFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
        List<TextButton> buyButtonList = new ArrayList<TextButton>();
        List<Weapon> weaponList = new ArrayList<Weapon>();

        int i = 0;
        while (i <= department.getWeaponStock().size() - 1 && department.getWeaponStock().get(i) instanceof Weapon) {
            weaponList.add(department.getWeaponStock().get(i));
            i++;
        }

        int j = 0;
        while (j <= weaponList.size() - 1){
            titleFont.draw(batch, weaponList.get(j).getName(), 160, 880 - (150 * j));
            bodyFont.draw(batch, "Damage: " + df.format(weaponList.get(j).getBaseDamage()), 160, 850 - (150 * j));
            bodyFont.draw(batch, "Crit Chance: " + df.format(weaponList.get(j).getBaseCritChance()), 160, 830 - (150 * j));
            bodyFont.draw(batch, "Hit Chance: " + df.format(weaponList.get(j).getBaseChanceToHit()), 160, 810 - (150 * j));
            bodyFont.draw(batch, "Cooldown: " + df.format(weaponList.get(j).getBaseCooldown()), 160, 790 - (150 * j));

            buyButtonList.add(new TextButton("Buy (" + weaponList.get(j).getCost() + "g)", textButtonStyle));
            buyButtonList.get(j).setPosition(160, 740 - (j * 150));
            stage.addActor(buyButtonList.get(j));
            j++;
        }

        buyWeaponButtonListener(buyButtonList, weaponList);
    }

    public void buyWeaponButtonListener(final List<TextButton> buyButtonList, final List<Weapon> weaponList) {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        department.buyWeapon(weaponList.get(j));
                        stage.clear();
                    } catch (IllegalStateException e) {
                        buyButtonList.get(j).setText("Insufficient Gold!");
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage() == "Weapon does not exist") {
                            buyButtonList.get(j).setText("Out of Stock :(");
                        } else if (e.getMessage() == "Not enough gold") {
                            buyButtonList.get(j).setText("Insufficient Gold!");
                        }
                    }

                    return true;
                }
            });

            i++;
        }
    }

    public void drawSellWeaponFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
        List<TextButton> sellButtonList = new ArrayList<TextButton>();
        List<Weapon> weaponList = new ArrayList<Weapon>();

        int i = 0;
        while (i <= playerShip.getWeapons().size() - 1 && playerShip.getWeapons().get(i) instanceof Weapon) {
            weaponList.add(playerShip.getWeapons().get(i));
            i++;
        }

        int j = 0;
        while (j <= weaponList.size() - 1){
            titleFont.draw(batch, weaponList.get(j).getName(), 360, 880 - (150 * j));
            bodyFont.draw(batch, "Damage: " + df.format(weaponList.get(j).getBaseDamage()), 360, 850 - (150 * j));
            bodyFont.draw(batch, "Crit Chance: " + df.format(weaponList.get(j).getBaseCritChance()), 360, 830 - (150 * j));
            bodyFont.draw(batch, "Hit Chance: " + df.format(weaponList.get(j).getBaseChanceToHit()), 360, 810 - (150 * j));
            bodyFont.draw(batch, "Cooldown: " + df.format(weaponList.get(j).getBaseCooldown()), 360, 790 - (150 * j));

            sellButtonList.add(new TextButton("Sell (" + df.format(weaponList.get(j).getCost() * STORE_SELL_PRICE_MULTIPLIER) + "g)", textButtonStyle));
            sellButtonList.get(j).setPosition(360, 740 - (j * 150));
            stage.addActor(sellButtonList.get(j));
            j++;
        }
        sellButtonListener(sellButtonList, weaponList);
    }

    public void sellButtonListener(final List<TextButton> buyButtonList, final List<Weapon> weaponList) {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    try {
                        department.sellWeapon(weaponList.get(j));
                        stage.clear();
                    } catch (IllegalArgumentException e) {
                        buyButtonList.get(j).setText("Empty Slot!");
                    }
                    return true;
                }
            });

            i++;
        }

    }

    public void drawBuyRoomUpgradeFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
        List<TextButton> buyButtonList = new ArrayList<TextButton>();
        List<RoomUpgrade> roomUpgradeList = new ArrayList<RoomUpgrade>();
        int i = 0;
        while (i <= department.getUpgradeStock().size() - 1 && department.getUpgradeStock().get(i) instanceof RoomUpgrade) {
            roomUpgradeList.add(department.getUpgradeStock().get(i));
            i++;
        }

        int j = 0;
        while (j <= roomUpgradeList.size() - 1){
            titleFont.draw(batch, roomUpgradeList.get(j).getName(), 560, 880 - (150 * j));
            bodyFont.draw(batch, "Room: " + roomUpgradeList.get(j).getAffectsRoom(), 560, 830 - (150 * j));
            bodyFont.draw(batch, "Multiplier: " + df.format(roomUpgradeList.get(j).getMultiplier()), 560, 850 - (150 * j));

            buyButtonList.add(new TextButton("Buy (" + df.format(roomUpgradeList.get(j).getCost()) + "g)", textButtonStyle));
            buyButtonList.get(j).setPosition(560, 740 - (j * 150));
            stage.addActor(buyButtonList.get(j));
            j++;
        }
        buyRoomUpgradeButtonListener(buyButtonList, roomUpgradeList);
    }

    public void buyRoomUpgradeButtonListener(final List<TextButton> buyButtonList, final List<RoomUpgrade> roomUpgradeList) {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    try {
                        department.buyRoomUpgrade(roomUpgradeList.get(j));
                        stage.clear();
                    } catch (IllegalStateException e) {
                        buyButtonList.get(j).setText("Insufficient Gold!");
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage() == "Room does not exist") {
                            buyButtonList.get(j).setText("Out of Stock :(");
                        } else if (e.getMessage() == "Not enough gold") {
                            buyButtonList.get(j).setText("Insufficient Gold!");
                        }
                    }
                    return true;
                }
            });

            i++;
        }

    }

    public void drawBuyResourceFeatures(BitmapFont titleFont){
        List<TextButton> buyButtonList = new ArrayList<TextButton>();

        titleFont.draw(batch, "Crew", 160, 200);
        titleFont.draw(batch, "Food", 360, 200);
        titleFont.draw(batch, "Repair", 560, 200);

        buyButtonList.add(new TextButton("Buy (" + CREW_COST + "g)", textButtonStyle));
        buyButtonList.get(0).setPosition(220, 180);
        stage.addActor(buyButtonList.get(0));

        buyButtonList.add(new TextButton("Buy (" + FOOD_COST + "g)", textButtonStyle));
        buyButtonList.get(1).setPosition(420, 180);
        stage.addActor(buyButtonList.get(1));

        buyButtonList.add(new TextButton("Buy (" + REPAIR_COST + "g)", textButtonStyle));
        buyButtonList.get(2).setPosition(620, 180);
        stage.addActor(buyButtonList.get(2));

        buyResourceButtonListener(buyButtonList);
    }

    public void buyResourceButtonListener(final List<TextButton> buyButtonList){
        buyButtonList.get(0).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        department.buyResource(Resource.CREW, SHOP_CREW_AMOUNT);
                        stage.clear();
                    } catch (IllegalStateException e) {
                    } catch (IllegalArgumentException e) {
                    }
                    return true;
                }
            });
        buyButtonList.get(1).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    department.buyResource(Resource.FOOD, SHOP_FOOD_AMOUNT);
                    stage.clear();
                } catch (IllegalStateException e) {
                } catch (IllegalArgumentException e) {
                }
                return true;
            }
        });
        buyButtonList.get(2).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    department.buyResource(Resource.REPAIR, SHOP_REPAIR_AMOUNT);
                    stage.clear();
                } catch (IllegalStateException e) {
                } catch (IllegalArgumentException e) {
                }
                return true;
            }
        });
    }


}

