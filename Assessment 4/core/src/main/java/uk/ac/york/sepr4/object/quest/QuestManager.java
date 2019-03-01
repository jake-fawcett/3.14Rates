package uk.ac.york.sepr4.object.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import lombok.Data;
import uk.ac.york.sepr4.object.entity.EntityManager;

@Data
public class QuestManager {

    private Array<Quest> questList;
    private EntityManager entityManager;

    public QuestManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        Json json = new Json();
        this.questList= json.fromJson(Array.class, Quest.class, Gdx.files.internal("quests.json"));

    }



}
