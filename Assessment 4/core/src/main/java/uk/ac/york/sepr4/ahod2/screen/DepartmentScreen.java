package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.StyleManager;
import uk.ac.york.sepr4.ahod2.object.building.Department;
import uk.ac.york.sepr4.ahod2.object.entity.Player;

import java.util.Random;

public class DepartmentScreen extends AHODScreen {

    private GameInstance gameInstance;
    @Getter
    private Department department;
    private MinigameScreen minigameScreen;
    private Table topTable = new Table();
    private TextButton repairButton, upgradeButton;

    private Integer addedHealth = 0;
    private boolean purchasedUpgrade = false;

    public DepartmentScreen(GameInstance gameInstance, Department department) {
        super(new Stage(new ScreenViewport()), FileManager.menuScreenBG);

        this.gameInstance = gameInstance;
        this.department = department;

        this.minigameScreen = new MinigameScreen(gameInstance, this);

        generateRandomUpgrade();

        setupTopTable();
        // EDITED FOR ASSESSMENT 4: Added Stats HUD to department screen, gold balance/health clearer
        setStatsHUD(gameInstance);
        setMessageHUD(gameInstance);
    }

    /***
     * Generate health upgrade based on department power.
     */
    private void generateRandomUpgrade() {
        Random random = new Random();
        addedHealth = 5 * (random.nextInt(department.getMinigamePower()) + 1);
    }

    /***
     * Get cost of upgrade based on department power.
     * @return upgrade cost (gold)
     */
    private Integer getUpgradeCost() {
        return department.getMinigamePower() * 50;
    }

    /***
     * Create department elements.
     */
    private void setupTopTable() {
        topTable.setFillParent(true);
        topTable.top();

        //welcome label
        Label label = new Label("Welcome to " + department.getName(), StyleManager.generateLabelStyle(50, Color.RED));
        topTable.add(label)
                .expandX()
                .padTop(Value.percentHeight(0.03f, topTable))
                .height(Value.percentHeight(0.15f, topTable));
        topTable.row();
        //repair button
        repairButton = new TextButton("Click to repair!\nCost: " + getRepairCost(),
                StyleManager.generateTBStyle(40, Color.GREEN, Color.GRAY));
        repairButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                repair();
            }
        });
        topTable.add(repairButton)
                .expandX()
                .padTop(Value.percentHeight(0.02f, topTable))
                .height(Value.percentHeight(0.1f, topTable));
        topTable.row();
        //upgrade button
        upgradeButton = new TextButton("Click to upgrade!\n (+" + addedHealth + " health)\nCost: " + getUpgradeCost(),
                StyleManager.generateTBStyle(30, Color.PURPLE, Color.GRAY));
        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                purchaseUpgrade();
            }
        });
        topTable.add(upgradeButton)
                .expandX()
                .padTop(Value.percentHeight(0.02f, topTable))
                .height(Value.percentHeight(0.1f, topTable));
        topTable.row();
        //minigame (gamble) button
        TextButton gambleButton = new TextButton("Visit Tavern (Minigame)",
                StyleManager.generateTBStyle(30, Color.GOLD, Color.GRAY));
        gambleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                switchMinigame();
            }
        });
        topTable.add(gambleButton)
                .expandX()
                .padTop(Value.percentHeight(0.02f, topTable))
                .height(Value.percentHeight(0.1f, topTable));
        topTable.row();
        //exit button
        TextButton exitButton = new TextButton("Exit Department",
                StyleManager.generateTBStyle(30, Color.RED, Color.GRAY));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                exitScreen();
            }
        });
        topTable.add(exitButton)
                .expandX()
                .padTop(Value.percentHeight(0.02f, topTable))
                .height(Value.percentHeight(0.1f, topTable));

        //add table to screen
        getStage().addActor(topTable);
    }

    /***
     * Repair button clicked.
     * Check if player has enough gold.
     * If yes, repair ship and debit gold.
     */
    private void repair() {
        Player player = gameInstance.getPlayer();
        if (getRepairCost() <= player.getGold()) {
            player.takeGold(getRepairCost());
            player.getShip().setHealth(player.getShip().getMaxHealth());
        } else {
            gameInstance.getMessageHUD().addStatusMessage("Not enough gold!", 3f);
        }
    }

    /***
     * Upgrade button clicked.
     * Check if player has enough gold.
     * If yes, apply upgrade (inc health and maxhealth) and debit gold.
     */
    private void purchaseUpgrade() {
        Player player = gameInstance.getPlayer();
        if (!purchasedUpgrade) {
            if (player.getGold() >= getUpgradeCost()) {
                purchasedUpgrade = true;
                player.takeGold(getUpgradeCost());
                player.getShip().setMaxHealth(player.getShip().getMaxHealth() + addedHealth);
            } else {
                gameInstance.getMessageHUD().addStatusMessage("Not enough gold!", 3f);
            }
        } else {
            gameInstance.getMessageHUD().addStatusMessage("Already purchased!", 3f);
        }
    }

    /***
     * Used when minigame has been played (create new instance)
     */
    public void resetMinigame() {
        this.minigameScreen = new MinigameScreen(gameInstance, this);
    }

    /***
     * Minigame button clicked.
     * Switch to MinigameScreen.
     */
    private void switchMinigame() {
        gameInstance.fadeSwitchScreen(minigameScreen);
    }

    /***
     * Exit button clicked.
     * Switch back to SailScreen.
     */
    private void exitScreen() {
        gameInstance.fadeSwitchScreen(gameInstance.getSailScreen());
    }

    /***
     * Get cost to repair ship (based on difference form full health and dept repair scale factor).
     * @return cost of repair (gold)
     */
    private Integer getRepairCost() {
        Player player = gameInstance.getPlayer();
        Integer toHeal = (player.getShip().getMaxHealth() - player.getShip().getHealth());
        return toHeal * department.getRepairCost();
    }

    /***
     * Update repair cost label.
     */
    private void updateTables() {
        repairButton.setText("Click to repair!\nCost: " + getRepairCost());
    }

    @Override
    public void renderInner(float delta) {
        updateTables();
    }
}
