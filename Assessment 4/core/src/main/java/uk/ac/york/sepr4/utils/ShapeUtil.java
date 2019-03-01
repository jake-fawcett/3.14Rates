package uk.ac.york.sepr4.utils;


import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class ShapeUtil {

    /***
     * Checks whether a polygon and rectangle overlap.
     * Used for checking collisions.
     * @param polygon
     * @param rectangle
     * @return true if polygon and rectangle overlap
     */
    public static boolean overlap(Polygon polygon, Rectangle rectangle) {
        float[] points = polygon.getTransformedVertices();
        for(int i=0;i<points.length;i+=2) {
            float x = points[i], y = points[i+1];
            if(rectangle.contains(x, y)){
                return true;
            }
        }
        return false;
    }

}
