package uk.ac.york.sepr4.object.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import uk.ac.york.sepr4.object.entity.LivingEntity;

public class ProjectileManager {

    @Getter
    private Array<Projectile> projectileList;

    public ProjectileManager() {
        this.projectileList = new Array<>();

    }


    public void spawnProjectile(LivingEntity livingEntity, float speed, float angle) {
        Projectile projectile = new Projectile(livingEntity, speed, angle);
        projectileList.add(projectile);
    }

    /**
     * Added for Assessment 3: overloaded spawnProjectile to add a damage parameter
     */
    public void spawnProjectile(LivingEntity livingEntity, float speed, float angle, double damage) {
        Projectile projectile = new Projectile(livingEntity, speed, angle, damage);
        projectileList.add(projectile);
    }
    public Array<Projectile> getProjectileInArea(Rectangle rectangle) {
        Array<Projectile> projectiles = new Array<>();
        for(Projectile projectile : projectileList) {
            Intersector.overlaps(rectangle, projectile.getRectBounds());
            if(projectile.getRectBounds().overlaps(rectangle)){
                projectiles.add(projectile);
            }
        }
        return projectiles;
    }
    /**
     * Adds and removes projectiles as actors from the stage.
     */
    public void handleProjectiles(Stage stage) {
        stage.getActors().removeAll(removeNonActiveProjectiles(), true);

        for (Projectile projectile : getProjectileList()) {
            if (!stage.getActors().contains(projectile, true)) {
                Gdx.app.debug("ProjectileManager", "Adding new projectile to actors list.");
                stage.addActor(projectile);
            }
        }
    }

    public Array<Projectile> removeNonActiveProjectiles() {
        Array<Projectile> toRemove = new Array<Projectile>();
        for(Projectile projectile : projectileList) {
            if(!projectile.isActive()){
                toRemove.add(projectile);
            }
        }
        projectileList.removeAll(toRemove, true);
        return toRemove;
    }

}
