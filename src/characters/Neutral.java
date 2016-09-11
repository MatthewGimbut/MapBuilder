package characters;


import map.MapParser;

public class Neutral extends Character {

	public Neutral(String name, int lvl, int currentHP, int maxHP, int currentMana, int maxMana, int atk, int magic, int def, int speed, int currentCarry, int carryCap) {
		super(name, lvl, currentHP, maxHP, currentMana, maxMana, atk, magic, def, speed, currentCarry, carryCap);
	}
	
	public Neutral(String name, String north, String south, String east, String west) {
		super(name, 1, 100, 100, 100, 100, 1, 1, 1, 1, 0, 10, north, south, east, west);
		this.setImage(south);
	}
	
	public Neutral() {
		super(MapParser.getRandomName(), 1, 100, 100, 100, 100, 1, 1, 1, 1, 0, 10);
		this.setImage(getRandomDirection());
	}
	
	public Neutral(String name) {
		super(name, 1, 100, 100, 100, 100, 1, 1, 1, 1, 0, 10, Character.GENERIC_NEUTRAL_NORTH, Character.GENERIC_NEUTRAL_SOUTH,
				Character.GENERIC_NEUTRAL_EAST, Character.GENERIC_NEUTRAL_WEST);
		this.setImage(getRandomDirection());
	}

}
