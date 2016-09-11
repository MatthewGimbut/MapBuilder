package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Helmet armor class
 * @author Matthew Gimbut
 *
 */
public class Helmet  extends Armor {
	
	public Helmet(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, ArmorType armorType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, armorType);
		this.setImageLocation("Images\\Weapons\\" + this.getArmorType().toString() + "\\Helmet.png");
		this.setItemToolTipText(getHelmetToolTip());
		this.setSimpleName(getSimpleHelmetName());
	}
	
	public Helmet(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				  Rarity howRare, ArmorType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Helmet() {
		super(0, 0, 6, 1, 3, 0, 0, 80, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Helmet.png");
		this.setItemToolTipText(getHelmetToolTip());
		this.setSimpleName(getSimpleHelmetName());
	}

	private String getHelmetToolTip() {
		if(this.getArmorType().equals(ArmorType.cloth)) {
			return "A " + this.getArmorType() + " hood";
		}
		else {
			return "A " + this.getArmorType() + " helmet";
		}
	}

	private String getSimpleHelmetName() {
		if(this.getArmorType().equals(ArmorType.cloth)) {
			return this.getArmorType() + " hood";
		}
		else {
			return this.getArmorType() + " helmet";
		}
	}
}
