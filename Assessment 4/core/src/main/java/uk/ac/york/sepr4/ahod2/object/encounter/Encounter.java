package uk.ac.york.sepr4.ahod2.object.encounter;


import lombok.Getter;

import java.util.List;

/***
 * Card used to represent instance of encounter.
 * Contains data used to populate EncounterScreen.
 */
@Getter
public class Encounter {

    private Integer id;
    private String name, text;
    private List<EncounterOption> options;
    private double chance;
    private String background = "default.png";

    public Encounter() {
        //json
    }

}
