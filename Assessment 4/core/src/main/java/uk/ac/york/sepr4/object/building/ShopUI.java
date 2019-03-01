package uk.ac.york.sepr4.object.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.sepr4.GameScreen;
import uk.ac.york.sepr4.object.entity.Player;
import lombok.Getter;

import javax.naming.NameNotFoundException;


/**
 * Added for assessment 3, manages interactions with departments
 */
public class ShopUI {

    private GameScreen gameScreen;
    private Player player;
    @Getter
    private String name;
    @Getter
    private Stage stage;

    private Label balanceLabel;
    private TextButton[] items;

    private Label shopLabel;
    private Label maxSpeedLabel;
    private Label healthLabel;
    private Label turningSpeedLabel;
    private Label maxHealthLabel;
    private Label bulletDamage;
    private Label accelerationLabel;

    public ShopUI (GameScreen gameScreen, String name) throws NameNotFoundException {
        this.name = name;
        this.gameScreen = gameScreen;
        this.stage = new Stage(new ScreenViewport());
        player = gameScreen.getEntityManager().getOrCreatePlayer();

        Table table = new Table();
        table.setFillParent(true);
        String[] itemNames;
        switch (name){
            case "biology":
                itemNames = new String[]{"Full heal : 100g", "Increase Max Health : 200g"};
                break;
            case "computer science":
                itemNames = new String[]{"Increase shot damage : 100g", "Triple shot: 1000g"};
                break;
            case "physics":
                itemNames = new String[]{"Increase max speed : 100g", "Increase maneuverability: 200g",
                                         "Increase acceleration: 300g"};
                break;
            default:
                throw new NameNotFoundException();
        }
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("default_skin/uiskin.json"));
        balanceLabel = new Label("Gold available: " + player.getBalance(), new Label.LabelStyle(
                                                                                        new BitmapFont(), Color.GOLD));
        shopLabel = new Label("Department of " + name, new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        //
        maxSpeedLabel = new Label("Max Speed: " + (int)player.getMaxSpeed(), new Label.LabelStyle(
                new BitmapFont(), Color.BLACK));
        accelerationLabel = new Label("Acceleration " + (int)player.getAcceleration(), new Label.LabelStyle(
                new BitmapFont(), Color.BLACK));
        turningSpeedLabel = new Label("Turning Speed: " + player.getTurningSpeed(), new Label.LabelStyle(
                new BitmapFont(), Color.BLACK));
        healthLabel = new Label("Health: " + player.getHealth().intValue(), new Label.LabelStyle(
                new BitmapFont(), Color.BLACK));
        maxHealthLabel = new Label("Max Health: " + player.getMaxHealth().intValue(), new Label.LabelStyle(
                new BitmapFont(), Color.BLACK));
        bulletDamage = new Label("Bullet Damage: " + (int)player.getBulletDamage(), new Label.LabelStyle(
                new BitmapFont(), Color.BLACK));

        table.add(shopLabel).colspan(3).center();
        table.row().pad(10,0,0,10);
        table.add(balanceLabel).colspan(3).center();
        table.row().pad(10,0,10,0);
        table.add(maxSpeedLabel);
        table.add(accelerationLabel);
        table.add(turningSpeedLabel);
        table.row().pad(0,0,0,10);
        table.add(healthLabel).fillX().uniformX();
        table.add(maxHealthLabel).fillX().uniformX();
        table.add(bulletDamage).fillX().uniformX();
        table.row().pad(10,0,10,0);



        items = new TextButton[itemNames.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new TextButton(itemNames[i], skin);
            int finalI = i;
            items[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    shopEvent(finalI);
                }
            });
            table.add(items[i]).colspan(3).center();
            table.row().pad(10,0,10,0);
        }
        TextButton exit = new TextButton("Exit shop", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.exitDepartment();
            }
        });
        table.add(exit).colspan(3).center();
    }

    public void dispose() {
        this.stage.dispose();
    }


    /**
     * Handle attempts by the player to purchase an upgrade
     * @param finalI index of the element the player clicked on
     */
    private void shopEvent(int finalI) {
        switch(name) {
            case "biology":
                if (finalI == 0) {
                    if (player.getHealth() != player.getMaxHealth() && player.deduceBalance(100)) {
                        player.setHealth(player.getMaxHealth());
                        player.updateHealthBar();
                    }
                }
                else if (finalI == 1) {
                    if (player.deduceBalance(200)) {
                        player.setMaxHealth(player.getMaxHealth() + 5f);
                        player.setHealth(player.getHealth() + 5f);
                        player.updateHealthBar();
                    }
                }
                break;
            case "physics":
                if (finalI == 0) {
                    if (player.deduceBalance(100)) {
                        player.setMaxSpeed(player.getMaxSpeed() + 10f);
                    }
                }
                else if (finalI == 1) {
                    if (player.deduceBalance(200)) {
                        player.setTurningSpeed(player.getTurningSpeed() + 0.25f);
                    }
                }
                else if (finalI == 2) {
                    if (player.deduceBalance(300)) {
                        player.setAcceleration(player.getAcceleration() + 10f);
                    }
                }
                break;
            case "computer science":
                if (finalI == 0) {
                    if (player.deduceBalance(100)) {
                        player.setBulletDamage(player.getBulletDamage() + 1);
                    }
                }
                else if (finalI == 1) {
                    if (!player.isTripleShot()) {
                        if (player.deduceBalance(1000)) {
                            player.setTripleShot(true);
                            player.setReqCooldown(1.0f);
                            // Update button to indicate upgrade has been gotten
                            items[finalI].setText("Triple shot out of stock");
                        }
                    }
                    else {
                        items[finalI].setText("Triple shot out of stock");
                    }
                }
               break;
        }
        balanceLabel.setText("Gold available: " + player.getBalance());
        maxSpeedLabel.setText("MaxSpeed: " + (int)player.getMaxSpeed());
        accelerationLabel.setText("Acceleration " + (int)player.getAcceleration());
        turningSpeedLabel.setText("TurningSpeed: " + player.getTurningSpeed());
        maxHealthLabel.setText("Health: " + player.getHealth().intValue());
        healthLabel.setText("MaxHealth: " + player.getMaxHealth().intValue());
        bulletDamage.setText("BulletDamage: "+ (int) player.getBulletDamage());
    }


}
