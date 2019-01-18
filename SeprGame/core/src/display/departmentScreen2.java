package display;

import banks.CoordBank;
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
import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import game_manager.GameManager;
import location.Department;


import javax.xml.soap.Text;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static banks.RoomUpgradeSetBank.COMP_SCI_UPGRADES;
import static banks.RoomUpgradeSetBank.LMB_UPGRADES;
import static banks.WeaponSetBank.COMP_SCI_WEPS;
import static banks.WeaponSetBank.LMB_WEPS;
import static other.Constants.STORE_SELL_PRICE_MULTIPLIER;

public class departmentScreen2 implements Screen {
    private GameManager game = new GameManager(null, null);

    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    private DecimalFormat df;

    private int randInt = pickRandom(2);
    private Department department = assignDepartment(randInt);

    private Ship playerShip = game.getPlayerShip();

    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas;
    private Skin skin = new Skin();

    private Boolean boolShowShop = false;
    private Sprite shopBackground;
    private BitmapFont titleFont = new BitmapFont();
    private BitmapFont bodyFont = new BitmapFont();

    private List<TextButton> buyButtonList = new ArrayList<TextButton>();

    @Override
    public void show() {
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");

        buttonShowShop(textButtonStyle);
        buttonToMenu(textButtonStyle);

        shopBackground = createShopBackground();

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        drawBackground();
        drawFriendlyShip();
        drawDepartment(randInt);

        drawHealthBar();
        drawIndicators();

        drawShopBackground(shopBackground, boolShowShop);
        buyButtonList = drawBuyWeaponFeatures(titleFont, bodyFont, textButtonStyle);
        drawSellWeaponInformation(titleFont, bodyFont);

        drawBuyRoomUpgradeInformation(titleFont, bodyFont);

        toggleShop(boolShowShop, shopBackground, titleFont, bodyFont, buyButtonList);

        batch.end();

        stage.draw();
        stage.act();
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
        friendlyCrewQuaters.setPosition(CoordBank.FRIENDLY_ROOM1.getX(),CoordBank.FRIENDLY_ROOM1.getY());

        Sprite friendlyEmptyRoom1 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom1.setPosition(CoordBank.FRIENDLY_ROOM2.getX(),CoordBank.FRIENDLY_ROOM2.getY());

        Sprite friendlyCrowsNest = roomSpriteAtlas.createSprite("crowsNest");
        friendlyCrowsNest.setPosition(CoordBank.FRIENDLY_ROOM3.getX(),CoordBank.FRIENDLY_ROOM3.getY());

        Sprite friendlyGunDeck = roomSpriteAtlas.createSprite("gunDeck");
        friendlyGunDeck.setPosition(CoordBank.FRIENDLY_ROOM4.getX(),CoordBank.FRIENDLY_ROOM4.getY());

        Sprite friendlyEmptyRoom2 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom2.setPosition(CoordBank.FRIENDLY_ROOM5.getX(),CoordBank.FRIENDLY_ROOM5.getY());

        Sprite friendlyHelm = roomSpriteAtlas.createSprite("helm");
        friendlyHelm.setPosition(CoordBank.FRIENDLY_ROOM6.getX(),CoordBank.FRIENDLY_ROOM6.getY());

        Sprite friendlyEmptyRoom3 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom3.setPosition(CoordBank.FRIENDLY_ROOM7.getX(),CoordBank.FRIENDLY_ROOM7.getY());

        Sprite friendlyEmptyRoom4 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom4.setPosition(CoordBank.FRIENDLY_ROOM8.getX(),CoordBank.FRIENDLY_ROOM8.getY());

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
                return (new Department(COMP_SCI_WEPS.getWeaponList(), COMP_SCI_UPGRADES.getRoomUpgradeList(), game));
            case 1:
                return (new Department(LMB_WEPS.getWeaponList(), LMB_UPGRADES.getRoomUpgradeList(), game));
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
        Texture hpBackground = new Texture("background.png");
        Texture hpDisabledBackground = new Texture("disabledBackground.png");

        ProgressBar.ProgressBarStyle hpBarStyle = new ProgressBar.ProgressBarStyle();
        hpBarStyle.background = new TextureRegionDrawable( new TextureRegion(hpBackground));
        hpBarStyle.disabledBackground = new TextureRegionDrawable( new TextureRegion(hpDisabledBackground));

        ProgressBar hpBar = new ProgressBar(0,1000,10,false,hpBarStyle);
        hpBar.setWidth(320);
        hpBar.setHeight(64);
        hpBar.setPosition(25,950);

        stage.addActor(hpBar);

        hpBar.setValue(500);
    } //FIXME

    public void drawIndicators(){
        BitmapFont indicatorFont = new BitmapFont();
        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + game.getPoints(), 25, 965);
        indicatorFont.draw(batch, "Gold: " + game.getGold(), 110, 965);
        indicatorFont.draw(batch, "Food: " + game.getFood(), 195, 965);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), 280, 965);
    }

    public void buttonShowShop(TextButton.TextButtonStyle textButtonStyle) {
      final TextButton showShop = new TextButton("Open Shop", textButtonStyle);
      showShop.setPosition(350, 960);
      showShop.addListener(new InputListener() {
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
              boolShowShop = !boolShowShop;
              return true;
          }
      });
      stage.addActor(showShop);
    }

    public void buttonToMenu(TextButton.TextButtonStyle textButtonStyle){
        TextButton toMenu = new TextButton("To Menu", textButtonStyle);
        toMenu.setPosition(880, 980);
        toMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getGame().setScreen(new menuScreen(game));
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

        if (showShop){
            shopBackground.setAlpha(1);
        } else {
            shopBackground.setAlpha(0);
        }
    }

    public List<TextButton> drawBuyWeaponFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
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

            buyButtonList.add(new TextButton("Buy (" + weaponList.get(j).getCost() + ")", textButtonStyle));
            buyButtonList.get(j).setPosition(160, 740 - (j * 150));
            stage.addActor(buyButtonList.get(j));
            j++;
        }

        setButtonListener(buyButtonList, weaponList);

        return buyButtonList;
    }

    public void setButtonListener(final List<TextButton> buyButtonList, final List<Weapon> weaponList) {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    try {
                        department.buyWeapon(weaponList.get(j));
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

    public void drawSellWeaponInformation(BitmapFont titleFont, BitmapFont bodyFont) {
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
            j++;
        }
    }

    public void drawBuyRoomUpgradeInformation(BitmapFont titleFont, BitmapFont bodyFont) {
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
            j++;
        }
    }
    //FIXME Unable to Sell Room Upgrades

    public void toggleShop(Boolean boolShowShop, Sprite shopBackground, BitmapFont titleFont, BitmapFont bodyFont, List<TextButton> buyButtonList) {
        if (boolShowShop) {
            shopBackground.setAlpha(0.85f);
            titleFont.setColor(1, 1, 1, 1);
            bodyFont.setColor(1, 1, 1, 1);
            int i = 0;
            while (i <= buyButtonList.size() - 1){
                buyButtonList.get(i).setColor(1,1,1,1);
                i++;
            }
        } else {
            shopBackground.setAlpha(0);
            titleFont.setColor(1, 1, 1, 0);
            bodyFont.setColor(1, 1, 1, 0);
            int i = 0;
            while (i <= buyButtonList.size() - 1){
                buyButtonList.get(i).setColor(1,1,1,0);
                i++;
            }
        }
    }

}

