package base;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PhysicsActor extends BaseActor {

    private Vector2 velocity;
    private Vector2 acceleration;

    /**
     * Maximum speed
     */
    private float maxSpeed;

    /**
     * Speed reduction, in pixels/second, when not accelerating
     */
    private float deceleration;

    /**
     * Should image rotate to match velocity?
     */
    private boolean autoAngle;

    /**
     * Is the ship anchored?
     */
    private boolean anchor;

    public PhysicsActor() {
        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.maxSpeed = 9999;
        this.deceleration = 0;
        this.autoAngle = false;
        this.anchor = true;
    }

    public void setAccelerationXY(float ax, float ay) {acceleration.set(ax, ay);}

    /**
     * Set acceleration from angle and speed
     */
    public void setAccelerationAS(float angleDeg, float speed) {
        acceleration.x = speed * MathUtils.cosDeg(angleDeg);
        acceleration.y = speed * MathUtils.sinDeg(angleDeg);
    }

    public void setDeceleration(float d) {deceleration = d;}

    public float getSpeed() {return velocity.len();}

    public void setSpeed(float s) {velocity.setLength(s);}

    public void setMaxSpeed(float ms) {maxSpeed = ms;}

    /**
     * @return the angle of motion of an Actor
     */
    public float getMotionAngle() {
        return MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees;
    }

    /**
     * Adjust acceleration of the Actor based on a given strength and angle
     * @param angle The angle of acceleration
     * @param amount The magnitude of acceleration
     */
    public void addAccelerationAS(float angle, float amount) {
        acceleration.add(amount * MathUtils.cosDeg(angle),amount * MathUtils.sinDeg(angle));
    }

    public void act(float dt) {
        super.act(dt);

        // apply acceleration
        velocity.add(acceleration.x * dt,acceleration.y * dt);

        // decrease velocity when not accelerating
        if (acceleration.len() < 0.01) {
            float decelerateAmount = deceleration * dt;
            if (getSpeed() < decelerateAmount) setSpeed(0);
            else setSpeed(getSpeed() - decelerateAmount);
        }

        // cap at max speed
        if (getSpeed() > maxSpeed) setSpeed(maxSpeed);

        // apply velocity
        moveBy(velocity.x * dt,velocity.y * dt);

        // rotate image when moving
        if (autoAngle && getSpeed() > 0.1) setRotation(getMotionAngle());
    }

    public void setAnchor(boolean anchor) { this.anchor = anchor; }

    public boolean isAnchor() { return anchor; }
}
