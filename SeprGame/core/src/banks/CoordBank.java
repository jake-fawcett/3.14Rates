package banks;

/**
 * A bank of coordinates storing the position of rooms for the graphics system.
 */
public enum CoordBank {

    FRIENDLY_ROOM1(100,256),
    FRIENDLY_ROOM2(228,256),
    FRIENDLY_ROOM3(100,384),
    FRIENDLY_ROOM4(228,384),
    FRIENDLY_ROOM5(100,512),
    FRIENDLY_ROOM6(228,512),
    FRIENDLY_ROOM7(100,640),
    FRIENDLY_ROOM8(228,640);

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
