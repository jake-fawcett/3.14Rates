package uk.ac.york.sepr4.object.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import lombok.Data;

import java.util.Random;

@Data
public class ItemManager {

    private Array<Item> items;


    private Integer baseXP = 10, baseGold = 100;

    public ItemManager() {
        Json json = new Json();
        items = json.fromJson(Array.class, Item.class, Gdx.files.internal("items.json"));
        Gdx.app.log("ItemManager", "Loaded "+items.size+" items!");

    }

    public Reward generateReward() {
        Random random = new Random();
        Double scale = random.nextDouble();

        return new Reward(baseXP + (scale.intValue()*100), baseGold + (scale.intValue()*10));
    }

}
