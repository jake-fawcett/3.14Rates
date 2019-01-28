package banks;

/**
 * A bank of coordinates storing the position of rooms for the graphics system.
 */
public enum CoordBank {

    FRIENDLY_CREWQUATERS(100,256),
    FRIENDLY_EMPTYROOM1(228,256),
    FRIENDLY_CROWSNEST(100,384),
    FRIENDLY_GUNDECK(228,384),
    FRIENDLY_EMPTYROOM2(100,512),
    FRIENDLY_HELM(228,512),
    FRIENDLY_EMPTYROOM3(100,640),
    FRIENDLY_EMPTYROOM4(228,640),
    ENEMY_CREWQUATERS(700, 256),
    ENEMY_EMPTYROOM1(828, 256),
    ENEMY_CROWSNEST(700, 384),
    ENEMY_EMPTYROOM2(828, 384),
    ENEMY_GUNDECK(700, 512),
    ENEMY_EMPTYROOM3(828, 512),
    ENEMY_EMPTYROOM4(700, 640),
    ENEMY_HELM(828, 640);


    // Internal workings of the enum
    private int x;
    private int y;

    // Internal workings of the enum
    CoordBank(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x coordinate of the room.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y coordinate of the room.
     */
    public int getY() {
        return y;
    }

}
