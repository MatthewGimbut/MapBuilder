package sprites;

public class GenericObstacle extends Sprite {

	public GenericObstacle(int x, int y) {
		super(x, y);
		isObstacle = true;
		setImage("file:Images\\TreeArea.png");
	}
	
	public GenericObstacle(int x, int y, String imageLocation) {
		super(x, y);
		isObstacle = true;
		setImage(imageLocation);
	}

}
