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

public class MinigameScreen extends BaseScreen {
    private Player player;
    private Department department;
    
    private Label goldLabel;
    private Label resultLabel;

    public MinigameScreen(final PirateGame main, Department department) {
        super(main);
        player = main.getPlayer();
        this.department = department;

        Table uiTable = new Table();

        Label goldTextLabel = new Label("Gold:", main.getSkin());
        goldLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin());
        goldLabel.setAlignment(Align.left);

        uiTable.add(goldTextLabel);
        uiTable.add(goldLabel).width(goldTextLabel.getWidth());

        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        final TextButton Heads = new TextButton("Bet 5 on Heads", main.getSkin());
        Heads.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                
            }
        });

        final TextButton Tails = new TextButton("Bet 5 on Tails", main.getSkin());
        Tails.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        Table bettingTable = new Table();
        bettingTable.setFillParent(true);
        Label title = new Label("Heads or Tails!", main.getSkin());
        title.setAlignment(Align.center);

        bettingTable.add(title);
        bettingTable.row();
        bettingTable.add(Heads);
        bettingTable.add(Tails);

        mainStage.addActor(bettingTable);

        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void update(float delta) {
        goldLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
    }
}
