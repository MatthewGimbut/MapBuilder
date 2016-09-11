package characters;

import attacks.Other.StatChange;
import items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Abstract character superclass for all characters appearing in the game.
 * @author Matthew Gimbut & Sean Zimmerman
 *
 */
public abstract class Character implements Serializable {

	public static final String GENERIC_NEUTRAL_SOUTH = "Images\\Characters\\GenericNeutral\\GenericNeutralSouth.png";
	public static final String GENERIC_NEUTRAL_NORTH = "Images\\Characters\\GenericNeutral\\GenericNeutralNorth.png";
	public static final String GENERIC_NEUTRAL_EAST = "Images\\Characters\\GenericNeutral\\GenericNeutralEast.png";
	public static final String GENERIC_NEUTRAL_WEST = "Images\\Characters\\GenericNeutral\\GenericNeutralWest.png";
	private static final long serialVersionUID = -7026591060236208199L;
	private List<StatChange> currentStatusEffects; //buffs and debuffs
	private int lvl;
	private String name;
	private int currentHP;
	private int maxHP;
	private int currentMana;
	private int maxMana;
	private int atk;
	private int magic;
	private int def;
	private int speed;
	private double carryCap;
	private double currentCarry;
	private boolean isDead;
	private String image;
	protected String south;
	protected String north;
	protected String east;
	protected String west;
	
	public Character(String name, int lvl, int currentHP, int maxHP, int currentMana, int maxMana, int atk, int magic, int def, int speed, int currentCarry, int carryCap) {
		currentStatusEffects = new ArrayList<StatChange>();
		this.lvl = lvl;
		this.name = name;
		this.currentHP = currentHP;
		this.maxHP = maxHP;
		this.currentMana = currentMana;
		this.maxMana = maxMana;
		this.atk = atk;
		this.magic = magic;
		this.def = def;
		this.speed = speed;
		this.currentCarry = currentCarry;
		this.carryCap = carryCap;
		this.isDead = false;

	}
	
	public Character(String name, int lvl, int currentHP, int maxHP, int currentMana, int maxMana, int atk, int magic, int def, int speed,
					 int currentCarry, int carryCap, String north, String south, String east, String west) {
		this(name, lvl, currentHP, maxHP, currentMana, maxMana, atk, magic, def, speed, currentCarry, carryCap);
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}
	
	/**
	 * Adds to base stats according to item equipped.
	 * @param i The item equipped
	 */
	public void equipUpdateStats(Item i) {
		this.atk  += i.getAtk();
		this.magic += i.getMagic(); //TODO
		this.def += i.getDef();
		this.maxHP += i.getHpBoost();
		this.maxMana += i.getManaBoost(); //TODO
		this.speed -= i.getSpeedModifier();	
	}
	
	
	/**
	 * Removes from base stats according to item unequipped.
	 * @param i The item unequipped
	 */
	public void unequipUpdateStats(Item i) {
		this.atk  -= i.getAtk();
		this.magic -= i.getMagic(); 
		this.def -= i.getDef();
		this.maxHP -= i.getHpBoost();
		this.maxMana -= i.getManaBoost(); 
		this.speed += i.getSpeedModifier();	
	}
	
	/**
	 * Method to level up the character and increase stats
	 */
	public void lvlUp(int leftoverXP) {
		this.lvl++;
		//GameController.playSound(""); //TODO
		Random r = new Random();
		this.speed += r.nextInt(3);
		this.atk += r.nextInt(2);
		this.magic += r.nextInt(4); 
		this.def += r.nextInt(2);
		this.carryCap += r.nextInt(4);
		int hpBoost = r.nextInt(7);
		this.maxHP += hpBoost;
		this.currentHP += hpBoost;
		int manaBoost = r.nextInt(5);
		this.maxMana += manaBoost;
		this.currentMana += manaBoost;
	}
	
	public double getCurrentCarry() {
		return currentCarry;
	}

	public void setCurrentHP(int hp) {
		this.currentHP = hp;
	}
	
	public void modifyCurrentCarry(double currentCarry) {
		this.currentCarry += currentCarry;
	}
	
	public double getCarryCap() {
		return carryCap;
	}

	public void modifyCarryCap(double carryCap) {
		this.carryCap += carryCap;
	}
	
	public int getLvl() {
		return this.lvl;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAtk() {
		return atk;
	}

	public void modifyAtk(int atk) {
		this.atk += atk;
	}
	
	public int getMagic() {
		return magic;
	}
	
	public void modifyMagic(int magic) {
		this.magic += magic;
	}

	public int getDef() {
		return def;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void modifyMaxHP(int hp) {
		this.maxHP += hp;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public void modifyMaxMana(int mana) {
		this.maxMana += mana;
	}

	public void modifyDef(int def) {
		this.def += def;
	}

	public int getSpeed() {
		return speed;
	}

	public void modifySpd(int speed) {
		this.speed += speed;
	}
	
	
	public int getCurrentHP() {
		return currentHP;
	}

	public void modifyCurrentHP(int hpMod) {
		if (currentHP + hpMod <= maxHP) {
			currentHP += hpMod;
		}
		else {
			currentHP = maxHP;
		}
		
		if (currentHP < 0) {
			currentHP = 0;
		}
	}
	
	public int getCurrentMana() {
		return currentMana;
	}
	
	public void modifyCurrentMana(int manaMod) {
		if (currentMana + manaMod <= maxMana) {
			currentMana += manaMod;
		}
		else {
			currentMana = maxMana;
		}
		
		if (currentMana < 0) {
			currentMana = 0;
		}
	}
	
	public void setDead(boolean dead) {
		this.isDead = dead;
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void removeAllStatChanges() {
		for(StatChange s: currentStatusEffects) {
			s.remove(this);
		}
		currentStatusEffects.clear();
	}
	
	public void addStatChange(StatChange s) {
		s.apply(this);
	}
	
	public void decrementEffects() {
		Iterator<StatChange> it = currentStatusEffects.iterator();
		while(it.hasNext()) {
			StatChange s = it.next();
			s.decrement();
			if(s.turnsLeft() == 0) { //if there are no turns left remove it from the list
				it.remove();
			}
		}
	}

	public String getSouthImage() {
		return south;
	}

	public String getNorthImage() {
		return north;
	}

	public String getEastImage() {
		return east;
	}

	public String getWestImage() {
		return west;
	}

	protected String getRandomDirection() {
		Random r = new Random();
		switch(r.nextInt(4)) {
			case 0: return north;
			case 1: return south;
			case 2: return east;
			case 3: return west;
		}
		return south;
	}

}
