package uk.ac.york.sepr4.object.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import uk.ac.york.sepr4.GameScreen;
import uk.ac.york.sepr4.hud.HealthBar;
import uk.ac.york.sepr4.utils.AIUtil;

@Data
public abstract class LivingEntity extends Entity {

    private Double health = 20.0, maxHealth = 20.0, damage = 5.0;
    private boolean isAccelerating, isBraking, isDead, isDying;
    private float turningSpeed = 2.3f;
    private float currentCooldown = 0f, reqCooldown = 0.5f, maxSpeed = 100f, angularSpeed = 0f, acceleration = 40f;

    //TODO: Better ways to monitor this
    private int collidedWithIsland = 0, colliedWithBoat = 0;

    private HealthBar healthBar;

    public LivingEntity(Texture texture, Vector2 pos) {
        super(texture, pos);

        this.healthBar = new HealthBar(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void kill(boolean silent) {
        //if not silent, death animation will appear
        this.isDying = !silent;
        this.isDead = silent;
    }

    /***
     * Called to action collision action (boat reversal)
     * @param withBoat true if collision was with another LivingEntity (boat)
     */
    public void collide(boolean withBoat, float thetaTP) {
        if(withBoat) {
            setColliedWithBoat(10);
            setAngle(thetaTP);
        } else {
            setCollidedWithIsland(10);
            setAngle(AIUtil.normalizeAngle(getAngle() - (float) Math.PI));
        }
        //setAngle(AIUtil.normalizeAngle(getAngle() - (float) Math.PI));
        if (getSpeed() > getMaxSpeed() / 5) {
            setSpeed(getMaxSpeed() / 5);
        }
    }

    public HealthBar getHealthBar() {
        this.healthBar.update();
        return this.healthBar;
    }

    @Override
    public void act(float deltaTime) {
        //Assessment 3 - do nothing if paused
        if (GameScreen.isPaused()) {
            return;
        }
        setCurrentCooldown(getCurrentCooldown() + deltaTime);

        if (!this.isDying) {
            float speed = getSpeed();

            if (isAccelerating) {

                if (speed > maxSpeed) {
                    speed = maxSpeed;
                } else {
                    //Changed for Assessment 3: acceleration is now a variable
                    speed += acceleration * deltaTime;
                }
            } else if (isBraking) {
                if (speed > 0) {
                    speed -= 80f * deltaTime;
                }
            } else {
                if (speed > 0) {
                    speed -= 20f * deltaTime;
                }
            }
            setSpeed(speed);
            super.act(deltaTime);
        }
    }

    /***
     * Called to inflict damage on LivingEntity
     * @param damage amount of damage to inflict
     * @return true if LivingEntity alive
     */
    public boolean damage(Double damage) {
        this.health = this.health - damage;
        if (this.health <= 0) {
            kill(false);
            return false;
        }
        return true;
    }

    /***
     * Called when a LivingEntity is to fire a shot.
     * @param angle angle at which to fire
     * @param damage damage the fired bullet deals
     * @return true if cooldown sufficient and shot has been fired
     */
    public boolean fire(float angle, double damage) {
        EntityManager entityManager = GameScreen.getInstance().getEntityManager();
            if (currentCooldown >= reqCooldown) {
                setCurrentCooldown(0f);
                entityManager.getProjectileManager().spawnProjectile( this, getSpeed(), angle, damage);
                entityManager.getAnimationManager().addFiringAnimation(this,angle - (float)Math.PI/2);
                return true;
            }

        return false;
    }

    //Added for Assessment 3: tripleFire method to enable the triple fire upgrade for the player
    /***
      * Make a LivingEntity fire 3 bullets
      * @param angle angle at which to fire
      * @param damage damage the bullet will deal
      * @return true if cooldown sufficient and shot has been fired
      */
    public boolean tripleFire(float angle, double damage) {
        EntityManager entityManager = GameScreen.getInstance().getEntityManager();
        if (currentCooldown >= reqCooldown) {
            setCurrentCooldown(0f);
            entityManager.getProjectileManager().spawnProjectile(this, getSpeed(), angle, damage);
            entityManager.getProjectileManager().spawnProjectile(this, getSpeed(), angle + 0.15f, damage);
            entityManager.getProjectileManager().spawnProjectile(this, getSpeed(), angle - 0.15f, damage);
            return true;
        }
        return false;
    }
}
