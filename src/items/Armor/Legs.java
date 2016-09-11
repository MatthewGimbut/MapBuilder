package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Legs armor class
 * @author Matthew Gimbut
 *
 */
public class Legs extends Armor {

	public Legs(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, ArmorType armorType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, armorType);
		this.setImageLocation("Images\\Weapons\\" + this.getArmorType().toString() + "\\Axe.png");
		this.setSimpleName(this.getArmorType() + " legs");
		this.setItemToolTipText("A pair of " + this.getArmorType() + " leggings");
	}
	
	public Legs(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				Rarity howRare, ArmorType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Legs() {
		super(0, 0, 5, 2, 5, 0, 0, 75, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Legs.png");
		this.setSimpleName(this.getArmorType() + " legs");
		this.setItemToolTipText("A pair of " + this.getArmorType() + " leggings");
	}
}
