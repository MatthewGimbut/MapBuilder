package map;
import sprites.*;
import java.util.ArrayList;

/**
 *
 * Contains all information important to a specific map region. This includes the background, the file location,
 * the player (maybe unnecessary?), and all map items sorted into their specific arrays.
 *
 * An instance of this class is written to a JSON file to retain all required information easily.
 *
 * Each separate ArrayList is necessary to retain the type information of the objects because it isn't saved in JSON.
 * The InterfaceAdapter is meant for interfaces/abstract classes. Sprite is neither, which is why we use this approach
 * instead of adding another instance of the adapter class to the Gson builder object.
 *
 * @author Matthew Gimbut
 *
 */

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


    /**
     * Only constructor for the JSONMapTemplate in order to write all necessary information to file.
     * @param mapItems The ArrayList containing all of the Sprites currently on the map.
     * @param id The CSS ID used to set the background.
     * @param fileLocation The file location of the json file.
     */
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

        //Sorts the elements into their appropriate arrays
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

    /**
     * Concatenates all of the objects from the sorted arrays into one big array.
     * @return An ArrayList containing all map items.
     */
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

    /**
     * Returns the size of the ArrayList returned by getMapItems(), which is all of the items on the map unsorted.
     * @return An int representation of the total amount of Sprites.
     */
    public int size() {
        return getMapItems().size();
    }

    /**
     * Returns the CSS ID property of the GamePane to set the background.
     * @return A string containing the CSS ID for the background.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the file location of the json file.
     * @return A String containing the file location of the json file.
     */
    public String getFileLocation() { return this.fileLocation; }

}
