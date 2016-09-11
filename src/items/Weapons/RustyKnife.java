package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for the rusty knife weapon
 * @author Matthew
 *
 */
@Deprecated
public class RustyKnife extends Weapon {
	
	public RustyKnife(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
		System.out.println(this.getWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Dagger.png");
	}
	
	public RustyKnife() {
		super(5, 0, 0, 1, 2, 0, 0, 10, Item.randomRareness(), WeaponType.iron);
		this.setImageLocation("Images\\Weapons\\RustyKnife\\RustyKnife.png");
	}
	
	@Override
	public String getSimpleName() {
		return "rusty knife";
	}

	@Override
	public String getItemToolTipText() {
		return "A rusty knife. Add more text here.";	
		}
	
}
