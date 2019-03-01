package uk.ac.york.sepr4.object.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import uk.ac.york.sepr4.object.building.Building;
import uk.ac.york.sepr4.object.building.College;
import uk.ac.york.sepr4.object.building.Department;
import uk.ac.york.sepr4.object.projectile.ProjectileManager;
import uk.ac.york.sepr4.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EntityManager {

    private Player player;

    @Getter
    private Array<NPCBoat> npcList = new Array<>();

    private GameScreen gameScreen;

    @Getter
    private AnimationManager animationManager;
    @Getter
    private ProjectileManager projectileManager;

    public EntityManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        this.projectileManager = new ProjectileManager();
        this.animationManager = new AnimationManager(this);
    }
    
    public Player getOrCreatePlayer() {
        if(player == null) {
            player = new Player(gameScreen.getPirateMap().getSpawnPoint());
            animationManager.createWaterTrail(player);
        }
        return player;
    }

    public List<LivingEntity> getLivingEntities() {
        List<LivingEntity> list = new ArrayList<>();
        npcList.forEach(npcBoat -> list.add(npcBoat));
        list.add(getOrCreatePlayer());

        return new ArrayList<>(list);
    }

    public boolean isOccupied(Rectangle rectangle) {
        for(LivingEntity livingEntities : getLivingEntities()) {
            if(rectangle.overlaps(livingEntities.getRectBounds())) {
                return true;
            }
        }
        return false;
    }

    public Optional<Building> getPlayerLocation() {
        Optional<Building> loc = Optional.empty();
        for(College building : gameScreen.getBuildingManager().getColleges()) {
            if(building.getBuildingZone().contains(player.getX(), player.getY())) {
                loc = Optional.of(building);
            }
        }
        for(Department building : gameScreen.getBuildingManager().getDepartments()) {
            if(building.getBuildingZone().contains(player.getX(), player.getY())) {
                loc = Optional.of(building);
            }
        }
        return loc;
    }

    public void addNPC(NPCBoat npcBoat){
        if(!npcList.contains(npcBoat, false)) {
            this.npcList.add(npcBoat);
            animationManager.createWaterTrail(npcBoat);
        } else {
            Gdx.app.error("EntityManager", "Tried to add an NPC with ID that already exists!");
        }
    }

    public void handleStageEntities(Stage stage, float delta){
        projectileManager.handleProjectiles(stage);
        handleNPCs(stage);
        animationManager.handleEffects(stage, delta);
    }

    /**
     * Adds and removes NPCs as actors from the stage.
     */
    private void handleNPCs(Stage stage) {
        stage.getActors().removeAll(removeDeadNPCs(), true);

        for (NPCBoat NPCBoat : npcList) {
            if (!stage.getActors().contains(NPCBoat, true)) {
                Gdx.app.debug("EntityManager", "Adding new NPCBoat to actors list.");
                stage.addActor(NPCBoat);
            }
        }
    }

    public Array<LivingEntity> getLivingEntitiesInArea(Rectangle rectangle) {
        Array<LivingEntity> entities = new Array<>();
        for(NPCBoat NPCBoat : npcList) {
            if(NPCBoat.getRectBounds().overlaps(rectangle)){
                entities.add(NPCBoat);
            }
        }
        if(player.getRectBounds().overlaps(rectangle)) {
            entities.add(player);
        }
        return entities;
    }

    private Array<NPCBoat> removeDeadNPCs() {
        Array<NPCBoat> toRemove = new Array<>();
        for(NPCBoat NPCBoat : npcList) {
            if(NPCBoat.isDead()){
                toRemove.add(NPCBoat);
            }
        }
        npcList.removeAll(toRemove, true);
        return toRemove;
    }









}
