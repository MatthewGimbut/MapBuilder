package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Generic weapon class for axes
 * @author Matthew Gimbut
 *
 */
public class Axe extends Weapon {

	public Axe(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Axe.png");
		this.setSimpleName(this.getWeaponType() + " axe");
		this.setItemToolTipText("A " + this.getWeaponType() + " axe");
	}
	
	public Axe(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, 
			Rarity howRare, WeaponType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Axe() {
		super(10, 0, 0, 3, 6, 0, 0, 180, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\axe\\Axe.png");
		this.setSimpleName(this.getWeaponType() + " axe");
		this.setItemToolTipText("A " + this.getWeaponType() + " axe");
	}
}
