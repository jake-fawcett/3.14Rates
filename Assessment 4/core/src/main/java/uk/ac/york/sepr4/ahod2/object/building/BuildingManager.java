package uk.ac.york.sepr4.ahod2.object.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import lombok.Data;
import uk.ac.york.sepr4.ahod2.GameInstance;

import java.util.Optional;

/***
 * Class responsible for loading instances of colleges and departments from file.
 * Also contains utility functions for getting buildings from id, etc.
 */
@Data
public class BuildingManager {

    private GameInstance gameInstance;

    private Array<College> colleges;
    private Array<Department> departments;

    public BuildingManager(GameInstance gameInstance) {
        this.gameInstance = gameInstance;

        Json json = new Json();
        loadColleges(json.fromJson(Array.class, College.class, Gdx.files.internal("data/colleges.json")));
        loadDepartments(json.fromJson(Array.class, Department.class, Gdx.files.internal("data/departments.json")));

        Gdx.app.log("BuildingManager", "Loaded " + colleges.size + " colleges!");
    }

    /***
     * Get college with specified id if exists.
     * @param id specified id
     * @return Optional of college if exists, else empty optional.
     */
    public Optional<College> getCollegeByID(Integer id) {
        for (College college : colleges) {
            if (college.getId() == id) {
                return Optional.of(college);
            }
        }
        return Optional.empty();
    }

    /***
     * Get department with specified id if exists.
     * @param id specified id
     * @return Optional of department if exists, else empty optional.
     */
    public Optional<Department> getDepartmentByID(Integer id) {
        for (Department department : departments) {
            if (department.getId() == id) {
                return Optional.of(department);
            }
        }
        return Optional.empty();
    }

    private void loadColleges(Array<College> colleges) {
        this.colleges = colleges;
    }

    private void loadDepartments(Array<Department> departments) {
        this.departments = departments;
    }


}
