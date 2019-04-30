package uk.ac.york.sepr4.ahod2.object.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.object.card.Card;

import java.util.*;

/***
 * Class used to represent instance of ship.
 * Instances of class are generated to create enemies but class also stored by player class as their own ship.
 */
@Data
public class Ship {

    private String name;
    private boolean boss;
    private Integer health, maxHealth = 4, mana, maxMana = 1;
    // ADDED FOR ASSESSMENT 4: additionalMana
    public Integer additionalMana = 0;
    private Texture image;
    //current hand and full deck (non battle)
    private List<Card> hand = new ArrayList<>(), deck = new ArrayList<>();
    //shuffled deck - used during battle
    private Deque<Card> playDeck = new ArrayDeque<>();

    @Getter
    private List<Integer> delayedDamage = new ArrayList<>(), delayedHeal = new ArrayList<>();

    public Ship() {
        this.health = maxHealth;

        //JUnit Test Compatibility
        try {
            //Check if LibGdx assets can be loaded
            Class.forName("uk.ac.york.sepr4.ahod2.io.FileManager");
            image = FileManager.defaultShipTexture;
            image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } catch (Exception ex) {
            //either not found or initializer exception (no gdx - for tests)
        } catch (Error error) {
        }
    }

    /***
     * Returns true if health drops to 0 or below
     * @param damage
     * @return
     */
    private boolean damage(Integer damage) {
        if (damage >= health) {
            health = 0;
            return true;
        } else {
            health -= damage;
            return false;
        }

    }

    /***
     * Increase health by specified value (cap at max health).
     * @param val specified value
     */
    // EDITED FOR ASSESSMENT 4: Made public
    public void heal(Integer val) {
        if (health + val >= maxHealth) {
            health = maxHealth;
        } else {
            health += val;
        }
    }

    /***
     * Reset ship's battle variables and shuffles deck
     * @param defaultCards
     */
    public void battleStart(List<Card> defaultCards) {
        resetBattleParams();
        shuffleReset(defaultCards);
    }

    //shuffle default cards and deck and add to playing deck.
    public void shuffleReset(List<Card> defaultCards) {
        List<Card> list = new ArrayList<>(defaultCards);
        list.addAll(deck);

        Collections.shuffle(list);
        list.forEach(card -> playDeck.add(card));
    }

    public void setMaxMana(Integer lMana) {
        if (lMana > 10) {
            maxMana = 10;
        } else {
            maxMana = lMana;
        }
    }

    /***
     * Reset ship's battle variables.
     */
    public void resetBattleParams() {
        hand = new ArrayList<>();
        playDeck = new ArrayDeque<>();
        delayedDamage = new ArrayList<>();
        // EDITED FOR ASSESSMENT 4: Implemented additional mana from crew
        maxMana = 1 + additionalMana;
        mana = 1 + additionalMana;
    }

    public boolean applyDelayedDamage(GameInstance gameInstance, Vector2 poi) {
        if (delayedDamage.size() > 0) {
            if (delayedDamage.get(0) > 0) {
                //JUnit Test Compat
                if (gameInstance != null && poi != null) {
                    gameInstance.getAnimationHUD().addDamageAnimation(poi, delayedDamage.get(0), 3f);
                }
            }
            if (damage(delayedDamage.get(0))) {
                return true;
            }
            delayedDamage.remove(0);
        }
        return false;
    }


    public void applyDelayedHeal(GameInstance gameInstance, Vector2 poi) {
        if (delayedHeal.size() > 0) {
            if (delayedHeal.get(0) > 0) {
                //JUnit Test Compat
                if (gameInstance != null && poi != null) {
                    gameInstance.getAnimationHUD().addHealAnimation(poi, delayedHeal.get(0), 3f);
                }
            }
            heal(delayedHeal.get(0));
            delayedHeal.remove(0);
        }
    }

    public void addHeal(Integer val, Integer turn) {
        if (delayedHeal.size() > turn) {
            delayedHeal.set(turn, delayedHeal.get(turn) + val);
        } else {
            delayedHeal.add(turn, val);
        }
    }

    public void addDamage(Integer val, Integer turn) {
        if (delayedDamage.size() > turn) {
            delayedDamage.set(turn, delayedDamage.get(turn) + val);
        } else {
            delayedDamage.add(turn, val);
        }
    }

    // ADDED FOR ASSESSMENT 4:  incMana
    public void incMana(){
        additionalMana += 1;
    }

    public void deductMana(Integer val) {
        mana -= val;
    }

    public void useCard(Card card) {
        hand.remove(card);
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void addCard(Card card) {
        deck.add(card);
    }
}
