package uk.ac.york.sepr4.object.quest;

import lombok.Data;
import uk.ac.york.sepr4.object.item.Reward;

import java.util.List;

@Data
public class Quest {

    private String name, startMessage, endMessage;
    private List<Quest> requires, gives;
    private Reward reward;
    private String targetEntityName;
    private boolean isKillQuest, isStarted, isCompleted;


    public Quest(){
        // Empty constructor for JSON DAO.
    }

}
