package sprites;

import characters.Player;

/**
 * Created by Matthew on 8/13/2016.
 */
public class PlayerSprite extends Sprite {

    private int playerSpeed = 3;
    private Player player;

    public PlayerSprite(int x, int y, Player player) {
        super(x, y);
        this.player = player;
        initPlayer();
    }

    private void initPlayer() {
        isObstacle = false;
        setImage("file:Images\\Player\\PlayerSouth.png");
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int speed) {
        this.playerSpeed = speed;
    }

}
