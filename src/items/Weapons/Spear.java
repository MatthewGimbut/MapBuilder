package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for spears
 * @author Matthew Gimbut
 *
 */
public class Spear extends Weapon {

	public Spear(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Spear.png");
		this.setSimpleName(this.getWeaponType() + " spear");
		this.setItemToolTipText("A " + this.getWeaponType() + " spear");
	}

	public Spear(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, 
			Rarity howRare, WeaponType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Spear() {
		super(14, 0, 0, 5, 8, 0, 0, 200, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\spear\\Spear.png");
		this.setSimpleName(this.getWeaponType() + " spear");
		this.setItemToolTipText("A " + this.getWeaponType() + " spear");
	}
}
