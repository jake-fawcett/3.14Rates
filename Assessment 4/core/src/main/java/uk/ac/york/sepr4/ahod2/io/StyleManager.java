package uk.ac.york.sepr4.ahod2.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/***
 * Class responsible for generating styles for on-screen elements such as Labels, TextButtons, etc.
 */
public class StyleManager {

    public static final FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("font/PirataOne-Regular.ttf"));

    /***
     * Generates a BitmapFont of specified size and colour.
     * @param size specified size
     * @param color specified colour
     * @return instance of BitmapFont
     */
    public static BitmapFont generatePirateFont(Integer size, Color color) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;

        return generator.generateFont(parameter);
    }

    /***
     * Generates a LabelStyle of specified size and colour.
     * @param size specified size
     * @param color specified colour
     * @return instance of LabelStyle
     */
    public static Label.LabelStyle generateLabelStyle(Integer size, Color color) {
        return new Label.LabelStyle(generatePirateFont(size, color), color);
    }

    /***
     * Generates a TextButtonStyle of specified size and colours.
     * @param size specified size
     * @param colorUp specified colour of button
     * @param colorDown specified colour of button when pressed
     * @return instance of TextButtonStyle
     */
    public static TextButton.TextButtonStyle generateTBStyle(Integer size, Color colorUp, Color colorDown) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = generatePirateFont(size, colorUp);
        style.fontColor = colorUp;
        style.downFontColor = colorDown;

        return style;
    }

}
