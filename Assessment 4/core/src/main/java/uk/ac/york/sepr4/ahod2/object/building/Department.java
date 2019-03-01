package uk.ac.york.sepr4.ahod2.object.building;

import lombok.Data;

/***
 * Class used to represent instance of department.
 * Contains id, name, cost to repair boat and minigame scaling factor (cost and reward).
 */
@Data
public class Department {

    private Integer id, repairCost, minigamePower;
    private String name;

    public Department() {
        //json
    }

}
