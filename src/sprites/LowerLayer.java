package sprites;

public class LowerLayer extends Sprite {

    public LowerLayer(int x, int y, String image) {
        super(x, y);
        isObstacle = false;
        setImage(image);
    }

}
