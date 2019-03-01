package uk.ac.york.sepr4.ahod2.object.encounter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/***
 * Class used to load and generate weighted instances of encounters.
 * Encounters loaded from JSON file.
 */
public class EncounterManager {

    private final NavigableMap<Double, Encounter> map = new TreeMap<>();
    private double weights = 0;

    public EncounterManager() {
        Json json = new Json();

        //add encounter to weighted map
        Array<Encounter> encounters = json.fromJson(Array.class, Encounter.class, Gdx.files.internal("data/encounters.json"));
        encounters.forEach(encounter -> {
            weights += encounter.getChance();
            map.put(weights, encounter);
        });

        Gdx.app.log("EncounterManager", "Loaded " + encounters.size + " encounters!");
    }

    /***
     * Generate random encounter based on weighted chance.
     * @return random encounter instance.
     */
    public Encounter generateEncounter() {
        Random random = new Random();
        double val = random.nextDouble() * weights;
        return map.higherEntry(val).getValue();

    }
}
