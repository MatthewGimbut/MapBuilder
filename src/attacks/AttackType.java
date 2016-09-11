package attacks;

/**
 * Enumerator for all of the different types of attacks
 * @author Sean Zimmerman
 *
 */
public enum AttackType {

	physical {
		public String toString() {
			return "physical";
		}
	}, magic {
		public String toString() {
			return "magic";
		}
	}, special {
		public String toString() {
			return "special";
		}
	}, buff {
		public String toString() {
			return "buff";
		}
	}
}
