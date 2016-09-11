package items.Consumables;

/**
 * Created by Matthew on 4/11/2016.
 */
public enum PotionType {
    Health {
        public String toString() { return "Health Potion"; }
    }, Mana {
        public String toString() { return "Mana Potion"; }
    }, Defense {
        public String toString() { return "Defense Potion"; }
    }, Agility {
        public String toString() { return "Agility Potion"; }
    }, Attack {
        public String toString() { return "Attack Potion";}
    }
}