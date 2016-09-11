package attacks.Other;
import java.util.Map;

/**
 * An Abstract Class which represents a Stat Change in an attack (ex, buff or debuff)
 * @author Sean Zimmerman
 *
 */
public abstract class StatChange {

	private int numTurns;
	private Map<String, Double> effectedStats; //Stat name and value effected by
	private String effect;
	
	public StatChange(int numTurns, Map<String, Double> effectedStats, String effect) {
		this.numTurns = numTurns;
		this.effectedStats = effectedStats;
		this.effect = effect;
	}
	
	public abstract void apply(characters.Character c);
	public abstract void remove(characters.Character c);
	
	public void decrement() {
		numTurns--;
	}
	
	public int turnsLeft() {
		return numTurns;
	}
	
	public String toString() {
		return effect;
	}
}
