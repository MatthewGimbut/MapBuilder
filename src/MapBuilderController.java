import characters.Neutral;
import items.Armor.*;
import items.Consumables.Potion;
import items.Consumables.PotionType;
import items.Item;
import items.Rarity;
import items.Weapons.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.MapParser;
import sprites.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MapBuilderController extends BorderPane {

    private Stage primaryStage;
    private final String BACKGROUND_STRING = "-fx-background-position: center center; " +
            "-fx-background-repeat: stretch;";
    private GraphicsContext gc;
    private ObservableList<String> categories;
    private MapParser mapParser;
    private final String GENERIC_DIRECTORY = "Images\\Nature";
    private final String LOOTABLE_DIRECTORY = "Images\\Lootables";
    private final String ENEMY_DIRECTORY = "Images\\Enemies";
    private final String NEUTRAL_DIRECTORY = "Images\\Characters";
    private final String MISC_DIRECTORY = "Images\\Miscellaneous";
    private final String STRUCTURE_DIRECTORY = "Images\\Structures";
    private final String ITEM_DIRECTORY = "Images\\Items";
    private final String LOWER_DIRECTORY = "Images\\Lower";
    private final String UPPER_DIRECTORY = "Images\\Upper";

    private Stack<Sprite> removedItems;

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button addToMap;
    @FXML private AnchorPane anchor;
    @FXML private ImageView blackBackground;
    @FXML private ComboBox<String> categoryChoice;
    @FXML private Button clear;
    @FXML private TextField fileName;
    @FXML private ImageView grassBackground;
    @FXML private CheckBox hostile;
    @FXML private FlowPane imageFlow;
    @FXML private AnchorPane mapView;
    @FXML private CheckBox obstacle;
    @FXML private Button save;
    @FXML private ImageView selectedSpriteImage;
    @FXML private ImageView testBackground;
    @FXML private Button treeBorder;
    @FXML private TextField x;
    @FXML private TextField y;
    @FXML private Label filePath;
    @FXML private Button undo;
    @FXML private Button redo;


    public MapBuilderController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mapParser = new MapParser();
        this.categories = FXCollections.observableArrayList("Generic Obstacles", "Lootables", "Enemies", "Neutral NPCs", "Miscellaneous", "Structures", "Lower Layer", "Upper Layer","Items");
        this.removedItems = new Stack<>();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "MapBuilder.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    void loadImageArray(String dir) {
        ArrayList<File> files = new ArrayList<>();
        getFiles(dir, files);

            for(File f : files) {
                try {
                    HBox box = new HBox();
                    ImageView image = new ImageView(new Image("file:" + f.getAbsolutePath()));
                    image.setOnMouseClicked(event -> {
                        selectedSpriteImage.setImage(image.getImage());
                        filePath.setText(f.getAbsolutePath());
                    });

                    box.setMaxHeight(image.getFitHeight()+10);
                    box.setMaxWidth(image.getFitWidth()+10);
                    box.getChildren().add(image);
                    box.setStyle("-fx-border-color: black; " +
                                    "-fx-border-width: 1;");
                    imageFlow.getChildren().add(box);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    private void getFiles(String dir, ArrayList<File> files) {
        File directory = new File(dir);

        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                getFiles(file.getAbsolutePath(), files);
            }
        }
    }

    @FXML
    void initialize() {

        Canvas canvas = new Canvas(mapView.getPrefWidth(), mapView.getPrefHeight());
        mapView.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        drawMap();

        grassBackground.setOnMouseClicked(event ->{
            mapView.setStyle("-fx-background-image: url('GrassBackground.png'); " + BACKGROUND_STRING);
            mapParser.setBackground("grass");
        });

        blackBackground.setOnMouseClicked(event -> {
            mapView.setStyle("-fx-background-image: url('BlackBackground.png'); " + BACKGROUND_STRING);
            mapParser.setBackground("interior-black");
        });

        testBackground.setOnMouseClicked(event -> {
            mapView.setStyle("-fx-background-image: url('TestBack.png'); " + BACKGROUND_STRING);
            mapParser.setBackground("neonTest");
        });

        hostile.setOnAction(event -> {
            if(hostile.isSelected()) {
                mapParser.setHostile(true);
            } else {
                mapParser.setHostile(false);
            }
        });

        save.setOnAction(event -> {
            String name = fileName.getText();
            if(name.length() > 0) {
                if(!name.endsWith(".map")) {
                    name += ".map";
                }
                mapParser.writeToFile(name);
            }
        });

        treeBorder.setOnAction(event -> {
            mapParser.placeTreeBorder();
            drawMap();
        });

        clear.setOnAction(event -> {
            mapParser = new MapParser();
            drawMap();
        });

        addToMap.setOnAction(event -> {
            switch(categoryChoice.getSelectionModel().getSelectedItem()) {
                case"Generic Obstacles":
                    generateGenericObstacle(filePath.getText());
                    break;
                case "Lootables":
                    generateLootable(filePath.getText());
                    break;
                case "Enemies":

                    break;
                case "Neutral NPCs":
                    generateNeutral(filePath.getText());
                    break;
                case "Miscellaneous":
                    generateMisc(filePath.getText());
                    break;
                case "Structures":
                    generateGenericObstacle(filePath.getText());
                    break;
                case "Lower Layer":
                    generateLowerLayer(filePath.getText());
                    break;
                case "Upper Layer":
                    generateUpperLayer(filePath.getText());
                    break;
                case "Items":
                    generateDisplayItem(filePath.getText());
                    break;
            }
            drawMap();
        });

        categoryChoice.setItems(categories);

        categoryChoice.valueProperty().addListener(event -> {
            selectedSpriteImage.setImage(null);
            imageFlow.getChildren().clear();
            switch(categoryChoice.getSelectionModel().getSelectedItem()) {
                case"Generic Obstacles":
                    loadImageArray(GENERIC_DIRECTORY);
                    break;
                case "Lootables":
                    loadImageArray(LOOTABLE_DIRECTORY);
                    break;
                case "Enemies":
                    loadImageArray(ENEMY_DIRECTORY);
                    break;
                case "Neutral NPCs":
                    loadImageArray(NEUTRAL_DIRECTORY);
                    break;
                case "Miscellaneous":
                    loadImageArray(MISC_DIRECTORY);
                    break;
                case "Structures":
                    loadImageArray(STRUCTURE_DIRECTORY);
                    break;
                case "Lower Layer":
                    loadImageArray(LOWER_DIRECTORY);
                    break;
                case "Upper Layer":
                    loadImageArray(UPPER_DIRECTORY);
                    break;
                case "Items":
                    loadImageArray(ITEM_DIRECTORY);
                    break;
            }
        });

        undo.setOnAction(event -> {
            if(mapParser.getMapItems().size() > 0) {
                removedItems.push(mapParser.getMapItems().get(mapParser.getMapItems().size()-1));
                mapParser.getMapItems().remove(mapParser.getMapItems().size()-1);
                drawMap();
            }
        });

        redo.setOnAction(event -> {
            if(removedItems.size() > 0) {
                Sprite sprite = removedItems.pop();
                if(!mapParser.addItem(sprite)) removedItems.push(sprite);
                drawMap();
            }
        });

        this.setCenter(anchor);
    }

    private void generateNeutral(String imageLoc) {
        boolean success = false;
        String name = "Default name";
        TextInputDialog nameInput = new TextInputDialog();
        nameInput.setTitle("NPC creation");
        nameInput.setContentText("What is the name of the neutral NPC?");

        Optional<String> result1 = nameInput.showAndWait();
        if (result1.isPresent()){
                name = result1.get();
        }

        Neutral neut = new Neutral(name);

        success = false;
        int numDialog = 0;
        while (!success) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Dialog creation");
            dialog.setContentText("How many line of dialog would you like to create? Max length ~65 characters per line.");
            Optional<String> result2 = dialog.showAndWait();
            if (result2.isPresent()){
                try {
                    numDialog = Integer.parseInt(result2.get());
                    success = true;
                } catch(NumberFormatException e) {
                    success = false;
                    System.out.println("invalid");
                }
            }
        }

        Optional<String> result3;
        String[] dialogArray = new String[numDialog];
        for(int i = 0; i < numDialog; i++) {
            TextInputDialog prompt = new TextInputDialog();
            prompt.setTitle("Dialog creation");
            prompt.setContentText("Enter line " + i);

            result3 = prompt.showAndWait();
            if (result3.isPresent()){
                dialogArray[i] = result3.get();
            }
        }

        NPC npc = new NPC(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), neut, dialogArray);

        if(!mapParser.addItem(npc)) {
            displayIntersectionError();
        }
    }

    private void generateLootable(String imageLoc) {
        Lootable loot = new Lootable(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), imageLoc, getItemList());

        if(!mapParser.addItem(loot)) {
            displayIntersectionError();
        }
    }

    private void generateDisplayItem(String imageLoc) {
        Item i = generateItem();
        DisplayItem display = new DisplayItem(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), i);
        display.setImage(imageLoc);

        if(!mapParser.addItem(display)) {
            displayIntersectionError();
        }
    }

    private LinkedList<Item> getItemList() {
        LinkedList<Item> items = new LinkedList<>();
        int numItems = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Item creation");
            dialog.setContentText("How many items would you like to create?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    numItems = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }

        for(int i = 0; i < numItems; i++) {
            items.add(generateItem());
        }
        return items;
    }

    private Item generateItem() {
        Item i = null;

        List<String> choices = new ArrayList<>();
        choices.add("Boots");
        choices.add("ChestPiece");
        choices.add("Gloves");
        choices.add("Helmet");
        choices.add("Shield");
        choices.add("Legs");
        choices.add("Potion");
        choices.add("Axe");
        choices.add("Dagger");
        choices.add("Mace");
        choices.add("Spear");
        choices.add("Sword");

        String resultString = "";
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Boots", choices);
        dialog.setTitle("Item Choice");
        dialog.setContentText("Choose item type:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            resultString = result.get();
        }

        if(resultString.equals("Potion")) {
            i = new Potion(getPotionType(), getPotionVal(), getGoldVal());
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unique item");
            alert.setContentText("Is this a unique or generic item?");

            ButtonType buttonTypeOne = new ButtonType("Unique");
            ButtonType buttonTypeTwo = new ButtonType("Generic");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> promptResult = alert.showAndWait();
            if (promptResult.get() == buttonTypeOne){ //Unique
                switch(resultString) {
                    case "Boots":
                        i = new Boots(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "ChestPiece":
                        i = new ChestPiece(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Gloves":
                        i = new Gloves(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Helmet":
                        i = new Helmet(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Shield":
                        i = new Shield(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Legs":
                        i = new Legs(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Axe":
                        i = new Axe(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Dagger":
                        i = new Dagger(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Sword":
                        i = new Sword(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Spear":
                        i = new Spear(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                    case "Mace":
                        i = new Mace(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType(), getUniqueFileLoc(), getUniqueName(), getUniqueTooltip());
                        break;
                }
            } else if (promptResult.get() == buttonTypeTwo) { //Generic
                switch(resultString) {
                    case "Boots":
                        i = new Boots(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType());
                        break;
                    case "ChestPiece":
                        i = new ChestPiece(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType());
                        break;
                    case "Gloves":
                        i = new Gloves(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType());
                        break;
                    case "Helmet":
                        i = new Helmet(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType());
                        break;
                    case "Shield":
                        i = new Shield(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType());
                        break;
                    case "Legs":
                        i = new Legs(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getArmorType());
                        break;
                    case "Axe":
                        i = new Axe(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType());
                        break;
                    case "Dagger":
                        i = new Dagger(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType());
                        break;
                    case "Sword":
                        i = new Sword(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType());
                        break;
                    case "Spear":
                        i = new Spear(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType());
                        break;
                    case "Mace":
                        i = new Mace(getAttack(), getMagic(), getDefense(), getSpeed(), getWeight(), getHPBoost(), getManaBoost(), getGoldVal(), getRarity(), getWeaponType());
                        break;
                }
            }
        }

        return i;
    }

    private void generateGenericObstacle(String imageLoc) {
        GenericObstacle go = new GenericObstacle(0, 0, imageLoc);

        go.setX(Integer.parseInt(x.getText()));
        go.setY(Integer.parseInt(y.getText()));

        if(!mapParser.addItem(go)) {
            displayIntersectionError();
        }
    }

    private void generateLowerLayer(String imageLoc) {
        LowerLayer low = new LowerLayer(0,0, imageLoc);

        low.setX(Integer.parseInt(x.getText()));
        low.setY(Integer.parseInt(y.getText()));

        mapParser.addItem(low); //Don't check for intersections here, lower layer is fine to intersect!!
    }

    private void generateUpperLayer(String imageLoc) {
        UpperLayer upper = new UpperLayer(0, 0, imageLoc);

        upper.setX(Integer.parseInt(x.getText()));
        upper.setY(Integer.parseInt(y.getText()));

        upper.setObstacle(false);

        if(!mapParser.addItem(upper)) {
            displayIntersectionError();
        }
    }

    private void generateMisc(String imageLoc) {
        if(imageLoc.endsWith("SaveArea.png")) {
            Save save = new Save(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));
            if(!mapParser.addItem(save)) {
                displayIntersectionError();
            }
        } else if(imageLoc.endsWith("ExitVisible.png")) {
            //TODO exit code
        }
    }

    private void displayIntersectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Intersection");
        alert.setContentText("Sprite could not be added because it intersects with a preexisting item.");
        alert.showAndWait();
    }

    private void drawMap() {
        gc.clearRect(0, 0, mapView.getPrefWidth(), mapView.getPrefHeight());
        mapParser.getMapItems().forEach(sprite -> sprite.render(gc));
    }

    private WeaponType getWeaponType() {
        WeaponType wt = null;

        List<String> choices = new ArrayList<>();
        choices.add("Wood");
        choices.add("Stone");
        choices.add("Bronze");
        choices.add("Iron");
        choices.add("Steel");

        String resultString = "";
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Wood", choices);
        dialog.setTitle("Weapon Choice");
        dialog.setContentText("Choose weapon tier:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            resultString = result.get();
        }

        switch(resultString) {
            case "Wood":
                wt = WeaponType.wood;
                break;
            case "Stone":
                wt = WeaponType.stone;
                break;
            case "Bronze":
                wt = WeaponType.bronze;
                break;
            case "Iron":
                wt = WeaponType.iron;
                break;
            case "Steel":
                wt = WeaponType.steel;
                break;
            default:
                wt = WeaponType.bronze;
                break;
        }

        return wt;
    }

    private ArmorType getArmorType() {
        ArmorType at = null;

        List<String> choices = new ArrayList<>();
        choices.add("Wood");
        choices.add("Cloth");
        choices.add("Leather");
        choices.add("Stone");
        choices.add("Bronze");
        choices.add("Iron");
        choices.add("Steel");

        String resultString = "";
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Wood", choices);
        dialog.setTitle("Armor Choice");
        dialog.setContentText("Choose armor tier:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            resultString = result.get();
        }

        switch(resultString) {
            case "Wood":
                at = ArmorType.wood;
                break;
            case "Cloth":
                at = ArmorType.cloth;
                break;
            case "Leather":
                at = ArmorType.leather;
                break;
            case "Bronze":
                at = ArmorType.bronze;
                break;
            case "Iron":
                at = ArmorType.iron;
                break;
            case "Steel":
                at = ArmorType.steel;
                break;
            default:
                at = ArmorType.bronze;
                break;
        }

        return at;
    }


    private PotionType getPotionType() {
        PotionType pt = null;

        List<String> choices = new ArrayList<>();
        choices.add("Health");
        choices.add("Mana");
        choices.add("Defense");
        choices.add("Agility");
        choices.add("Attack");

        String resultString = "";
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Health", choices);
        dialog.setTitle("Potion Choice");
        dialog.setContentText("Choose potion type:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            resultString = result.get();
        }

        switch(resultString) {
            case "Health":
                pt = PotionType.Health;
                break;
            case "Mana":
                pt = PotionType.Mana;
                break;
            case "Defense":
                pt = PotionType.Defense;
                break;
            case "Agility":
                pt = PotionType.Agility;
                break;
            case "Attack":
                pt = PotionType.Attack;
                break;
            default:
                pt = PotionType.Health;
                break;
        }

        return pt;
    }

    private Rarity getRarity() {
        Rarity r = null;

        List<String> choices = new ArrayList<>();
        choices.add("Junk");
        choices.add("Common");
        choices.add("Uncommon");
        choices.add("Rare");
        choices.add("Very Rare");
        choices.add("Legendary");


        String resultString = "";
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Common", choices);
        dialog.setTitle("Rarity");
        dialog.setContentText("Choose rarity:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            resultString = result.get();
        }

        switch(resultString) {
            case "Junk":
                r = Rarity.JUNK;
                break;
            case "Common":
                r = Rarity.COMMON;
                break;
            case "Uncommon":
                r = Rarity.UNCOMMON;
                break;
            case "Rare":
                r = Rarity.RARE;
                break;
            case "Very Rare":
                r = Rarity.VERY_RARE;
                break;
            case "Legendary":
                r = Rarity.LEGENDARY;
                break;
            default:
                r = Rarity.COMMON;
                break;
        }

        return r;
    }

    private int getAttack() {
        int atk = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Attack value");
            dialog.setContentText("What is the attack stat of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    atk = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }

        return atk;
    }

    private int getDefense() {
        int def = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Defense value");
            dialog.setContentText("What is the defense stat of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    def = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return def;
    }

    private int getSpeed() {
        int spd = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Speed value");
            dialog.setContentText("What is the speed stat of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    spd = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return spd;
    }

    private double getWeight() {
        double weight = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Weight value");
            dialog.setContentText("What is the weight of the item? (To the nearest tenth)");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    weight = Double.parseDouble(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return weight;
    }

    private int getMagic() {
        int magic = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Magic value");
            dialog.setContentText("What is the magic stat of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    magic = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return magic;
    }

    private int getHPBoost() {
        int hpBoost = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("HP Boost value");
            dialog.setContentText("What is the HP boost of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    hpBoost = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return hpBoost;
    }

    private int getManaBoost() {
        int mana = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Mana boost value");
            dialog.setContentText("What is the mana boost of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    mana = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return mana;
    }

    private int getGoldVal() {
        int gold = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Gold value");
            dialog.setContentText("What is the gold value of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    gold = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return gold;
    }

    private int getPotionVal() {
        int potVal = 0;
        boolean successfulItemNum = false;
        while (!successfulItemNum) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Potion value");
            dialog.setContentText("What is the value of the potion? (Ex. how much it heals, the attack boost, etc.)");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    potVal = Integer.parseInt(result.get());
                    successfulItemNum = true;
                } catch(NumberFormatException e) {
                    successfulItemNum = false;
                    System.out.println("invalid");
                }
            }
        }
        return potVal;
    }

    private String getUniqueName() {
        String name = "";

        boolean successful = false;
        while (!successful) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Unique name");
            dialog.setContentText("What is the unique name of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    name = result.get();
                    successful = true;
                } catch(NumberFormatException e) {
                    successful = false;
                    System.out.println("invalid");
                }
            }
        }

        return name;
    }

    private String getUniqueTooltip() {
        String tooltip = "";

        boolean successful = false;
        while (!successful) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Unique tooltip");
            dialog.setContentText("What is the unique tooltip of the item?");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    tooltip = result.get();
                    successful = true;
                } catch(NumberFormatException e) {
                    successful = false;
                    System.out.println("invalid");
                }
            }
        }

        return tooltip;
    }

    private String getUniqueFileLoc() {
        String fileLoc = "";

        boolean successful = false;
        while (!successful) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Unique image");
            dialog.setContentText("What is the unique image file location of the item? Should be formatted as such: file:Images\\Weapons\\wood\\Boots.png, with the file being located in the Images directory of the game.");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    fileLoc = result.get();
                    successful = true;
                } catch(NumberFormatException e) {
                    successful = false;
                    System.out.println("invalid");
                }
            }
        }

        return fileLoc;
    }
}
