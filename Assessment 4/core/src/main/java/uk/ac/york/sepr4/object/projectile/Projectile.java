package uk.ac.york.sepr4.object.projectile;

import com.badlogic.gdx.Gdx;
import lombok.Data;
import uk.ac.york.sepr4.TextureManager;
import uk.ac.york.sepr4.object.entity.Entity;
import uk.ac.york.sepr4.object.entity.LivingEntity;

@Data
public class Projectile extends Entity {

    private LivingEntity shooter;
    private Double damage = 5.0;
    private Integer baseSpeed = 100;

    private boolean active = true;

    public Projectile(LivingEntity shooter, float speed, float angle) {
        //Take speed from the shooter so bullet has a speed relative to the shooter
        //TODO: Make the speed direction dependant (how much of the force is in the direction of the projectile)
        super(TextureManager.CANNONBALL, shooter.getCentre());

        this.shooter = shooter;

        setAngle(angle);
        setSpeed(speed + baseSpeed);
    }

    /**
     * Added for Assessment 3: overloaded projectile constructor to add a damage parameter
     * @param shooter The entity shooting the projectile
     * @param speed Speed of the projectile
     * @param angle Angle at which the projectile is shot
     * @param damage Damage dealt on impact by projectile
     */
    public Projectile(LivingEntity shooter, float speed, float angle, double damage){
         super(TextureManager.CANNONBALL, shooter.getCentre());

        this.shooter = shooter;

        setAngle(angle);
        setSpeed(speed + baseSpeed);
        setDamage(damage);
    }

    @Override
    public void act(float deltaTime) {
        if(this.distanceFrom(shooter) > 1000) {
            Gdx.app.debug("Projectile","Clearing up distant projectile!");
            this.active = false;
        } else {
            super.act(deltaTime);
        }
    }
}
