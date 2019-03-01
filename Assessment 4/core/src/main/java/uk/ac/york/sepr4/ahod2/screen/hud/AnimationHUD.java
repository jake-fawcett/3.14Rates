package uk.ac.york.sepr4.ahod2.screen.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.Getter;
import uk.ac.york.sepr4.ahod2.io.StyleManager;

import java.util.ArrayList;
import java.util.List;

/***
 * Class used to load and update on-screen animation elements such as damage and heal splats.
 */
public class AnimationHUD {
    @Getter
    private Stage animationsStage;
    private List<Animation> animations = new ArrayList<>();

    public AnimationHUD() {
        // Local widths and heights.
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        animationsStage = new Stage(new FitViewport(w, h, new OrthographicCamera()));
    }

    /***
     * Create label animation for a damage splat of specified value and duration.
     * @param coords location for animation
     * @param value damage value to display
     * @param time duration of animation
     */
    public void addDamageAnimation(Vector2 coords, Integer value, Float time) {
        Label dmgLabel = new Label("-" + value, StyleManager.generateLabelStyle(45, Color.RED));
        dmgLabel.setPosition(coords.x, coords.y);
        animations.add(new Animation(dmgLabel, time));
    }

    /***
     * Create label animation for a heal splat of specified value and duration.
     * @param coords location for animation
     * @param value heal value to display
     * @param time duration of animation
     */
    public void addHealAnimation(Vector2 coords, Integer value, Float time) {
        Label healLabel = new Label("+" + value, StyleManager.generateLabelStyle(45, Color.GREEN));
        healLabel.setPosition(coords.x, coords.y);
        animations.add(new Animation(healLabel, time));
    }

    /***
     * Update animations.
     * Remove animations if delta exceeds remaining time.
     * Add new animations to stage.
     * @param delta time since last update
     */
    public void update(float delta) {
        List<Animation> toRemove = new ArrayList<>();
        for (Animation animation : animations) {
            //if animation time is over, add to remove list
            if (animation.getTime() <= delta) {
                toRemove.add(animation);
                animationsStage.getActors().removeValue(animation.getActor(), false);
                continue;
            }
            if (!animationsStage.getActors().contains(animation.getActor(), false)) {
                //add animation actor to stage if not exists
                animationsStage.addActor(animation.getActor());
            }
            animation.setTime(animation.getTime() - delta);
        }

        //remove finished animations (avoid ConcurrentModificationException)
        animations.removeAll(toRemove);

        animationsStage.act();
        animationsStage.draw();
    }
}
