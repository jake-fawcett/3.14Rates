package uk.ac.york.sepr4.ahod2.object;

import com.badlogic.gdx.Gdx;
import lombok.Data;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.object.building.BuildingManager;
import uk.ac.york.sepr4.ahod2.object.building.College;
import uk.ac.york.sepr4.ahod2.object.building.Department;
import uk.ac.york.sepr4.ahod2.screen.sail.NodeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * Class representing a level of the game.
 * Loaded from JSON file. Contains attributes such as college (boss) id,
 * node map depth (how many rows of nodes), department ids and reward (gold).
 */
@Data
public class GameLevel {

    private Integer id, levelGold, battleGold, collegeId, difficulty, depth;
    private List<Integer> departmentIds;

    //populated when loaded by gameinstance
    private College college;
    private List<Department> departments = new ArrayList<>();
    private NodeView nodeView;

    public GameLevel() {
        //json
    }

    /***
     * Load level, check departments and college with ids specified exist.
     * @param gameInstance
     * @return true if loaded, false if error
     */
    public boolean load(GameInstance gameInstance) {
        BuildingManager buildingManager = gameInstance.getBuildingManager();
        //check if college with id specified exists
        Optional<College> optCollege = buildingManager.getCollegeByID(collegeId);
        if (!optCollege.isPresent()) {
            Gdx.app.error("GameLevel", "College with ID specified doesnt exist for GameLevel " + id);
            return false;
        }
        college = optCollege.get();
        //check if departments with id specified exists
        for (Integer departmentId : departmentIds) {
            Optional<Department> optDept = buildingManager.getDepartmentByID(departmentId);
            if (!optDept.isPresent()) {
                Gdx.app.error("GameLevel", "Dept with ID:" + departmentId + " specified doesnt exist for GameLevel " + id);
                return false;
            }
            departments.add(optDept.get());
        }
        if (nodeView == null) {
            //generate nodeview for this level
            nodeView = new NodeView(gameInstance.getSailScreen(), this);
        }
        Gdx.app.debug("GameLevel", "Successfully loaded level " + id);
        return true;
    }


}
