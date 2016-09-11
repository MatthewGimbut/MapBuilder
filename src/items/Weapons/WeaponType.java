package items.Weapons;

/**
 * Enumerator for all different available weapon types
 * @author Matthew Gimbut
 *
 */
public enum WeaponType {
	wood {
		public String toString() {
			return "wood";
		}
	}, stone {
		public String toString() {
			return "stone";
		}
	}, bronze {
		public String toString() {
			return "bronze";
		}
	}, iron {
		public String toString() {
			return "iron";
		}
	}, steel {
		public String toString() {
			return "steel";
		}
	}

}
