//Added For Assessment 3
package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;

import java.util.concurrent.TimeUnit;

public class VictoryScreen extends BaseScreen {
    private boolean hasWon;
    private int i;

    public VictoryScreen(PirateGame main, Boolean hasWon) {
        super(main);
        //Stores if the User has Won or Lost
        hasWon = this.hasWon;

        //Table used to store labels to align and position them relatively
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        uiTable.setPosition(0,0);
        uiTable.align(Align.center);

        //Creates Win and Lose messages based on if the Player has Won or Lost
        if (hasWon) {
            Label Victory = new Label("You Win!", main.getSkin());
            uiTable.add(Victory);
            i=0;
        } else {
            Label Loss = new Label("You Lose :(", main.getSkin());
            uiTable.add(Loss);
            Label Message = new Label("Your Gold will be Halved and Points Reset", main.getSkin());
            uiTable.row();
            uiTable.add(Message);
            i=0;
        }

        //Creates Label displaying the players score on the Win/Loss Screen
        Label pointsLabel = new Label("Score: " + Integer.toString(main.getPlayer().getPoints()), main.getSkin());
        uiTable.row();
        uiTable.add(pointsLabel);

        uiStage.addActor(uiTable);
            }

    @Override
    public void update(float delta) {
        i++;

        //i and If statement used to allow the screen to Update to the Victory Screen
        //before sleeping so the Screen is displayed for a set amount of time
        if (i == 2){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }

            //If the player has won the game closes, If they have lost they are reset to the Sailing Screen
            if (hasWon) {
                System.exit(0);
            } else {
                pirateGame.setScreen(pirateGame.getSailingScene());
            }
        }
    }
}
//End Added