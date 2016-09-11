package items.Weapons;

import items.Item;
import items.Rarity;

import java.util.Random;

/**
 * Abstract Weapon superclass for all other weapon types
 * @author Matthew Gimbut
 *
 */
@SuppressWarnings("serial")
public abstract class Weapon extends Item {

	private WeaponType weaponType;
	
	public Weapon(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, WeaponType weaponType) {
		super((int) Math.round(atk*weaponTypeMultiplier(weaponType)), (int) Math.round(magic*weaponTypeMultiplier(weaponType)), 
				(int) Math.round(def*weaponTypeMultiplier(weaponType)),	speedModifier, (int) (weight * weaponTypeMultiplier(weaponType)), hpBoost, manaBoost,
				(int) Math.round(value*weaponTypeMultiplier(weaponType)), howRare);
		this.weaponType = weaponType;
	}
	
	public WeaponType getWeaponType() {
		return this.weaponType;
	}
	
	public static WeaponType getRandomWeaponType() {
		Random randy = new Random();
		int random = randy.nextInt(150);
		if(random >= 0 && random < 25) {
			return WeaponType.wood;
		}
		else if(random >= 25 && random < 120) {
			return WeaponType.stone;
		}
		else if(random >= 120 && random < 135) {
			return WeaponType.bronze;
		}
		else if(random >= 135 && random < 145) {
			return WeaponType.iron;
		}
		else if(random >= 145 && random < 150) {
			return WeaponType.steel;
		}
		else {
			return WeaponType.wood;
		}
	}
	
	public static double weaponTypeMultiplier(WeaponType weapon) {
		switch(weapon) {
		case wood: 
			return 0.30;
		case stone: 
			return 0.50;
		case bronze: 
			return 1.0;
		case iron: 
			return 1.5;
		case steel: 
			return 2.0;
		default:
			return 1.0;
		}
	}
}
