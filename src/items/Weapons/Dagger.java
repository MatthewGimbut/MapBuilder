package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Generic weapon class for daggers
 * @author Matthew Gimbut
 *
 */
public class Dagger extends Weapon {

	public Dagger(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		System.out.println(this.getWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Dagger.png");
		this.setSimpleName(this.getWeaponType() + " dagger");
		this.setItemToolTipText("A " + this.getWeaponType() + " dagger");
	}
	
	public Dagger(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
				  Rarity howRare, WeaponType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		this.setImageLocation(uniqueImage);
		this.setSimpleName(uniqueName);
		this.setItemToolTipText(uniqueToolTip);
	}
	
	public Dagger() {
		super(6, 0, 0, 1, 2, 0, 0, 75, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\dagger\\Dagger.png");
		this.setSimpleName(this.getWeaponType() + " dagger");
		this.setItemToolTipText("A " + this.getWeaponType() + " dagger");
	}	
}
