package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Armor class for boots
 * @author Matthew Gimbut
 *
 */
public class Boots extends Armor {

	public Boots(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, ArmorType armorType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, armorType);
		this.setImageLocation("Images\\Weapons\\" + this.getArmorType().toString() + "\\Axe.png");
		this.setSimpleName(this.getArmorType() + " boots");
		this.setItemToolTipText("A pair of " + this.getArmorType() + " boots");
	}
	
	public Boots(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				 Rarity howRare, ArmorType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Boots() {
		super(0, 0, 3, 1, 3.0, 0, 0, 65, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Boots.png");
		this.setSimpleName(this.getArmorType() + " boots");
		this.setItemToolTipText("A pair of " + this.getArmorType() + " boots");
	}
}
