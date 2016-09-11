package items.Armor;

import items.Item;
import items.Rarity;

import java.util.Random;

/**
 * Abstract superclass for armors.
 * Does all the math unique to armor types here, contains some useful
 * static methods related to armors. 
 * @author Matthew
 *
 */
public abstract class Armor extends Item {

	private ArmorType armorType;
	
	public Armor(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, ArmorType armorType) {
		super((int) Math.round(atk * ArmorTypeMultiplier(armorType)), (int)Math.round(magic * ArmorTypeMultiplier(armorType)), 
				(int) Math.round(def * ArmorTypeMultiplier(armorType)),	speedModifier, (int) Math.round(weight * ArmorTypeMultiplier(armorType)), hpBoost, 
				manaBoost, (int) Math.round(value * ArmorTypeMultiplier(armorType)), howRare);
		this.armorType = armorType;
	}

	public ArmorType getArmorType() {
		return this.armorType;
	}
	
	/**
	 * Gets a random armor type from the ArmorType enumerator
	 * @return An ArmorType
	 */
	public static ArmorType getRandomArmorType() {
		Random randy = new Random();
		int random = randy.nextInt(150);
		if(random >= 0 && random < 25) {
			return ArmorType.cloth;
		}
		else if(random >= 25 && random < 120) {
			return ArmorType.wood;
		}
		else if(random >= 120 && random < 135) {
			return ArmorType.leather;
		}
		else if(random >= 135 && random < 143) {
			return ArmorType.bronze;
		}
		else if(random >= 143 && random < 148) {
			return ArmorType.iron;
		}
		else if(random >= 148 && random < 150) {
			return ArmorType.steel;
		}
		else {
			return ArmorType.cloth;
		}
	}
	
	/**
	 * Gets the appropriate multiplier for a specific armor type
	 * @param armor The armor type 
	 * @return A double containing the ArmorType multiplier
	 */
	public static double ArmorTypeMultiplier(ArmorType armor) {
		switch(armor) {
		case cloth: 
			return 0.50;
		case wood: 
			return 0.75;
		case leather: 
			return 1.0;
		case bronze: 
			return 1.25;
		case iron: 
			return 1.5;
		case steel: 
			return 2.0;
		default:
			return 1.0;
		}
	}
	
}
