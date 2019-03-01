package uk.ac.york.sepr4;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.york.sepr4.utils.ShapeUtil;

public class ShapeUtilTest {

    @Test
    public void overlapTest() {
        float[] points = {1,1, 2,1, 2,2, 1,2};
        Polygon polygon = new Polygon(points);
        Rectangle rectangle = new Rectangle(0,0, 2,2);

        Assert.assertTrue(ShapeUtil.overlap(polygon, rectangle));
    }
}
