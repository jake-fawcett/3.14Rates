package uk.ac.york.sepr4.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Getter;
import uk.ac.york.sepr4.object.entity.LivingEntity;

public class HealthBar extends ProgressBar {

    @Getter
    private LivingEntity livingEntity;

    /**
     * Class which handles health bars above living entities
     */
    public HealthBar(LivingEntity livingEntity) {
    //Changed for Assessment 3: improved resolution on HealthBar to accomodate higher health enemies
        super(0f, 1f, 0.001f, false, new ProgressBarStyle());
        this.livingEntity = livingEntity;


        Integer height = 5;
        Integer width = Math.round(livingEntity.getWidth());

        getStyle().background = getColoredDrawable(width, height, Color.RED);
        getStyle().knob = getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = getColoredDrawable(width, height, Color.GREEN);

        setWidth(livingEntity.getWidth());
        setHeight(height);

        setAnimateDuration(0.0f);
        update();

        setAnimateDuration(0.25f);
    }

    public void update() {
        setX(livingEntity.getX());
        //just above
        setY(livingEntity.getY() + livingEntity.getTexture().getHeight());
        setValue((float)(
                livingEntity.getHealth()
                        /
                        livingEntity.getMaxHealth()));
    }

    /**
     * Creates an image of determined size filled with determined color.
     *
     * @param width of an image.
     * @param height of an image.
     * @param color of an image fill.
     * @return {@link Drawable} of determined size filled with determined color.
     */
    private static Drawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }
}
