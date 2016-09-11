package sprites;

public class Save extends Sprite {
	public Save(int x, int y) {
		super(x, y);
		isObstacle = true;
		setImage("file:Images\\SaveArea.png");
	}
}
