package uk.ac.york.sepr4.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import uk.ac.york.sepr4.utils.ShapeUtil;

import java.util.*;

public class PirateMap {

    @Getter
    private TiledMap tiledMap;

    private final String objectLayerName = "objects";
    private final String spawnPointObject = "spawn";

    private MapLayer objectLayer;

    @Getter
    private Vector2 spawnPoint;

    @Getter
    private boolean objectsEnabled;

    @Getter
    private List<Polygon> collisionObjects;

    public PirateMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.collisionObjects = new ArrayList<>();

        if (checkObjectLayer()) {
            setCollisionObjects();
            this.objectsEnabled = true;
        } else {
            Gdx.app.error("Pirate Map", "Map does NOT contain object layer!");
            this.objectsEnabled = false;
        }

    }

    public Vector2 getSpawnPoint() {
        if (isObjectsEnabled()) {
            return spawnPoint;
        } else {
            return new Vector2(50, 50);
        }
    }

    public boolean isColliding(Rectangle rectangle) {
        for(Polygon polygon : collisionObjects) {
            if(ShapeUtil.overlap(polygon, rectangle)){
                return true;
            }
        }
        return false;
    }

    private void setCollisionObjects() {
        for (MapLayer mapLayer : tiledMap.getLayers()) {

            if (mapLayer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer) mapLayer;

                //scan across
                for (int x = 0; x <= tileLayer.getWidth(); x++) {
                    //scan up
                    for (int y = 0; y <= tileLayer.getHeight(); y++) {
                        TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                        if (cell != null) {
                            TiledMapTile tile = tileLayer.getCell(x, y).getTile();
                            if (tile.getObjects() != null) {

                                Iterator<MapObject> iterator = tile.getObjects().iterator();
                                while (iterator.hasNext()) {
                                    MapObject mapObject = iterator.next();
                                    if (mapObject instanceof PolygonMapObject) {
                                        PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                                        Polygon oldPoly = polygonMapObject.getPolygon();
                                        Polygon polygon = new Polygon();
                                        polygon.setVertices(oldPoly.getVertices());
                                        polygon.setOrigin(oldPoly.getOriginX(), oldPoly.getOriginY());
                                        polygon.setPosition((x+(oldPoly.getX()/64))*32f, (y+(oldPoly.getY())/64)*32f);
                                        //TODO: LibGDX fault. LoadObject does not correctly get tile rotation.
                                        //Rotation value is always 0 (even on rotated tiles).
                                        //Simple replace with non-rotated tiles in map editor.
                                        polygon.setRotation(polygonMapObject.getPolygon().getRotation());
                                        polygon.setScale(1/2f, 1/2f);
                                        collisionObjects.add(polygon);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        Gdx.app.log("PirateMap", "Loaded " + this.collisionObjects.size() + " collision objects!");
    }

    private boolean checkObjectLayer() {
        this.objectLayer = tiledMap.getLayers().get(objectLayerName);
        if (this.objectLayer != null) {
            return setSpawnObject();
        }
        return false;
    }

    /***
     * Gets a map (not collision) object with the specified name.
     * @param objectName
     * @return
     */
    public Optional<RectangleMapObject> getMapObject(String objectName) {
        try {
            MapObject mapObject = objectLayer.getObjects().get(objectName);
            if (mapObject instanceof RectangleMapObject) {
                return Optional.of((RectangleMapObject) mapObject);
            }
        } catch (NullPointerException e) {}
        return Optional.empty();
    }

    private boolean setSpawnObject() {
        MapObject mapObject = objectLayer.getObjects().get(spawnPointObject);
        if (mapObject != null && mapObject instanceof RectangleMapObject) {
            RectangleMapObject object = (RectangleMapObject) mapObject;
            this.spawnPoint = scaleTiledVectorToMap(new Vector2(object.getRectangle().x, object.getRectangle().y));
            return true;
        }
        return false;
    }

    public Vector2 scaleTiledVectorToMap(Vector2 tiledVector) {
        return tiledVector.scl(1 / 2f);
    }

}
