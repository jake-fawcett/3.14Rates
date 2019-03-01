package uk.ac.york.sepr4.ahod2.object;

import com.badlogic.gdx.Gdx;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.object.card.Card;
import uk.ac.york.sepr4.ahod2.object.entity.Ship;

import java.util.Optional;
import java.util.Random;

/***
 * Utility class used to randomly generate enemy ships.
 */
public class ShipFactory {

    private static final Integer healthInterval = 3;

    //difficulty 1-10

    /***
     * Generate enemy ship with specified difficulty used to scale health and cards chosen.
     * @param difficulty
     * @return instance of random enemy ship
     */
    public static Ship generateEnemyShip(Integer difficulty) {
        Random random = new Random();
        Ship ship = new Ship();

        Integer health = ship.getMaxHealth() + difficulty * random.nextInt(healthInterval + 1);
        ship.setMaxHealth(health);
        ship.setHealth(health);

        Integer noCards = random.nextInt(difficulty);
        while (noCards > 0) {
            Optional<Card> optional = GameInstance.INSTANCE.getCardManager().randomCard(difficulty);
            if (optional.isPresent()) {
                ship.addCard(optional.get());
                noCards--;
            } else {
                Gdx.app.debug("ShipFactory", "Couldn't get valid card!");
            }
        }
        return ship;
    }

}
