package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Created by Matthew on 8/13/2016.
 */
public class Sprite implements Serializable {

    private transient Image image;
    private int x, y, dx, dy, width, height;
    private boolean visible;
    protected boolean isObstacle;
    protected String[] message;
    private String location;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    public Sprite(int x, int y, String image) {
        this.x = x;
        this.y = y;
        visible = true;
        setImage(image);
        this.location = image;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(int x, int y)
    {
        dx = x;
        dy = y;
    }

    public void setImage(String filename) {
        if(!filename.startsWith("file:")) {
            filename = "file:" + filename;
        }
        this.location = filename;
        Image i = new Image(filename);
        setImage(i);
    }

    public void setImage(Image image) {
        this.image = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBounds().intersects(this.getBounds());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void modifyX(int dx) {
        x += dx;
    }

    public void modifyY(int dy) {
        y += dy;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public Image getImage() {
        return image;
    }

    public String getImageLocation() {
        return this.location;
    }
}
