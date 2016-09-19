package map;
import sprites.*;
import java.util.ArrayList;

public class MapJSONTemplate {

    private String id;
    private String fileLocation;
    private ArrayList<GenericObstacle> genericObstacles;
    private ArrayList<DisplayItem> displayItems;
    private ArrayList<Exit> exits;
    private ArrayList<Lootable> lootables;
    private ArrayList<LowerLayer> lowerLayers;
    private ArrayList<NPC> npcs;
    private ArrayList<Save> saves;
    private ArrayList<UpperLayer> upperLayers;
    private PlayerSprite playerSprite;


    public MapJSONTemplate(ArrayList<Sprite> mapItems, String id, String fileLocation) {
        this.id = id;
        this.fileLocation = fileLocation;
        this.genericObstacles = new ArrayList<>();
        this.displayItems = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.lootables = new ArrayList<>();
        this.lowerLayers = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.saves = new ArrayList<>();
        this.upperLayers = new ArrayList<>();

        mapItems.forEach(sprite -> {
            String className = sprite.getClass().getSimpleName();
            switch(className) {
                case "GenericObstacle":
                    genericObstacles.add((GenericObstacle) sprite);
                    break;
                case "DisplayItem":
                    displayItems.add((DisplayItem) sprite);
                    break;
                case "Exit":
                    exits.add((Exit) sprite);
                    break;
                case "Lootable":
                    lootables.add((Lootable) sprite);
                    break;
                case "LowerLayer":
                    lowerLayers.add((LowerLayer) sprite);
                    break;
                case "NPC":
                    npcs.add((NPC) sprite);
                    break;
                case "Save":
                    saves.add((Save) sprite);
                    break;
                case "UpperLayer":
                    upperLayers.add((UpperLayer) sprite);
                    break;
                case "PlayerSprite":
                    this.playerSprite = (PlayerSprite) sprite;
                    break;
            }
        });
    }

    public ArrayList<Sprite> getMapItems() {
        ArrayList<Sprite> concatenation = new ArrayList<>();
        concatenation.addAll(genericObstacles);
        concatenation.addAll(displayItems);
        concatenation.addAll(exits);
        concatenation.addAll(lootables);
        concatenation.addAll(lowerLayers);
        concatenation.addAll(npcs);
        concatenation.addAll(saves);
        concatenation.addAll(upperLayers);
        return concatenation;
    }

    public int size() {
        return getMapItems().size();
    }

    public String getId() {
        return id;
    }

    public String getFileLocation() { return this.fileLocation; }

    public void serialize() {

    }
}
