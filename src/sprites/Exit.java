package sprites;

public class Exit extends Sprite {

    private String nextMapLocation;
    private int nextX;
    private int nextY;
    private Cardinal placement;

    public Exit(int x, int y, int nextX, int nextY, Cardinal placement, String nextMapLocation) {
        super(x, y);
        this.nextX = nextX;
        this.nextY = nextY;
        this.nextMapLocation = nextMapLocation;
        this.placement = placement;
        initExit();
    }

    private void initExit() {
        isObstacle = true;
        setImage("file:Images\\Exit.png");
    }

    public Cardinal getPlacement() { return placement; }

    public String getNextMapLocation() {
        return nextMapLocation;
    }

    public void setNextMapLocation(String nextMapLocation) {
        this.nextMapLocation = nextMapLocation;
    }

    public int getNextX() {
        return nextX;
    }

    public void setNextX(int nextX) {
        this.nextX = nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void setNextY(int nextY) {
        this.nextY = nextY;
    }

}