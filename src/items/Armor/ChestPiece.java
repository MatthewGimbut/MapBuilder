package items.Armor;

import items.Item;
import items.Rarity;

/**
 * ChestPiece armor class
 *
 * @author Matthew Gimbut
 */
public class ChestPiece extends Armor {

    public ChestPiece(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value, Rarity howRare, ArmorType armorType) {
        super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, armorType);
        this.setImageLocation("Images\\Weapons\\" + this.getArmorType().toString() + "\\Chest.png");
        this.setSimpleName(this.getArmorType() + " armor");
        this.setItemToolTipText("A " + this.getArmorType() + " chest");
    }

    public ChestPiece(int atk, int magic, int def, int speedModifier, double weight, int hpBoost, int manaBoost, int value,
                      Rarity howRare, ArmorType weaponType, String uniqueImage, String uniqueName, String uniqueToolTip) {
        super(atk, magic, def, speedModifier, weight, hpBoost, manaBoost, value, howRare, weaponType);
        this.setImageLocation(uniqueImage);
        this.setSimpleName(uniqueName);
        this.setItemToolTipText(uniqueToolTip);
    }

    public ChestPiece() {
        super(0, 0, 8, 4, 9, 0, 0, 150, Item.randomRareness(), Armor.getRandomArmorType());
        this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Chest.png");
        this.setSimpleName(this.getArmorType() + " armor");
        this.setItemToolTipText("A " + this.getArmorType() + " chest");
    }
}
