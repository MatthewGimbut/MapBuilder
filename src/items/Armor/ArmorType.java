package items.Armor;

/**
 * Enumerator for all of the different types of armors
 * @author Matthew Gimbut
 *
 */
public enum ArmorType {
	cloth {
		public String toString() {
			return "cloth";
		}
	}, leather {
		public String toString() {
			return "leather";
		}
	}, bronze {
		public String toString() { return "bronze"; }
	}, iron {
		public String toString() {
			return "iron";
		}
	}, steel {
		public String toString() {
			return "steel";
		}
	}, wood {
		public String toString() {
			return "wood";
		}
	}
}
