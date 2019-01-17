package banks;

public enum CoordBank {

    FRIENDLY_ROOM1(100,256),
    FRIENDLY_ROOM2(228,256),
    FRIENDLY_ROOM3(100,384),
    FRIENDLY_ROOM4(228,384),
    FRIENDLY_ROOM5(100,512),
    FRIENDLY_ROOM6(228,512),
    FRIENDLY_ROOM7(100,640),
    FRIENDLY_ROOM8(228,640);


    private int x;
    private int y;

    CoordBank(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
