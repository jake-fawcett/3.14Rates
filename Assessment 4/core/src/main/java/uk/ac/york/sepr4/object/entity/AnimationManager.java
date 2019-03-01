package uk.ac.york.sepr4.object.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import uk.ac.york.sepr4.TextureManager;
import uk.ac.york.sepr4.utils.AIUtil;

import java.util.*;

public class AnimationManager {

    private EntityManager entityManager;

    //For cleanup
    private Array<Entity> lastFrameEffects = new Array<>(); //Needed for clean up
    private HashMap<LivingEntity, Float> deathAnimations = new HashMap<>();

    //Death Animations
    private Array<Entity> effects = new Array<>();
    //Water Trails
    private List<WaterTrail> waterTrails = new ArrayList<>();
    //Cannon "boom" animation
    private List<CannonExplosion> cannonExplosions = new ArrayList<>();

    public AnimationManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Takes the centre x,y of where you want the effect to appear
    public void addEffect(float x, float y, float angle, Texture texture, int width, int height, float alpha){
        Entity effect = new Entity(texture, new Vector2(x,y)) {};
        effect.setY(y - height/2);
        effect.setX(x - width/2);
        effect.setWidth(width);
        effect.setHeight(height);
        effect.setAlpha(alpha);
        effect.setAngle(angle);
        this.effects.add(effect);
    }

    /**
     * Removes all effects then adds all new effects
     * Effects work on a frame by frame basis so need to be spawned in every frame
     */
    public void handleEffects(Stage stage, float delta) {
        handleDeathAnimations(delta);
        updateWaterTrails();
        updateFiringAnimations();
        stage.getActors().removeAll(this.lastFrameEffects, true);

        for (Entity effect : effects) {
            if (!stage.getActors().contains(effect, true)) {
                stage.addActor(effect);
            }
        }

        this.lastFrameEffects = this.effects;
        this.effects = new Array<>();
    }

    public void addFiringAnimation(LivingEntity livingEntity, float firingAngle) {
        cannonExplosions.add(new CannonExplosion(livingEntity, firingAngle));
    }

    private void updateFiringAnimations() {
        List<CannonExplosion> toRemove = new ArrayList<>();
        for(CannonExplosion cannonExplosion: cannonExplosions) {
            if(cannonExplosion.isComplete()) {
                toRemove.add(cannonExplosion);
            } else {
                cannonExplosion.spawnEffects(this);
            }
        }
    }

    //TODO: Could be cleaned up
    //Water Trails
    private void updateWaterTrails() {
        List<WaterTrail> toRemove = new ArrayList<>();
        for(WaterTrail waterTrail : waterTrails) {
            if(waterTrail.getLE() instanceof NPCBoat) {
                //remove dead NPCs trail
                if(!entityManager.getNpcList().contains((NPCBoat) waterTrail.getLE(), false)){
                    toRemove.add(waterTrail);
                } else {
                    //if not dead, update effects
                    waterTrail.spawnEffects(this);
                }
            } else if(waterTrail.getLE() instanceof Player) {
                //remove dead players trail
                if(entityManager.getOrCreatePlayer().isDead()) {
                    toRemove.add(waterTrail);
                } else {
                    //if not dead, update effects
                    waterTrail.spawnEffects(this);
                }
            } else {
                Gdx.app.error("AnimationManager", "Trail found for unknown LE");
                toRemove.add(waterTrail);
            }
        }
        waterTrails.removeAll(toRemove);
    }

    public void createWaterTrail(LivingEntity livingEntity) {
        waterTrails.add(new WaterTrail(livingEntity));
    }
    


    //Death Animations
    //TODO: Cleanup like other animations
    private void handleDeathAnimations(float delta) {
        //add dying npcs if they arent already in there
        for(LivingEntity livingEntity : entityManager.getLivingEntities()) {
            if(livingEntity.isDying() && !deathAnimations.containsKey(livingEntity)) {
                deathAnimations.put(livingEntity, 0f);
            }
        }
        for(Iterator<Map.Entry<LivingEntity, Float>> it = deathAnimations.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<LivingEntity, Float> entry = it.next();
            LivingEntity livingEntity = entry.getKey();
            Float deathTimer = entry.getValue();
            livingEntity.setTexture(TextureManager.DEADENEMY);
            livingEntity.setAlpha(1-(deathTimer/5));
            if (deathTimer < 1/6f) {
                addEffect(livingEntity.getCentre().x, livingEntity.getCentre().y,
                        livingEntity.getAngle(), TextureManager.EXPLOSION1, 40, 40,1);
            } else if (deathTimer < 2/6f) {
                addEffect(livingEntity.getCentre().x, livingEntity.getCentre().y,
                        livingEntity.getAngle(), TextureManager.EXPLOSION2, 40, 40, 1);
            } else if (deathTimer < 1/2f){
                addEffect(livingEntity.getCentre().x, livingEntity.getCentre().y,
                        livingEntity.getAngle(), TextureManager.EXPLOSION3, 40, 40,1);
            }
            if (deathTimer > 5){
                livingEntity.setDead(true);
                livingEntity.setDying(false);
                it.remove();
            } else {
                entry.setValue(entry.getValue()+delta);
            }
        }
    }



}

class CannonExplosion {
    private LivingEntity lE;
    private Float firingAngle;
    private int frame = 1;

    public CannonExplosion(LivingEntity lE, float firingAngle) {
        this.lE = lE;
        this.firingAngle = firingAngle;
    }

    public boolean isComplete() {
        return (frame==21);
    }

    public void spawnEffects(AnimationManager animationManager) {
        animationManager.addEffect(AIUtil.getXwithAngleandDistance(lE.getCentre().x,
                firingAngle + (float)Math.PI/2, 50),
                AIUtil.getYwithAngleandDistance(lE.getCentre().y,
                        firingAngle + (float)Math.PI/2, 50),
                firingAngle, TextureManager.firingFrame(frame),
                70, 50, 1);
        frame++;
    }
}

class WaterTrail {
    private List<Vector2> lTrails = new ArrayList<>(), rTrails = new ArrayList<>();
    @Getter
    private LivingEntity lE;
    
    public WaterTrail(LivingEntity livingEntity) {
        this.lE = livingEntity;
    }
    
    public void spawnEffects(AnimationManager animationManager) {
        shiftTrails();
        lTrails.add(new Vector2(AIUtil.getXwithAngleandDistance(lE.getCentre().x, (float) (lE.getAngle() - 7 * Math.PI / 8), 50f),
                AIUtil.getYwithAngleandDistance(lE.getCentre().y, (float) (lE.getAngle() - 7 * Math.PI / 8), 45f)));
        rTrails.add(new Vector2(AIUtil.getXwithAngleandDistance(lE.getCentre().x, (float) (lE.getAngle() + 7 * Math.PI / 8), 50f),
                AIUtil.getYwithAngleandDistance(lE.getCentre().y, (float) (lE.getAngle() + 7 * Math.PI / 8), 45f)));


        for (int i = 0; i < lTrails.size() - 1; i++) {
            float xM = getXmidPoint(lTrails.get(i).x, lTrails.get(i + 1).x);
            float yM = getYmidPoint(lTrails.get(i).y, lTrails.get(i + 1).y);
            float angleP = getAngleToPoint(lTrails.get(i).x, lTrails.get(i).y, lTrails.get(i + 1).x, lTrails.get(i + 1).y) + (float)Math.PI/2;
            float distance = getDistanceToPoint(lTrails.get(i).x, lTrails.get(i).y, lTrails.get(i + 1).x, lTrails.get(i + 1).y);

            float xM2 = getXmidPoint(rTrails.get(i).x, rTrails.get(i + 1).x);
            float yM2 = getYmidPoint(rTrails.get(i).y, rTrails.get(i + 1).y);
            float angleP2 = getAngleToPoint(rTrails.get(i).x, rTrails.get(i).y, rTrails.get(i + 1).x, rTrails.get(i + 1).y) + (float)Math.PI/2;
            float distance2 = getDistanceToPoint(rTrails.get(i).x, rTrails.get(i).y, rTrails.get(i + 1).x, rTrails.get(i + 1).y);


            if (distance > 0.1) {
                if (i < lTrails.size() / 4) {
                    animationManager.addEffect(xM, yM, angleP, TextureManager.MIDDLEBOATTRAIL1, (int)(distance + 5), 10,0.1f);
                    animationManager.addEffect(xM2, yM2, angleP2,  TextureManager.MIDDLEBOATTRAIL1, (int)(distance2 + 5), 10,0.1f);
                } else if (i < lTrails.size() / 2) {
                    animationManager.addEffect(xM, yM, angleP,  TextureManager.MIDDLEBOATTRAIL1, (int)(distance + 5), 10,0.2f);
                    animationManager.addEffect(xM2, yM2, angleP2,  TextureManager.MIDDLEBOATTRAIL1, (int)(distance2 + 5), 10,0.2f);
                } else if (i < 3 * lTrails.size() / 4) {
                    animationManager.addEffect(xM, yM, angleP,  TextureManager.MIDDLEBOATTRAIL1, (int)(distance + 5), 10,0.3f);
                    animationManager.addEffect(xM2, yM2, angleP2,  TextureManager.MIDDLEBOATTRAIL1, (int)(distance2 + 5), 10,0.3f);
                } else {
                    animationManager.addEffect(xM, yM, angleP,  TextureManager.MIDDLEBOATTRAIL1, (int)(distance + 5), 10,0.5f);
                    animationManager.addEffect(xM2, yM2, angleP2, TextureManager.MIDDLEBOATTRAIL1, (int)(distance2 + 5), 10,0.5f);
                }
            }
        }

    }

    private float getXmidPoint(float x1, float x2) {
        if (x2 > x1){
            return (x1+(x2-x1)/2);
        } else {
            return (x1-(x2-x1)/2);
        }
    }

    private float getYmidPoint(float y1, float y2) {
        if (y2 > y1){
            return (y1+(y2-y1)/2);
        } else {
            return (y1-(y2-y1)/2);
        }
    }

    private float getAngleToPoint(float x1, float y1, float x2, float y2) {
        double d_angle = Math.atan(((y2 - y1) / (x2 - x1)));
        if(x2 < x1){
            d_angle += Math.PI;
        }
        float angle = (float)d_angle + (float)Math.PI/2;
        return angle;
    }

    private float getDistanceToPoint(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    private void shiftTrails() {
        if(lTrails.size() >= 60) {
            lTrails.remove(0);
            rTrails.remove(0);
        }
    }
}