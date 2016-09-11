package sprites;

import items.Item;

public class DisplayItem extends Sprite {

	private Item item;
	
	public DisplayItem(int x, int y, Item item) {
		super(x, y);
		this.item = item;
		initItem();
	}

    private void initItem() {
    	isObstacle = true;
        setImage(item.getImageLocation());
    }

    public Item getItem() {
    	return item;
    }
    
}
