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
        this.hasWon = hasWon;

        Table uiTable = new Table();
        uiTable.setFillParent(true);
        uiTable.setPosition(0,0);
        uiTable.align(Align.center);

        if (hasWon) {
            Label Victory = new Label("You Win!", main.getSkin());
            uiTable.add(Victory);
            i=0;
        } else {
            Label Loss = new Label("You Lose :(", main.getSkin());
            uiTable.add(Loss);
            Label Message = new Label("You will be reset to 0 points and respawn", main.getSkin());
            uiTable.row();
            uiTable.add(Message);
            i=0;
        }

        Label pointsLabel = new Label("Score: " + Integer.toString(main.getPlayer().getPoints()), main.getSkin());
        uiTable.row();
        uiTable.add(pointsLabel);

        uiStage.addActor(uiTable);
            }

    @Override
    public void update(float delta) {
        i++;

        if (i == 2){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }

            if (hasWon) {
                System.exit(0);
            } else {
                pirateGame.setScreen(pirateGame.getSailingScene());
                pirateGame.getPlayer().getPlayerShip().setPosition(500,10);
            }

            dispose();
        }
    }
}
