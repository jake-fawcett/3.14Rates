package uk.ac.york.sepr4.ahod2.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/***
 * Class responsible for holding static assets (images, sprites, etc).
 */
public class FileManager {

    public static Texture defaultShipTexture = new Texture(Gdx.files.internal("ships/default.png"));

    public static Texture sailScreenBG = new Texture(Gdx.files.internal("images/screen/sail5.png"));
    public static Texture menuScreenBG = new Texture(Gdx.files.internal("images/screen/menu.png"));
    public static Texture battleScreenBG = new Texture(Gdx.files.internal("images/screen/battle.png"));

    public static Texture nodeIcon = new Texture(Gdx.files.internal("images/node/default.png"));
    public static Texture collegeNodeIcon = new Texture(Gdx.files.internal("images/node/college.png"));
    public static Texture startNodeIcon = new Texture(Gdx.files.internal("images/node/start.png"));
    public static Texture randEncounterIcon = new Texture(Gdx.files.internal("images/node/encounter.png"));
    public static Texture battleNodeIcon = new Texture(Gdx.files.internal("images/node/battle.png"));
    public static Texture departmentNodeIcon = new Texture(Gdx.files.internal("images/node/department.png"));
    public static Texture seamonsterNodeIcon = new Texture(Gdx.files.internal("images/node/seamonster.png"));

    public static Texture hudShipView = new Texture(Gdx.files.internal("images/hud/shipview.png"));

    public static Drawable minigameDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("images/card/reload.png")));
    public static Drawable minigameBackDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("images/card/back.png")));


}
