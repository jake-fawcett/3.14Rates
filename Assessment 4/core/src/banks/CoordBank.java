package banks;

/**
 * A bank of coordinates storing the position of rooms for the graphics system.
 */
public enum CoordBank {

    FRIENDLY_CREWQUATERS((1920*100)/1024, (1080*256)/1024),
    FRIENDLY_EMPTYROOM1((1920*228)/1024, (1080*256)/1024),
    FRIENDLY_CROWSNEST((1920*100)/1024, (1080*384)/1024),
    FRIENDLY_GUNDECK((1920*228)/1024, (1080*384)/1024),
    FRIENDLY_EMPTYROOM2((1920*100)/1024, (1080*512)/1024),
    FRIENDLY_HELM((1920*228)/1024, (1080*512)/1024),
    FRIENDLY_EMPTYROOM3((1920*100)/1024, (1080*640)/1024),
    FRIENDLY_EMPTYROOM4((1920*228)/1024, (1080*640)/1024),
    ENEMY_CREWQUATERS((1920*700)/1024, (1080*256)/1024),
    ENEMY_EMPTYROOM1((1920*828)/1024, (1080*256)/1024),
    ENEMY_CROWSNEST((1920*700)/1024, (1080*384)/1024),
    ENEMY_EMPTYROOM2((1920*828)/1024, (1080*384)/1024),
    ENEMY_GUNDECK((1920*700)/1024, (1080*512)/1024),
    ENEMY_EMPTYROOM3((1920*828)/1024, (1080*512)/1024),
    ENEMY_EMPTYROOM4((1920*700)/1024, (1080*640)/1024),
    ENEMY_HELM((1920*828)/1024, (1080*640)/1024);


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
