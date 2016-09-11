package sprites;

public class NPC extends Sprite {
	
	private characters.Character chara;

	public NPC(int x, int y, characters.Character chara, String[] message) {
		super(x, y);
		this.chara = chara;
		this.message = message;
		initNPC();
	}

    private void initNPC() {
    	isObstacle = true;
        setImage(chara.getImage());
    }
 
    public characters.Character getNPC() {
    	return chara;
    }

	public void setCurrentImage(String imageLoc) {
		setImage(imageLoc);
	}
}
