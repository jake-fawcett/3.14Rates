package uk.ac.york.sepr4.ahod2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.object.card.Card;
import uk.ac.york.sepr4.ahod2.object.entity.Player;

import java.util.List;

/***
 * Used to present player with cards to choose from after battle victory.
 */
public class CardSelectionScreen extends AHODScreen {

    private List<Card> selection;
    private GameInstance gameInstance;
    //has card been chosen
    private boolean selected = false;

    public CardSelectionScreen(GameInstance gameInstance, List<Card> selection) {
        super(new Stage(new ScreenViewport()), FileManager.menuScreenBG);

        this.gameInstance = gameInstance;
        this.selection = selection;

        setStatsHUD(gameInstance);

        createSelectionTable();
    }

    /***
     * Create selection table and populate with selection of cards.
     */
    private void createSelectionTable() {
        Table selectionTable = new Table();
        selectionTable.setFillParent(true);
        selectionTable.top();
        // EDITED FOR ASSESSMENT 4: Commented out debug table so that there are no red lines on the final product
        //selectionTable.debug();

        //add selection cards to table
        for (Card card : selection) {
            Gdx.app.debug("CardSelScreen", card.getName());
            ImageButton imageButton = new ImageButton(new TextureRegionDrawable(card.getTexture()));
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent ev, float x, float y) {
                    if (!selected) {
                        selectCard(card);
                    }
                }
            });
            selectionTable.add(imageButton).expandX().expandY()
                    .align(Align.center);
        }
        getStage().addActor(selectionTable);

    }

    /***
     * Specified card has been selected.
     * Add card to player's deck and switch to SailScreen.
     * @param card specified card
     */
    private void selectCard(Card card) {
        selected = true;
        gameInstance.getPlayer().getShip().addCard(card);
        gameInstance.fadeSwitchScreen(gameInstance.getSailScreen(), true);
    }


    @Override
    public void renderInner(float delta) {
    }
}
