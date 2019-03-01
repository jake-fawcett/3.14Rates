package uk.ac.york.sepr4.object.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import lombok.Data;
import uk.ac.york.sepr4.object.building.College;
import uk.ac.york.sepr4.object.projectile.Projectile;
import uk.ac.york.sepr4.GameScreen;
import uk.ac.york.sepr4.utils.AIUtil;
import java.util.Optional;
import java.util.Random;

@Data
public class NPCBoat extends LivingEntity {

    //NPCBoat-specific variables
    private float range = 500f; //How far away it can see livingEntities/objects
    private float accuracy = 0.5f; //This is how accurate this is (currently is (1/0.5)*Math.PI/32 which allows for that much range on both sides of the perfect shot and picks a random angle from that range

    private float idealDistFromTarget = 250f; //For the distance you want NPC to be away from target (Goldy Lox Zone)
    private float gradientForNormalDist = 50f; //This is the standard deviation of the normal distabution

    private Optional<College> allied = Optional.empty(); //This is the faction the boat is allied with
    private Optional<LivingEntity> lastTarget = Optional.empty(); //This is the target currently being fought

    private boolean previousTurn = true; //right = true, left = false this is used to be able to take infomation over frames it is the prevoius turn the NPC did
    private boolean turning = false; //This is wether the boat is turning or not. ~In conjuction these to help turnPreCalc determine the angularSpeed across multiple frames

    private int dodging = 0; //Not dodging = 0 anything other than 0 is meaning dodging. this is the amount of frames you want the NPC to dodge for
    private float difficulty;

    private Random r = new Random(); //Just for randomness

    private float targetCheck = 3f; //Timer so target check aint every frame

    private boolean isBoss;

    private boolean hasTripleFire;

    public NPCBoat(Texture texture, Vector2 pos, float difficulty) {
        super(texture, pos);
        this.difficulty = difficulty;
    }

    /***
     *  This is the control logic of the NPCs AI. It uses functions from mainly AIUtil to be able to make decisions on how it is meant to behave.
     *  They are broken down into sections as to be able to make the code and control structure easier to read.
     *  When calling this function it will actually make the NPC that is in the world do the actions.
     *
     * @param deltaTime time since last act
     */
    public void act(float deltaTime) {
        // Assessment 3: do nothing if paused
        if (GameScreen.isPaused()) {
            return;
        }
        //Clears arrays for later use
        Array<Float> forces = new Array<>();
        Array<Float> angles = new Array<>();

        if (!this.isDying()) {
            //TARGET CHECK***************
            // timer to check for new target (expensive if done every tick)
            if (targetCheck < 4f) {
                targetCheck += deltaTime;
            }
            //Gets a target
            Optional<LivingEntity> optionalTarget = getTarget();
            if (optionalTarget.isPresent()) {
                LivingEntity target = optionalTarget.get();
                this.lastTarget = optionalTarget;
                //***************************


                //FORCES WANTED TO BE COMPUTED***************
                //Explained more on the resultant force function take a look
                float f = AIUtil.normalDistFromMean((float) this.distanceFrom(target), this.gradientForNormalDist, this.idealDistFromTarget); //---Normal Distrubtion 0 to 1 in max force this allows for us to have diffrent forces depending on distances to the player

                //Forces due to the target**
                if ((float) this.distanceFrom(target) < this.idealDistFromTarget) {
                    forces.add(1 - f);
                    angles.add(AIUtil.normalizeAngle(this.getAngleTowardsEntity(target)));
                } else {
                    forces.add(1 - f);
                    angles.add(AIUtil.normalizeAngle(this.getAngleTowardsEntity(target) - (float) Math.PI));
                }
                forces.add(f);
                angles.add(AIUtil.normalizeAngle(target.getAngle() - (float) Math.PI));
                //**

                //Forces due to the other living entitys**
                for (LivingEntity livingentity : getLivingEntitiesInRangeMinusTarget(target)) {
                    float n = AIUtil.normalDistFromMean((float) this.distanceFrom(livingentity), 50, 200); //---Normal Distrubtion again but to all livining entitys stops them wanting to collide
                    if ((float) this.distanceFrom(livingentity) < 200) {
                        forces.add((1 - n) / 2);
                        angles.add(AIUtil.normalizeAngle(this.getAngleTowardsEntity(livingentity) - (float) Math.PI));
                    } else {
                        forces.add((1 - n) / 2);
                        angles.add(AIUtil.normalizeAngle(this.getAngleTowardsEntity(livingentity)));
                    }
                }
                //**

                //Other forces can be applied in this way where the forces can be any value. In the cases above the max values they can get is 1 this should give you rough estimates of the power of the forces
                //Really good to add in functions that take into account certain things for strategic poistioning/cool interactions like ramming and whirlpools and things like that *HINT* *HINT*

                //********************************************


                //RESULTANT ANGLE*****************
                //Gets the resultant force of all the forces and angles of those forces given by forces and angles arrays
                //returns and array which is basically a pair being (resultant force, resultant forces angle)
                float ang = AIUtil.resultantForce(angles, forces).get(1);
                //********************************


                //NO DUMB MOVE CHECK**************
                //This section can be made to check whether certain moves maybe a bad move, e.g. moving into projectiles firing line, better strategic poistioning *HINT* *HINT*
                float wantedAngle = ang; //change
                //********************************


                //SPEED STUFF*****************
                //Look at NPC Behaviour 2 for more details/visuallisation
                //If not dodging
                if (this.dodging == 0) {
                    //gets the normal of the angle towards the boat explained in NPC Functions 3
                    float NormalFactor = Math.min(AIUtil.normalDistFromMean(AIUtil.angleDiffrenceBetweenTwoAngles(AIUtil.normalizeAngle(target.getAngle()), getAngleTowardsEntity(target)), (float) Math.PI / 8, (float) Math.PI / 2) * 100, 1f);
                    float A;
                    if (target.getSpeed() > getMaxSpeed()) {
                        A = getMaxSpeed();
                    } else {
                        A = target.getSpeed();
                    }
                    float idealSpeed = (1 - f) * getMaxSpeed() + ((f + NormalFactor) / 2) * A;
                    if (idealSpeed > getSpeed()) {
                        setAccelerating(true);
                        setBraking(false);
                    } else {
                        if (getSpeed() / 5 > idealSpeed) {
                            setBraking(true);
                            setAccelerating(false);
                        } else {
                            setAccelerating(false);
                            setBraking(false);
                        }
                    }
                } else {
                    //If dodging act this out
                    setAccelerating(false);
                    setBraking(true);
                    this.dodging -= 1;
                }
                //****************************


                //DODGE STARTER*****************

                //Actual dodge movements are implemented in Speed stuff but for more advanced dodge can be adjusted in both Speed stuff and Turn action *HINT* *HINT*

                //Gets all projectiles that will hit NPC and if above 0
                if (getProjectilesToDodge(getProjectilesInRange()).size > 0) {

                    //Set on a dodge if probability has chosen
                    float prob = 1f * getProjectilesToDodge(getProjectilesInRange()).size;
                    float random = r.nextFloat() * 100f;
                    if (random < prob) {
                        setDodging(100);
                        Gdx.app.debug("NPCBoat", "Dodging");
                    }
                } else {
                    //Stops the NPC being still for longer than needed
                    if (getDodging() > 0) {
                        setDodging(10);
                    }
                }
                //******************************


                //TURN ACTION*******************
                //Stops movement of under PI/16 from actually taking affect
                if (AIUtil.angleDiffrenceBetweenTwoAngles(getAngle(), wantedAngle) < Math.PI / 16) {
                    this.turning = false;
                } else {
                    this.turning = true;
                }

                //Checks for changes in turning so angular speed will be correct
                turnPreCalcs(AIUtil.rightForAngleDiffrenceBetweenTwoAngles(getAngle(), wantedAngle));

                //Sets previous turn to this turns right boolean e.g. if turning right then = true else false meaning left turn
                this.previousTurn = AIUtil.rightForAngleDiffrenceBetweenTwoAngles(getAngle(), wantedAngle);

                //Sets the angle depending on parameters same as in livingentity
                setAngle(getAngle() + (getAngularSpeed() * deltaTime) * (getSpeed() / getMaxSpeed()) % (float) (2 * Math.PI));
                //******************************


                //FIRING************************
                //Calculates perfectShot into fireangle then adds some randomness to the shot with the parameter of accuracy which is inveresed

                if (target.getSpeed() < target.getMaxSpeed() / 5) {
                    float fireangle = getAngleTowardsEntity(target);
                    //Calls fire at angle
                    fire((float) (fireangle + (-(1 / getAccuracy()) * (Math.PI / 32) + r.nextFloat() * (2 * (1 / getAccuracy()) * (Math.PI / 32)))), getDamage());
                } else {
                    //Stops the AI shooting at distances that are longer than 3 seconds due to infinte inteception points, if going parrell
                    if (AIUtil.timeForPerfectAngleToCollide(this, target, AIUtil.thetaForAngleDiffrence(AIUtil.normalizeAngle(target.getAngle()), getAngleTowardsEntity(target)), 100) < 3) {
                        float fireangle = AIUtil.perfectAngleToCollide(this, target, 100);
                        //calls fire at angle
                        fire((float) (fireangle + (-(1 / getAccuracy()) * (Math.PI / 32) + r.nextFloat() * (2 * (1 / getAccuracy()) * (Math.PI / 32)))), getDamage());
                    }
                }
                //******************************
            } else {
                //PATROL**********************
                setAccelerating(false);
                this.dodging = 0;
                //TODO: Pursue for a bit if had a previous target, then stop moving
                //****************************
            }
        }
        super.act(deltaTime);
    }

    /**
     * Checks whether the target passed to it is a good target to choose and can actually be chosen
     *
     * @param optionalLivingEntity
     * @return true if valid target, false otherwise
     */
    private boolean validTarget(Optional<LivingEntity> optionalLivingEntity) {
        if (optionalLivingEntity.isPresent()) {
            LivingEntity livingEntity = optionalLivingEntity.get();
            if (!(livingEntity.isDying() || livingEntity.isDead())) {
                if (livingEntity.distanceFrom(this) <= range) {
                    //if last target exists, not dead and is still in range
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Knits together all the target functions to be able to get a correct target in the range of the NPC
     *
     * @return theTarget
     */
    private Optional<LivingEntity> getTarget() {
        if (validTarget(this.lastTarget)) {
            //Gdx.app.debug("Target", "Last");
            return this.lastTarget;
        } else {
            this.lastTarget = Optional.empty();
            if (targetCheck > 4f) {
                //Gdx.app.debug("Target", "Nearest");
                targetCheck = 0f;
                return getNearestTarget();
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * Checks whether the livingEntity is part of the same college/faction
     * However if the NPC is below a certain health it will be scared so will fight targets,
     * usually only happens when player is shooting allied targets so they will fight back
     *
     * @param livingEntity
     * @return true if they are allied, false if not
     */
    private boolean areAllied(LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            if (getAllied().isPresent()) {
                //Implements the if they have low health fight
                if (getHealth() < 3 * getMaxHealth() / 4) {
                    return false;
                }
                return player.getCaptured().contains(getAllied().get());
            }
        } else {
            //must be an NPCBoat
            NPCBoat npcBoat = (NPCBoat) livingEntity;
            if (npcBoat.getAllied().isPresent() && getAllied().isPresent()) {
                return (npcBoat.getAllied().get().equals(getAllied().get()));
            }
        }

        return false;
    }

    /**
     * Is an extension of the getLivingEntitiesInRange() so that it removes the target in the array being passed back of all the entitys in the range of the NPC
     *
     * @param target
     * @return Array of all livingEntitys in the range of NPC - itself and target
     */
    private Array<LivingEntity> getLivingEntitiesInRangeMinusTarget(LivingEntity target) {
        Array<LivingEntity> nearby = getLivingEntitiesInRange();
        if (nearby.contains(target, false)) {
            nearby.removeValue(target, false);
        }
        return nearby;
    }

    /**
     * returns all livingEntities in the range of the NPC except itself
     *
     * @return Array of livingEntities in range of NPC - itself
     */
    private Array<LivingEntity> getLivingEntitiesInRange() {
        Array<LivingEntity> nearby = GameScreen.getInstance().getEntityManager().getLivingEntitiesInArea(getRangeArea());
        if (nearby.contains(this, false)) {
            nearby.removeValue(this, false);
        }
        return nearby;
    }

    /**
     * Gets all projectiles in range of NPC
     *
     * @return Array of all projectiles in the range of the NPC
     */
    private Array<Projectile> getProjectilesInRange() {
        Array<Projectile> nearby = GameScreen.getInstance().getEntityManager().getProjectileManager().getProjectileInArea(getRangeArea());
        return nearby;
    }

    /**
     * Returns an array of all the projectiles that are going to collide with the NPC out of the ones that have been passed to the function
     *
     * @param projectiles (All projectiles that want to be checked)
     * @return An array of projectiles that will collide with the NPC from the Array given to this function
     */
    private Array<Entity> getProjectilesToDodge(Array<Projectile> projectiles) {
        Array<Entity> projectilesToDodge = new Array<Entity>();
        for (Projectile projectile : projectiles) {
            //Checkout NPC Functions 2 but rather than the source being a NPC and the target being the target, the AI is now the target and the source is the projectile on the loop of iteration
            float thetaToThisInFuture = AIUtil.perfectAngleToCollide(projectile, this, projectile.getSpeed());
            float thetaActual = AIUtil.normalizeAngle(projectile.getAngle());
            float dist = (float) projectile.distanceFrom(this);
            boolean isTriangle = true;
            float theta;
            if (thetaToThisInFuture < thetaActual && thetaActual - thetaToThisInFuture < Math.PI / 2) {
                theta = thetaActual - thetaToThisInFuture;
            } else if (thetaActual < thetaToThisInFuture && thetaToThisInFuture - thetaActual < Math.PI / 2) {
                theta = thetaToThisInFuture - thetaActual;
            } else if (thetaActual < thetaToThisInFuture && (2 * Math.PI - thetaToThisInFuture) + thetaActual < Math.PI / 2) {
                theta = (float) (2 * Math.PI - thetaToThisInFuture) + thetaActual;
            } else if (thetaToThisInFuture < thetaActual && (2 * Math.PI - thetaActual) + thetaToThisInFuture < Math.PI / 2) {
                theta = (float) (2 * Math.PI - thetaActual) + thetaToThisInFuture;
            } else {
                theta = 0;
                isTriangle = false;
            }

            //Check out NPC Functions 1
            if (isTriangle == true) {
                float opp = (float) Math.tan(theta) * dist;
                if (opp < 0) {
                    opp = -opp;
                }
                if (opp < Math.max(3 * this.getRectBounds().height / 4, 3 * this.getRectBounds().width / 4)) {
                    projectilesToDodge.add(projectile);
                }
            }

        }
        return projectilesToDodge;
    }

    /**
     * Used in conjunction with the target selection stuff to be able to pick the nearest target to the NPC
     * Tries to always pick player
     *
     * @return the nearest target
     */
    private Optional<LivingEntity> getNearestTarget() {
        Player player = GameScreen.getInstance().getEntityManager().getOrCreatePlayer();
        Array<LivingEntity> nearby = getLivingEntitiesInRange();
        if (!areAllied(player)) {
            //not allied - target player
            if (nearby.contains(player, false)) {
                //if player is in range - target
                //Gdx.app.debug("NPCBoat", "Got nearby player");

                return Optional.of(player);
            }
        }
        //player has captured this NPCs allied college
        if (nearby.size > 0) {
            Optional<LivingEntity> nearest = Optional.empty();
            for (LivingEntity livingEntity : nearby) {
                if (!areAllied(livingEntity)) {
                    if (nearest.isPresent()) {
                        if (nearest.get().distanceFrom(this) > livingEntity.distanceFrom(this)) {
                            //closest enemy
                            nearest = Optional.of(livingEntity);
                            //Gdx.app.debug("NPCBoat", "Got closer nearby enemy");

                        }
                    } else {
                        nearest = Optional.of(livingEntity);
                        //Gdx.app.debug("NPCBoat", "Got new nearby enemy");
                    }
                }
            }
            return nearest;
        }
        //Gdx.app.debug("NPCBoat", "No nearby enemy");
        return Optional.empty();
    }

    private Rectangle getRangeArea() {
        Rectangle radius = getRectBounds();
        radius.set(radius.x - range, radius.y - range, radius.width + 2 * range, radius.height + 2 * range);
        return radius;
    }

    /**
     * This is needed to be able to make the AI turn like the player does because it doesn't have a person key up it doesn't know to change angular speed
     * This is needed to be called before any setAngle() made by the npc
     *
     * @param right = true if turning right, else is a left turn this is only used if turning = true
     */
    private void turnPreCalcs(boolean right) {
        if (this.previousTurn == true && right == false || this.previousTurn == false && right == true || this.turning == false) {
            setAngularSpeed(0);
        } else {
            if (right) {
                setAngularSpeed(-getTurningSpeed());
            } else {
                setAngularSpeed(getTurningSpeed());
            }
        }
    }

}
