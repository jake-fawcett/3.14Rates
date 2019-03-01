package uk.ac.york.sepr4.ahod2.object.card;

import com.badlogic.gdx.graphics.Texture;
import lombok.Data;

/***
 * Class used to represent instance of card.
 */
@Data
public class Card {

    private Integer id, manaCost, damage, damageSelf, moveTime, heal, power, defaultNo;
    private String name, desc, textureStr;
    private boolean _default;

    private Texture texture;

    public Card() {
        //load from json
    }

}
