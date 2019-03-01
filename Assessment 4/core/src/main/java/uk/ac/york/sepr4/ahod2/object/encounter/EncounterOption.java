package uk.ac.york.sepr4.ahod2.object.encounter;

import lombok.Data;

/***
 * Class represents possible option choice in Encounter dialog.
 * Contains variable used to enter into battle or alter player's balance.
 */
@Data
public class EncounterOption {

    private String text;
    private Integer gold;
    private boolean battle = false;
    //difficulty of enemy if battle = true
    private Integer difficulty;

    public EncounterOption() {
        //json
    }

}
