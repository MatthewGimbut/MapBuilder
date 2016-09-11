package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Generic weapon class for maces
 * @author Matthew Gimbut
 *
 */
public class Mace extends Weapon {

	public Mace(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Mace.png");
		this.setSimpleName(this.getWeaponType() + " mace");
		this.setItemToolTipText("A " + this.getWeaponType() + " mace");
	}
	
	public Mace(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				Rarity howRare, WeaponType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Mace() {
		super(12, 0, 0, 4, 7, 0, 0, 178, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\mace\\Mace.png");
		this.setSimpleName(this.getWeaponType() + " mace");
		this.setItemToolTipText("A " + this.getWeaponType() + " mace");
	}
}
