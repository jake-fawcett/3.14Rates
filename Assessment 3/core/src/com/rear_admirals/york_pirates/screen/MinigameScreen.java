//Added For Assessment 3

package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.Department;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.base.BaseScreen;

import java.util.Random;

public class MinigameScreen extends BaseScreen {
    private Player player;
    private Department department;

    private Label goldLabel;

    public MinigameScreen(final PirateGame main, final Department department) {
        super(main);
        //Contains the Games instance of the Player to change gold values
        player = main.getPlayer();
        //Contains the Department the Player was at so it can be returned to
        this.department = department;

        //Creates a Table which all UI is added to so it can be relatively positioned
        Table uiTable = new Table();

        //Creates the Label displaying the Users gold and positions it
        Label goldTextLabel = new Label("Gold:", main.getSkin());
        goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin());
        goldLabel.setAlignment(Align.left);

        uiTable.add(goldTextLabel);
        uiTable.add(goldLabel).width(goldTextLabel.getWidth());

        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        //Creates a Table which all betting buttons and messages are added to so they can be relatively positioned
        Table bettingTable = new Table();
        bettingTable.setFillParent(true);

        Label title = new Label("Heads or Tails!", main.getSkin());
        final Label status = new Label("", main.getSkin());
        Label empty = new Label(" ", main.getSkin());

        //Creates Heads Button which calls betHeads on click
        final TextButton Heads = new TextButton("Bet 5 on Heads", main.getSkin());
        Heads.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                betHeads(status);
            }
        });

        //Creates Tails Button which calls betTails on click
        final TextButton Tails = new TextButton("Bet 5 on Tails", main.getSkin());
        Tails.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                betTails(status);
            }
        });

        //Creates Exit button which returns the player to the screen for the department they were previously at
        final TextButton Exit = new TextButton("Exit", main.getSkin());
        Exit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pirateGame.setScreen(new DepartmentScreen(pirateGame, department));
                dispose();
            }
        });

        //Adds all Betting UI elements created above to the Table
        bettingTable.add(title);
        bettingTable.row();
        bettingTable.add(status);
        bettingTable.row();
        bettingTable.add(Heads);
        bettingTable.add(Tails);
        bettingTable.row();
        bettingTable.add(empty);
        bettingTable.row();
        bettingTable.add(Exit);

        mainStage.addActor(bettingTable);

        Gdx.input.setInputProcessor(mainStage);
    }

    /**
     * Pick a random from heads or tails
     * @return "h" or "t"
     */
    private String flipCoin() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            return "h";
        } else {
            return "t";
        }
    }

    /**
     * Picks Heads or Tails and Displays Win/Loss message and changes player gold for the player Choosing Heads
     * @param status
     */
    void betHeads(Label status) {
        //Checks the player has enough gold to bet
        if (player.getGold() >= 5) {
            if (flipCoin() == "h") {
                status.setText("The Coin was Tails, You Lose!");
                player.setGold(player.getGold() - 5);
            } else {
                status.setText("The Coin was Heads, You Win!");
                player.setGold(player.getGold() + 5);
            }
        } else {
            status.setText("Not Enough Gold!");
        }
    }

    /**
     * Picks Heads or Tails and Displays Win/Loss message and changes player gold for the player Choosing Tails
     * @param status
     */
    void betTails(Label status) {
        //Checks the player has enough gold to bet
        if (player.getGold() >= 5) {
            if (flipCoin() == "h") {
                status.setText("The Coin was Heads, You Lose!");
                player.setGold(player.getGold() - 5);
            } else {
                status.setText("The Coin was Tails, You Win!");
                player.setGold(player.getGold() + 5);
            }
        } else {
            status.setText("Not Enough Gold!");
        }
    }


    @Override
    public void update(float delta) {
        //Updates the Label displaying the players gold balance during betting
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
    }
}

//End Added