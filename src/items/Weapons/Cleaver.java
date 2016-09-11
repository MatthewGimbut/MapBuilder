package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for the cleaver
 * @author Matthew Gimbut
 *
 */
@Deprecated
public class Cleaver extends Weapon {
	
	public Cleaver(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		System.out.println(this.getWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Dagger.png");
	}
	
	public Cleaver() {
		super(5, 0, 0, 2, 4, 0, 0, 15, Item.randomRareness(), WeaponType.iron);
		this.setImageLocation("Images\\Weapons\\Cleaver\\Cleaver.png");
	}

	@Override
	public String getSimpleName() {
		return "cleaver";
	}

	@Override
	public String getItemToolTipText() {
		return "A rusty knife. Add more text here.";
	}


}
