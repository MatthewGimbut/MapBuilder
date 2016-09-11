package items.Consumables;

import items.Rarity;

public class Potion extends Consumable {

	private PotionType type;
	private int amount;
	//TODO change potion class so that potions don't use hp or mana boosts, add separate stat for all stat boost amounts

	public Potion(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, PotionType type,
				  int amount, String imageLocation) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare);
		this.type = type;
		this.amount = amount;
		this.setImageLocation(imageLocation);
		this.setItemToolTipText("A(n) " + type);
		this.setSimpleName(type.toString());
	}

	public Potion(PotionType type, int amount) {
		super(0, 0, 0, 0, .1, 0, 0, 100, Rarity.COMMON);
		this.type = type;
		this.amount = amount;
		this.setImageLocation("Images\\Consumables\\Potions\\" + type + ".png");
		this.setItemToolTipText("A(n) " + type);
		this.setSimpleName(type.toString());
	}

	public int getAmount() {
		return amount;
	}

	public PotionType getType() {
		return type;
	}
}

