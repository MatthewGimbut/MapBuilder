package sprites;

import items.Item;

import java.util.LinkedList;


public class Lootable extends Sprite {

	private LinkedList<Item> items;
	
	
	public Lootable(int x, int y) {
		super(x, y);
		isObstacle = true;
		setImage("file:Images\\Lootables\\ChestArea.png");
		items = Item.generateRandomItem(4);
	}

	public Lootable(int x, int y, String imageLocation, LinkedList<Item> items) {
        super(x, y);
        isObstacle = true;
		setImage(imageLocation);
        this.items = items;
    }

	public Lootable(int x, int y, LinkedList<Item> items) {
		super(x, y);
        isObstacle = true;
		setImage("file:Images\\Lootables\\ChestArea.png");
		this.items = items;
	}


	public void add(Item i) {
		items.add(i);
	}

	/**
	 * Gets the items in the chest
	 * @return A LinkedList<Item> containing the items in the chest
	 */
	public LinkedList<Item> getItems() {
		return items;
	}	
	
	/**
	 * Removes all items from a chest
	 */
	public void removeAllItems() {
		this.items.clear();
	}

}
