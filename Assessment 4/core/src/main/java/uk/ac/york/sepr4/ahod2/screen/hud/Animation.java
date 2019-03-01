package uk.ac.york.sepr4.ahod2.screen.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Data;

@Data
public class Animation {

    private Actor actor;
    private Float time;

    public Animation(Actor actor, Float time) {
        this.actor = actor;
        this.time = time;
    }

}