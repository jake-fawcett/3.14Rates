package uk.ac.york.sepr4.object.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import uk.ac.york.sepr4.object.PirateMap;

import java.util.Optional;

@Data
public abstract class Building {

    private String name;
    private String mapObjectStr;
    private Vector2 mapLocation;
    private Float buildingRange = 1500f;

    public boolean load(PirateMap pirateMap) {
        Optional<RectangleMapObject> objectOptional = pirateMap.getMapObject(mapObjectStr);
        if(objectOptional.isPresent()) {
            RectangleMapObject rect = objectOptional.get();
            this.mapLocation = pirateMap.scaleTiledVectorToMap(new Vector2(rect.getRectangle().x, rect.getRectangle().y));
            return true;
        } else {
            Gdx.app.log("Building", "Could not load map object for building: " + name);
        }
        return false;
    }

    public Rectangle getBuildingZone() {
        Vector2 pos = getMapLocation();
        return new Rectangle(pos.x-(getBuildingRange()/2), pos.y-(getBuildingRange()/2), getBuildingRange(), getBuildingRange());
    }
}
