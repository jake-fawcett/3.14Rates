package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Setter;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.io.StyleManager;
import uk.ac.york.sepr4.ahod2.object.card.Card;
import uk.ac.york.sepr4.ahod2.object.entity.Player;

public class ShipViewScreen extends AHODScreen {

    private final Integer cardsPerRow = 8;
    private GameInstance gameInstance;
    @Setter
    private AHODScreen previousScreen;
    //display variables
    private Table table = new Table(), cardTable = new Table(), crewTable = new Table(), member1 = new Table();
    private Label levelValueLabel;
    private Player player;

    public ShipViewScreen(GameInstance gameInstance) {
        super(new Stage(new ScreenViewport()), FileManager.menuScreenBG);
        this.gameInstance = gameInstance;

        createTable();
        //addMember1();
    }


    private void createTable() {
        //create top table (level and exit button)
        table.top();
        table.setFillParent(true);
        //create bottom table (deck view)
        cardTable.top();
        cardTable.setFillParent(true);
        cardTable.padTop(Value.percentHeight(0.05f, cardTable));

        crewTable.left();
        crewTable.padBottom(0.001f);
        crewTable.padLeft(15);
        crewTable.setFillParent(true);
        crewTable.add(new Label("Crew Members: ", StyleManager.generateLabelStyle(30, Color.GOLD )));

        //level label
        levelValueLabel = new Label("Level: ", StyleManager.generateLabelStyle(30, Color.GREEN));

        //exit button
        TextButton exitButton = new TextButton("Exit", StyleManager.generateTBStyle(30, Color.RED, Color.GRAY));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                gameInstance.fadeSwitchScreen(previousScreen);
            }
        });

        //add elements to table
        table.add(levelValueLabel)
                .expandX()
                .height(Value.percentHeight(0.05f, table));
        table.add(exitButton)
                .expandX()
                .height(Value.percentHeight(0.05f, table));

        //add tables to stage
        getStage().addActor(table);
        getStage().addActor(cardTable);
        getStage().addActor(crewTable);
    }

    /***
     * Update on-screen elements (level label and deck view).
     */
    private void update() {
        //update level label
        levelValueLabel.setText("Level: " + gameInstance.getPlayer().getLevel().getId());

        //update deck view
        cardTable.clear();
        Integer cardCount = 0;
        //draw each card in full deck
        for (Card card : gameInstance.getCardManager().getFullDeck(gameInstance.getPlayer().getShip())) {
            cardCount++;
            Image image = new Image(card.getTexture());
            image.setScaling(Scaling.fit);
            cardTable.add(image)
                    .expandX()
                    .width(Value.percentWidth(1f / cardsPerRow, cardTable));
            if (cardCount == cardsPerRow) {
                cardTable.row();
                cardCount = 0;
            }
        }
    }

  //  private void addMember1(){
  //      if (player.getBossCounter() >= 1){
  //          member1.left();
  //          member1.setFillParent(true);
  //          member1.padBottom(0.001f);
  //          member1.padLeft(10);
  //          member1.add(new Label("Extra 100 Gold every time you defeat the boss", StyleManager.generateLabelStyle(30, Color.GOLD )));
  //          getStage().addActor(member1);
  //      }
  //  }

    @Override
    public void renderInner(float delta) {
        update();
    }
}
