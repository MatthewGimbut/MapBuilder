package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for swords
 * @author Matthew Gimbut
 *
 */
public class Sword extends Weapon {

	public Sword(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				 Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Sword.png");
		this.setSimpleName(this.getWeaponType() + " sword");
		this.setItemToolTipText("A " + this.getWeaponType() + " sword");
	}
	
	public Sword(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				 Rarity howRare, WeaponType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Sword() {
		super(8, 0, 0, 2, 4, 0, 0, 175, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\sword\\Sword.png");
		this.setSimpleName(this.getWeaponType() + " sword");
		this.setItemToolTipText("A " + this.getWeaponType() + " sword");
	}
}
