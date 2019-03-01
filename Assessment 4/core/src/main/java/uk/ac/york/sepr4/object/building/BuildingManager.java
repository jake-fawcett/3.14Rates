package uk.ac.york.sepr4.object.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import lombok.Data;
import uk.ac.york.sepr4.object.entity.NPCBuilder;
import uk.ac.york.sepr4.object.entity.Player;
import uk.ac.york.sepr4.GameScreen;
import uk.ac.york.sepr4.object.entity.NPCBoat;

import javax.naming.NameNotFoundException;
import java.util.Optional;
import java.util.Random;

@Data
public class BuildingManager {

    private Array<College> colleges = new Array<>();
    private Array<Department> departments = new Array<>();
    private MinigameBuilding minigame;

    private GameScreen gameScreen;

    //time till next spawn attempt
    private float spawnDelta;

    /***
     * This class handles instances of buildings (Colleges and Departments)
     *
     * It is responsible for loading from file and making sure the map object relating to this building is present.
     * There is a method which arranges spawning of college enemies.
     * @param gameScreen
     */
    public BuildingManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.spawnDelta = 0f;

        if(gameScreen.getPirateMap().isObjectsEnabled()) {
            Json json = new Json();
            loadBuildings(json.fromJson(Array.class, College.class, Gdx.files.internal("colleges.json")));
            loadBuildings(json.fromJson(Array.class, Department.class, Gdx.files.internal("departments.json")));
            loadBuildings(json.fromJson(Array.class, MinigameBuilding.class, Gdx.files.internal("minigame.json")));
            Gdx.app.log("BuildingManager",
                    "Loaded "+colleges.size+" colleges and "+departments.size+" departments!");

        } else {
            Gdx.app.error("Building Manager", "Objects not enabled, not loading buildings!");
        }
    }

    /**
     * Added for Assessment 3: Added a text prompt to enter department when near
     */
    public void departmentPrompt() {
        for (Department department : departments) {
            Player player = gameScreen.getEntityManager().getOrCreatePlayer();
            if (department.getBuildingZone().contains(player.getRectBounds())) {
                gameScreen.setNearDepartment(true);
            }
            else {
                gameScreen.setNearDepartment(false);
            }
        }
    }

    /**
     * Added for Assessment 3: Text prompt to access minigame
     */
    public void minigamePrompt(){
        Player player = gameScreen.getEntityManager().getOrCreatePlayer();
        if (minigame.getBuildingZone().contains(player.getRectBounds())) {
            gameScreen.setNearMinigame(true);
        }
        else {
            gameScreen.setNearMinigame(false);
        }
    }

    //TODO: Could have a cooldown here
    public void checkBossSpawn() {
        for(College college : colleges) {
            if(!college.isBossSpawned()) {
                //TODO: Add collision check for boss spawn
                Player player = gameScreen.getEntityManager().getOrCreatePlayer();
                if (college.getBuildingZone().contains(player.getRectBounds())) {
                    Gdx.app.debug("BuildingManager", "Player entered college zone: " + college.getName());
                    Optional<NPCBoat> npcBoss = generateCollegeNPC(college, true);
                    if(npcBoss.isPresent()) {
                        college.setBossSpawned(true);
                        gameScreen.getEntityManager().addNPC(npcBoss.get());
                    }

                }
            }
        }
    }

    private Optional<Vector2> getValidRandomSpawn(College college, float size) {
        int attempts = 0;
        while (attempts<10) {
            Vector2 test = college.getRandomSpawnVector();
            Rectangle rectangle = new Rectangle(test.x-(size/2), test.y-(size/2), size, size);
            if(!gameScreen.getPirateMap().isColliding(rectangle)
                    && !gameScreen.getEntityManager().isOccupied(rectangle)) {
                return Optional.of(test);
            }
            attempts++;
        }
        return Optional.empty();
    }

    /**
     * Generate an NPCBoat which has the appropriate position and difficulty for a college
     * @param college College for which the NPCBoat is being generated
     * @param boss Whether the generated npc is a boss
     * @return       An NPCBoat with correct parameters
     */
    private Optional<NPCBoat> generateCollegeNPC(College college, boolean boss) {
        Random random = new Random();
        if(random.nextDouble() <= college.getSpawnChance()){
            Optional<Vector2> pos = getValidRandomSpawn(college, 250f);
            if(pos.isPresent()) {
                NPCBoat boat = new NPCBuilder().generateRandomEnemy( pos.get(), college,
                         boss ? college.getBossDifficulty().floatValue() : college.getEnemyDifficulty().floatValue(), boss);
                return Optional.of(boat);
            }
        }
        return Optional.empty();
    }

    public void spawnCollegeEnemies(float delta) {
        spawnDelta+=delta;
        if(spawnDelta >= 1f) {
            for (College college : this.colleges) {
                //check how many entities already exist in college zone (dont spawn too many)
                if(gameScreen.getEntityManager().getLivingEntitiesInArea(college.getBuildingZone()).size
                        < college.getMaxEntities()) {
                    Optional<NPCBoat> optionalEnemy = generateCollegeNPC(college,false);
                    if (optionalEnemy.isPresent()) {
                        //checks if spawn spot is valid
                        Gdx.app.debug("Building Manager", "Spawning an enemy at " + college.getName());
                        gameScreen.getEntityManager().addNPC(optionalEnemy.get());
                    }
                } else {
                    //Gdx.app.debug("BuildingManager", "Max entities @ "+college.getName());
                }
            }
            spawnDelta = 0f;
        }
    }

    //TODO: Make generic method
    private void loadBuildings(Array<Building> loading) {
        for(Building building : loading) {
            if (building.load(gameScreen.getPirateMap())) {
                if(building instanceof College) {
                    colleges.add((College) building);
                } else if (building instanceof Department) {
                    departments.add((Department) building);
                } else if (building instanceof MinigameBuilding) {
                    minigame = (MinigameBuilding) building;
                }
                Gdx.app.debug("BuildingManager", "Loaded " + building.getName());
            } else {
                Gdx.app.error("BuildingManager", "Failed to load " + building.getName());
            }

        }
    }


//    //TODO: Make generic method
//    private void loadColleges(Array<College> loading) {
//        for(College college : loading) {
//            if (college.load(gameScreen.getPirateMap())) {
//                    colleges.add(college);
//                    Gdx.app.debug("BuildingManager", "Loaded " + college.getName());
//                } else {
//                    Gdx.app.error("BuildingManager", "Failed to load " + college.getName());
//                }
//
//        }
//    }
//
//    private void loadDepartments(Array<Department> loading) {
//        for(Department department : loading) {
//            if (department.load(gameScreen.getPirateMap())) {
//                    departments.add(department);
//                    Gdx.app.debug("BuildingManager", "Loaded " + department.getName());
//                } else {
//                    Gdx.app.error("BuildingManager", "Failed to load " + department.getName());
//
//                }
//
//        }
//    }
}
