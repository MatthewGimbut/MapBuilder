package sprites;

public class UpperLayer extends Sprite {

    public UpperLayer(int x, int y, String image) {
        super(x, y);
        isObstacle = false;
        setImage(image);
    }

}
