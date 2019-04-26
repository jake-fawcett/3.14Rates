/*ADDED FOR ASSESSMENT 4
Added Monster Screen class extending AHODScreen, Creates the screen seen by the user when they encounter the sea monster
 */

package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.StyleManager;
import uk.ac.york.sepr4.ahod2.object.entity.Player;

public class MonsterScreen extends AHODScreen {

    private GameInstance gameInstance;

    public MonsterScreen(GameInstance gameInstance) {
        super(new Stage(new ScreenViewport(), new SpriteBatch()), new Texture(Gdx.files.internal("images/screen/encounter/default.png")));
        this.gameInstance = gameInstance;

        createEncounter();
    }

    private void createEncounter() {
        Table table1 = new Table();
        table1.setFillParent(true);
        table1.top();

        //create label for base encounter
        Label encounterText = new Label("You have Encountered a Sea Monster, Take 7 Damage", StyleManager.generateLabelStyle(60, Color.BLACK));
        table1.add(encounterText).expandX().padTop(Gdx.graphics.getHeight() / 4);

        //create EncounterButton table
        Table table2 = new Table();
        table2.setFillParent(true);
        table2.top();

        //add button to table
        TextButton tB = new TextButton("Escape!",
                StyleManager.generateTBStyle(30, Color.BLACK, Color.GRAY));

        tB.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent ev, float x, float y) {
                    Player player = gameInstance.getPlayer();

                    if (player.getShip().getHealth() > 7) {
                        player.getShip().setHealth(player.getShip().getHealth() - 7);
                        gameInstance.fadeSwitchScreen(gameInstance.getSailScreen());
                    } else {
                        //health less than 0, player loses
                        gameInstance.fadeSwitchScreen(new EndScreen(gameInstance, false));
                    }
                }
            });

        table2.add(tB).expandX().padTop(Gdx.graphics.getHeight() / 2);


        //add tables to stage
        getStage().addActor(table1);
        getStage().addActor(table2);
    }

    @Override
    public void renderInner(float delta) {
    }
}
