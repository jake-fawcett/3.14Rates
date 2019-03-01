package uk.ac.york.sepr4.object.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import uk.ac.york.sepr4.*;
import uk.ac.york.sepr4.TextureManager;
import uk.ac.york.sepr4.hud.HealthBar;
import uk.ac.york.sepr4.object.building.College;
import uk.ac.york.sepr4.object.item.Item;
import uk.ac.york.sepr4.object.item.Reward;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player extends LivingEntity implements InputProcessor {

    private Integer balance = 0, xp = 0, level = 1;
    private List<Item> inventory = new ArrayList<>();

    private List<College> captured = new ArrayList<>();
    private boolean turningLeft, turningRight, tripleShot = false;
    private double bulletDamage = 5;


    public Player(Vector2 pos) {
        super(TextureManager.PLAYER, pos);
        //face up
        setAngle((float)Math.PI);

        //testing
        //setMaxHealth(1000.0);
        //setHealth(1000.0);

	//Changed for Assessment 3: only default values instead of computations in the constructor
        setMaxHealth(20.0);
        setHealth(getMaxHealth());
        setMaxSpeed(100f);
        setDamage(0.5);
    }

    @Override
    public void act(float deltaTime) {
        if(!isDying() && !isDead() && !GameScreen.isPaused()) {
            float angle = getAngle();
            float angularSpeed = 0;
	    //Changed for Assessment 3: improved responsiveness on turning functions
            if (turningLeft) {
                angularSpeed += getTurningSpeed();
            }
            if (turningRight) {
                angularSpeed -= getTurningSpeed();
            }
            angle += ((angularSpeed * deltaTime) * (getSpeed() / getMaxSpeed())) % (float) (2 * Math.PI);
            setAngle(angle);
            super.act(deltaTime);
        }
    }

    public void capture(College college) {
        captured.add(college);
        Gdx.app.debug("Player", "Captured "+college.getName());
    }

    //Changed for Assessment 3: removed unused functions returning level progress
    /**
     * Compute whether the player will level up and give rewards if true
     * @return The level of the player
     */
    public Integer getLevel() {
        if (xp >= (level+1)*10) {
            level += 1;
            xp = 0;
            setMaxHealth(getMaxHealth() + 5);
            setHealth(getMaxHealth());
            updateHealthBar();
            setMaxSpeed(getMaxSpeed() + 20);
            setDamage(getDamage() + 0.1);
        }
        return level;
    }

    public void issueReward(Reward reward) {
        addBalance(reward.getGold());
        addXP(reward.getXp());
        addItems(reward.getItems());
    }

    public void addBalance(Integer val) {
        balance+=val;
    }
    public void addXP(Integer val) {
        xp+=val;
    }
    public void addItems(List<Item> items) {
        inventory.addAll(items);
    }


    //Added for Assessment 3: Allow interaction with shops
    public boolean deduceBalance(int deduction) {
        if(deduction <= balance) {
            balance -= deduction;
            return true;
        }
        return false;
    }

    public void updateHealthBar(){
        setHealthBar(new HealthBar(this));
    }
    //Methods below for taking keyboard input from player.
    @Override
    public boolean keyDown(int keycode) {
        // do nothing if paused
        if (GameScreen.isPaused()) {
            return false;
        }

        if(keycode == Input.Keys.W) {
            setAccelerating(true);
            return true;
        }

        if(keycode == Input.Keys.S) {
            setBraking(true);
            return true;
        }

        if(keycode == Input.Keys.A) {
            // Assessmnent 3 - changed to make turning more responsive
            turningLeft = true;
            return true;
        }

        if(keycode == Input.Keys.D) {
            // Assessmnent 3 - changed to make turning more responsive
            turningRight = true;
            return true;
        }
        if(keycode == Input.Keys.Q) {
            //minimap
            GameScreen.getInstance().getOrthographicCamera().zoom = 3;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W) {
            setAccelerating(false);
            return true;
        }

        if(keycode == Input.Keys.S) {
            setBraking(false);
            return true;
        }

        if(keycode == Input.Keys.A) {
            // Assessmnent 3 - changed to make turning more responsive
            turningLeft = false;
            return true;
        }

        if(keycode == Input.Keys.D) {
            // Assessmnent 3 - changed to make turning more responsive
            // TODO: unexpected behaviour when changing input managers
            turningRight = false;
            return true;
        }
        if(keycode == Input.Keys.Q) {
            //minimap
            GameScreen.getInstance().getOrthographicCamera().zoom = 1;
            return true;
        }
        return false;
    }
    //Added for Assessment 3: enable events that move the player
    public void movePlayer(Vector2 pos){
        this.setX(pos.x);
        this.setY(pos.y);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
