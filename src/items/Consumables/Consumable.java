package items.Consumables;

import items.Item;
import items.Rarity;

public abstract class Consumable extends Item {

	public Consumable(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare);
	}	
	
}
