package uk.ac.york.sepr4.ahod2.object.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.object.entity.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/***
 * Class used to load instances of cards from file and check their textures are present.
 * Contains functions to generate random cards with specified power.
 */
public class CardManager {

    private static final Integer maxSelectionSize = 4;
    private List<Card> cards = new ArrayList<>();
    @Getter
    private List<Card> defaultCards = new ArrayList<>();

    public CardManager() {
        Json json = new Json();
        loadCards(json.fromJson(Array.class, Card.class, Gdx.files.internal("data/cards.json")));

        //set default cards
        for (Card card : cards) {
            if (card.is_default()) {
                //add default card multiple times depending on json variable
                for (int i = 0; i < card.getDefaultNo(); i++) {
                    defaultCards.add(card);
                }
            }
        }

        Gdx.app.log("CardManager", "Loaded " + cards.size() + " cards!");
    }

    private void loadCards(Array<Card> cards) {
        for (Card card : cards) {
            FileHandle fileHandle = Gdx.files.internal("images/card/" + card.getTextureStr() + ".png");
            //check texture exists
            if (!fileHandle.exists()) {
                Gdx.app.error("CardManager", "Texture not found for card: " + card.getId());
                continue;
            }
            card.setTexture(new Texture(fileHandle));
            this.cards.add(card);
        }
    }

    /***
     * Draw random card from specified ship's deck and add to it's hand.
     * @param ship specified ship
     * @return true if card drawn, false if cannot draw
     */
    public boolean drawRandomCard(Ship ship) {
        //if deck size = 0
        if (ship.getPlayDeck().size() == 0) {
            //if hand size = 0
            if (ship.getHand().size() == 0) {
                ship.shuffleReset(defaultCards);
            } else {
                //deck was empty but cards still in hand (can't shuffle)
                return false;
            }
        }

        Card card = ship.getPlayDeck().poll();
        Gdx.app.debug("Card Manager", "Randomly Drawing:" + card.getName());
        ship.addCardToHand(card);
        return true;
    }

    /***
     * Returns a list of specified ship's deck including default cards.
     * @param ship specified ship
     * @return list of cards from deck inc. default cards
     */
    public List<Card> getFullDeck(Ship ship) {
        List<Card> deck = new ArrayList<>(ship.getDeck());
        deck.addAll(defaultCards);
        return deck;
    }

    /***
     * Generate random card from all available with power no greater than specified.
     * @param power maximum card power to generate
     * @return optional of generated card
     */
    public Optional<Card> randomCard(Integer power) {
        Random random = new Random();
        Integer attempts = 50;
        while (attempts > 0) {
            Card card = cards.get(random.nextInt(cards.size() - 1));
            if (!card.is_default() && card.getPower() <= power) {
                Gdx.app.debug("Card Manager", "Randomly Selected:" + card.getName());
                return Optional.of(card);
            }
            attempts--;
        }
        return Optional.empty();
    }

    /***
     * Generate random selection of cards with power no greater than specified
     * @param power maximum card power to generate
     * @return list of cards (size 1-4)
     */
    public List<Card> getRandomSelection(Integer power) {
        Random random = new Random();
        List<Card> selectionCards = new ArrayList<>();
        //min of 1, max 4
        Integer selectionSize = random.nextInt(maxSelectionSize) + 1;
        while (selectionCards.size() < selectionSize) {
            Optional<Card> optionalCard = randomCard(power);
            if (optionalCard.isPresent()) {
                selectionCards.add(optionalCard.get());
            }
        }
        return selectionCards;
    }

}

