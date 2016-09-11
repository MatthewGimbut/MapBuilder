package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Gloves armor class
 * @author Matthew Gimbut
 *
 */
public class Gloves extends Armor {

	public Gloves(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, ArmorType armorType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, armorType);
		this.setImageLocation("Images\\Weapons\\" + this.getArmorType().toString() + "\\Axe.png");
		this.setSimpleName(getSimpleGloveName());
		this.setItemToolTipText(getGloveToolTip());
	}
	
	public Gloves(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				  Rarity howRare, ArmorType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Gloves() {
		super(0, 0, 2, 1, 2, 0, 0, 50, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Gloves.png");
		this.setSimpleName(getSimpleGloveName());
		this.setItemToolTipText(getGloveToolTip());
	}
	
	private String getGloveToolTip() {
		if(this.getArmorType().equals(ArmorType.steel) 
				|| this.getArmorType().equals(ArmorType.bronze)
				|| this.getArmorType().equals(ArmorType.iron)
				|| this.getArmorType().equals(ArmorType.wood)) {
			return "A pair of " + this.getArmorType() + " gauntlets";
		} else {
			return "A pair of " + this.getArmorType() + " gloves";
		}
	}

	/**
	 * Changes the name based on what type of armor it is.
	 * Steel gloves would be a bit uncomfortable
	 * so I changed them to gauntlets.
	 */
	private String getSimpleGloveName() {
		if(this.getArmorType().equals(ArmorType.steel) 
				|| this.getArmorType().equals(ArmorType.bronze)
				|| this.getArmorType().equals(ArmorType.iron)
				|| this.getArmorType().equals(ArmorType.wood)) {
			return this.getArmorType() + " gauntlets";
		} else {
			return this.getArmorType() + " gloves";
		}
	}
}
