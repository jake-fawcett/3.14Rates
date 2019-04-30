package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.StyleManager;
import uk.ac.york.sepr4.ahod2.object.ShipFactory;
import uk.ac.york.sepr4.ahod2.object.card.Card;
import uk.ac.york.sepr4.ahod2.object.entity.Player;
import uk.ac.york.sepr4.ahod2.object.entity.Ship;
import uk.ac.york.sepr4.ahod2.util.BattleAI;

import java.util.Random;

//could be replaced by a bool?
enum BattleTurn {
    PLAYER, ENEMY
}

public class BattleScreen extends AHODScreen {

    //final variables
    @Getter
    private static final Integer handSize = 5;
    private GameInstance gameInstance;
    @Getter
    private Ship enemy;
    @Getter
    private Player player;
    //battle conditions
    private Integer gold;
    private Integer difficulty;
    //display variables
    private Image playerShipImage, enemyShipImage;
    private Label playerHealthLabel, enemyHealthLabel, playerManaLabel, enemyManaLabel;
    private TextButton drawButton;
    private Table table;
    //battle variables
    private BattleTurn turn;
    private boolean animating = false, end = false;
    private Integer turnNo = 1;

    /***
     * Used by generic battle encounters (through BattleNode) to initialize battle.
     * @param gameInstance
     */
    public BattleScreen(GameInstance gameInstance) {
        this(gameInstance,
                ShipFactory.generateEnemyShip(gameInstance.getCurrentLevel().getDifficulty()),
                gameInstance.getCurrentLevel().getDifficulty(),
                gameInstance.getCurrentLevel().getBattleGold());
    }

    /***
     * Used when battle initiated through EncounterScreen. Specified enemy and gold reward.
     * @param gameInstance
     * @param enemy specified enemy
     * @param gold gold reward
     */
    public BattleScreen(GameInstance gameInstance, Ship enemy, Integer difficulty, Integer gold) {
        super(new Stage(new ScreenViewport(), new SpriteBatch()), FileManager.battleScreenBG);
        this.gameInstance = gameInstance;
        this.enemy = enemy;
        this.difficulty = difficulty;
        this.gold = gold;

        player = gameInstance.getPlayer();

        //load battle visual elements
        loadBattleElements();

        //set turn to player
        turn = BattleTurn.PLAYER;

        //set message and animations hud to appear on this screen
        setMessageHUD(gameInstance);
        setAnimationsHUD(gameInstance);

        //(re-)set battle variables for enemy and player ship
        enemy.battleStart(gameInstance.getCardManager().getDefaultCards());
        player.getShip().battleStart(gameInstance.getCardManager().getDefaultCards());
    }

    /***
     * Get random position inside specified image.
     * Used to determine position of damage/heal animtion splats.
     * @param image specified image
     * @return random location within image.
     */
    private Vector2 getRandomImagePos(Image image) {
        Random random = new Random();

        return new Vector2(image.getX() + (image.getImageWidth() * random.nextFloat()),
                image.getY() + (image.getImageHeight() * random.nextFloat()));
    }

    @Override
    public void renderInner(float delta) {
        updateBattleElements();
        if (end) {
            return;
        }
        if (animating) {
            //do animations

            //switch turns
            if (turn == BattleTurn.PLAYER) {
                //Gdx.app.debug("BattleScreen", "Applying Enemy Damage!");
                player.getShip().applyDelayedHeal(gameInstance, getRandomImagePos(playerShipImage));
                if (enemy.applyDelayedDamage(gameInstance, getRandomImagePos(enemyShipImage))) {
                    //sink Animation
                    hasDied(enemy);
                } else {
                    //damage Animation
                }

                turn = BattleTurn.ENEMY;
            } else if (turn == BattleTurn.ENEMY) {
                //Gdx.app.debug("BattleScreen", "Applying Player Damage!");
                enemy.applyDelayedHeal(gameInstance, getRandomImagePos(enemyShipImage));
                if (player.getShip().applyDelayedDamage(gameInstance, getRandomImagePos(playerShipImage))) {
                    hasDied(player.getShip());
                }

                turnNo++;
                //set to turn number (max at 10)
                // EDITED FOR ASSESSMENT 4: Added additionalMana
                player.getShip().setMaxMana(turnNo + player.getShip().additionalMana);
                player.getShip().setMana(player.getShip().getMaxMana());
                enemy.setMaxMana(turnNo);
                enemy.setMana(enemy.getMaxMana());

                turn = BattleTurn.PLAYER;
                Gdx.app.debug("BattleScreen", "Player Move!");
            }
            animating = false;
        } else {
            if (turn == BattleTurn.ENEMY) {
                //do enemy turn
                BattleAI.chooseMove(this);
            }
        }
    }

    /***
     * Check whether is currently the player's turn.
     * @return true if player's, false otherwise
     */
    private boolean playerTurnCheck() {
        if (turn == BattleTurn.PLAYER && !animating) {
            return true;
        } else {
            gameInstance.getMessageHUD().addStatusMessage("Not your turn!", 2f);
            return false;
        }
    }


    public void useCard(Ship source, Ship target, Card card) {
        if (source.getMana() < card.getManaCost()) {
            //not enough mana to cast
            gameInstance.getMessageHUD().addStatusMessage("Not enough mana!", 3f);
            return;
        }

        source.useCard(card);
        source.deductMana(card.getManaCost());
        for (int i = 0; i <= card.getMoveTime(); i++) {
            source.addDamage(card.getDamageSelf(), i);
            target.addDamage(card.getDamage(), i);
            source.addHeal(card.getHeal(), i);
        }
        //could add delayed healing
        Gdx.app.debug("BattleScreen", "Using card: " + card.getName());

    }

    /***
     * End current turn.
     * (Set animating phase to true)
     */
    public void endTurn() {
        //skip turn
        Gdx.app.debug("BattleScreen", "Ending turn!");
        animating = true;
    }

    /***
     * Get current cost to draw a card.
     * Increases as no. of turns increases (max 10, min 1).
     * @return cost of draw
     */
    private Integer getCardDrawCost() {
        if (turnNo > 10) {
            return 5;
        } else if (turnNo == 1) {
            return 1;
        } else {
            return (int) Math.floor((double) turnNo / 2);
        }
    }

    /***
     * Attempt to draw a card for the specified ship.
     * @param source specified ship
     * @return true if card successfully drawn, false otherwise (not enough mana, etc)
     */
    public boolean drawCard(Ship source) {
        //check if ship has enough mana
        if (source.getMana() >= getCardDrawCost()) {
            //check if hand size is less than max hand size
            if (source.getHand().size() < handSize) {
                //check if can draw card (based on current playdeck)
                if (gameInstance.getCardManager().drawRandomCard(source)) {
                    Gdx.app.debug("BattleScreen", "Drawing Card!");
                    source.deductMana(getCardDrawCost());
                    return true;
                } else {
                    if (turn == BattleTurn.PLAYER) {
                        gameInstance.getMessageHUD().addStatusMessage("Can't draw card!", 3f);
                    }
                }
            } else {
                if (turn == BattleTurn.PLAYER) {
                    gameInstance.getMessageHUD().addStatusMessage("Max hand size!", 3f);
                }
            }
        } else {
            if (turn == BattleTurn.PLAYER) {
                gameInstance.getMessageHUD().addStatusMessage("Not enough mana!", 3f);
            }
        }
        return false;
    }

    /***
     * Called when a specified ship has died. Ends the battle.
     * If enemy died, add gold to player and advance (new level if enemy is boss)
     * If player died, switch to EndScreen (game lost).
     * @param ship specified ship
     */
    private void hasDied(Ship ship) {
        end = true;
        if (ship == enemy) {
            //player wins (reset mana and cards)
            player.addGold(gold);
            gameInstance.getMessageHUD().addGoldMessage(gold);
            if (enemy.isBoss()) {
                //screen is switched in this method
                // EDITED FOR ASSESSMENT 4: Crew members for bosses
                player.incBoss();
                if (player.getBossCounter() >= 1){
                    player.addGold(100);
                }
                if (player.getBossCounter() == 1){
                    gameInstance.fadeSwitchScreen(new PerkScreen(gameInstance, "Your player now gains an extra 100 gold everytime you defeat a boss"));
                }
                if (player.getBossCounter() >= 2){
                    player.getShip().heal(10);
                }
                if (player.getBossCounter() == 2){
                    gameInstance.fadeSwitchScreen(new PerkScreen(gameInstance, "Your player now heals for 10 every time you defeat a boss"));
                }
                if (player.getBossCounter() >= 3){
                    player.getShip().setMaxHealth(player.getShip().getMaxHealth() + 5);
                }
                if(player.getBossCounter() ==3){
                    gameInstance.fadeSwitchScreen(new PerkScreen(gameInstance, "Your player gains 5 hp every time time you defeat a boss"));
                }
                if (player.getBossCounter() >= 4){
                    player.getShip().incMana();
                }
                if(player.getBossCounter() == 4){
                    gameInstance.fadeSwitchScreen(new PerkScreen(gameInstance, "You now have 1 additional mana to start with everytime you defeat a boss"));
                }
            } else {
                gameInstance.fadeSwitchScreen(new CardSelectionScreen(gameInstance, gameInstance.getCardManager().getRandomSelection(difficulty)), true);
            }
        } else {
            //player lost
            gameInstance.fadeSwitchScreen(new EndScreen(gameInstance, false), true);
        }
    }

    /***
     * Setup tables for battle elements.
     * Including ship images, health and mana labels and draw/end turn buttons.
     */
    private void loadBattleElements() {
        //create ship images and set scaling (so should scale if screen size changes)
        playerShipImage = new Image(player.getShip().getImage());
        playerShipImage.setScaling(Scaling.fillY);

        //flip enemy image
        TextureRegion txR = new TextureRegion(enemy.getImage());
        txR.flip(true, false);
        enemyShipImage = new Image(txR);
        enemyShipImage.setScaling(Scaling.fillY);

        //health labels
        Label.LabelStyle style = StyleManager.generateLabelStyle(30, Color.RED);
        playerHealthLabel = new Label(player.getShip().getHealth() + "/" + player.getShip().getMaxHealth(), style);
        enemyHealthLabel = new Label(enemy.getHealth() + "/" + enemy.getMaxHealth(), style);

        //mana labels
        Label.LabelStyle style2 = StyleManager.generateLabelStyle(30, Color.PURPLE);
        playerManaLabel = new Label(player.getShip().getMana() + "/" + player.getShip().getMaxMana(), style2);
        enemyManaLabel = new Label(enemy.getMana() + "/" + enemy.getMaxMana(), style2);

        //buttons
        drawButton = new TextButton("Draw Card", StyleManager.generateTBStyle(30, Color.CORAL, Color.GRAY));
        drawButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                Gdx.app.debug("BattleScreen", "Pressed draw card button!");

                if (playerTurnCheck()) {
                    drawCard(player.getShip());
                }
            }
        });
        TextButton endTurnButton = new TextButton("End Turn", StyleManager.generateTBStyle(30, Color.ORANGE, Color.GRAY));
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                if (playerTurnCheck()) {
                    endTurn();
                }
            }
        });

        table = new Table();
        table.setFillParent(true);
        table.top();
        // EDITED FOR ASSESSMENT 4: Commented out debug table so there are no red lines in the final product
        // table.debug();

        //top 2/3 (ship + stat display)
        table.add(playerShipImage).expandX()
                .height(Value.percentHeight(0.40f, table))
                .width(Value.percentWidth(0.5f, table))
                .padTop(Value.percentHeight(0.02f, table));
        table.add(enemyShipImage).expandX()
                .height(Value.percentHeight(0.40f, table))
                .width(Value.percentWidth(0.5f, table))
                .padTop(Value.percentHeight(0.02f, table));
        table.row();

        table.add(playerHealthLabel).expandX()
                .height(Value.percentHeight(0.05f, table));
        table.add(enemyHealthLabel).expandX()
                .height(Value.percentHeight(0.05f, table));
        table.row();

        table.add(playerManaLabel).expandX()
                .height(Value.percentHeight(0.05f, table))
                .padBottom(Value.percentHeight(0.02f, table));
        table.add(enemyManaLabel).expandX()
                .height(Value.percentHeight(0.05f, table))
                .padBottom(Value.percentHeight(0.02f, table));
        table.row();

        //start of bottom 1/3
        table.add(drawButton).expandX()
                .height(Value.percentHeight(0.04f, table))
                .padBottom(Value.percentHeight(0.02f, table));
        table.add(endTurnButton).expandX()
                .height(Value.percentHeight(0.04f, table))
                .padBottom(Value.percentHeight(0.02f, table));

        getStage().addActor(table);
    }

    /***
     * Create ImageButton for specified card at specified hand position.
     * @param card specified card
     * @param index specified hand position
     * @return instance of ImageButton for this card
     */
    private ImageButton getIBForCard(Card card, Integer index) {
        //calculate heights
        Integer currentHandSize = player.getShip().getHand().size();
        Texture tex = card.getTexture();
        float height = table.getHeight() / 2; //1/3 of screen
        float width = tex.getWidth() * (height / (tex.getHeight()));

        //create button
        ImageButton iB = new ImageButton(new TextureRegionDrawable(tex));
        iB.setSize(width, height);
        iB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                if (playerTurnCheck()) {
                    useCard(player.getShip(), enemy, card);
                }
            }
        });

        //set button position
        float w = Gdx.graphics.getWidth();
        float pos = (w - (currentHandSize * width)) / (currentHandSize + 1);
        iB.setPosition((index + 1) * pos + (index * width), -10 - height / 8);

        return iB;
        //NB: Couldn't use a table for this as had problems with rapid setting of actors within it.
    }

    /***
     * Update battle elements.
     */
    private void updateBattleElements() {
        //update draw button
        drawButton.setText("Draw Card (" + getCardDrawCost() + ")");

        //update health and mana labels
        playerHealthLabel.setText(player.getShip().getHealth() + "/" + player.getShip().getMaxHealth());
        enemyHealthLabel.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
        playerManaLabel.setText(player.getShip().getMana() + "/" + player.getShip().getMaxMana());
        enemyManaLabel.setText(enemy.getMana() + "/" + enemy.getMaxMana());

        //update card imageubttons
        Array<Actor> toRemove = new Array<>();
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof ImageButton) {
                toRemove.add(actor);
            }
        }
        getStage().getActors().removeAll(toRemove, false);

        Integer index = 0;
        for (Card card : player.getShip().getHand()) {
            getStage().addActor(getIBForCard(card, index));
            index++;
        }

    }

}
