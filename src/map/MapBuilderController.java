package map;

import characters.Character;
import characters.Enemy;
import characters.Neutral;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import items.Armor.*;
import items.Consumables.Potion;
import items.Consumables.PotionType;
import items.Item;
import items.Rarity;
import items.Weapons.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.InterfaceAdapter;
import map.MapJSONTemplate;
import map.MapParser;
import quests.master.MasterQuests;
import quests.trigger.Trigger;
import sprites.*;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MapBuilderController extends BorderPane {

    private Stage primaryStage;
    private final String BACKGROUND_STRING = "-fx-background-position: center center; " +
            "-fx-background-repeat: stretch;";
    private GraphicsContext gc;
    private ObservableList<String> categories;
    private MapParser mapParser;
    private final String GENERIC_DIRECTORY      = "Images\\Objects\\Nature";
    private final String LOOTABLE_DIRECTORY     = "Images\\Objects\\Lootables";
    private final String ENEMY_DIRECTORY        = "Images\\Objects\\Enemies";
    private final String NEUTRAL_DIRECTORY      = "Images\\Objects\\Characters";
    private final String MISC_DIRECTORY         = "Images\\Objects\\Miscellaneous";
    private final String STRUCTURE_DIRECTORY    = "Images\\Objects\\Structures";
    private final String ITEM_DIRECTORY         = "Images\\Objects\\Items";
    private final String LOWER_DIRECTORY        = "Images\\Objects\\Lower";
    private final String UPPER_DIRECTORY        = "Images\\Objects\\Upper";

    private final String[] attack       = { "Attack (integer)", "Please enter the attack stat."};
    private final String[] defense      = { "Defense (integer)", "Please enter the defense stat."};
    private final String[] magic        = { "Magic (integer)", "Please enter the magic stat."};
    private final String[] speed        = { "Speed (integer)", "Please enter the speed stat."};
    private final String[] weight       = { "Weight (decimal)", "Please enter the weight stat."};
    private final String[] hpBoost      = { "HP Boost (integer)", "Please enter the size of the HP boost."};
    private final String[] manaBoost    = { "Mana boost (integer)", "Please enter the size of the mana boost."};
    private final String[] gold         = { "Gold (integer)", "Please enter the gold value."};
    private final String[] fileLoc      = { "File location (String)", "What is the unique image file location of the item? Should be formatted as such: file:Images\\Weapons\\wood\\Boots.png, with the file being located in the Images directory of the game."};
    private final String[] name         = { "Name (String", "What is the unique name?"};
    private final String[] tooltip      = { "Tooltip (String)", "What is the unique tooltip?"};
    private final String[] level        = { "Level (integer)", "What is the level of the character?"};
    private final String[] currentHP    = { "Current HP (integer)", "What is the current HP value of the character?"};
    private final String[] maxHP        = { "Max HP (integer)", "What is the max HP value of the character?"};
    private final String[] currentMana  = { "Current mana (integer)", "What is the current mana value of the character?"};
    private final String[] maxMana      = { "Max mana (integer)", "What is the max mana value for the character?"};
    private final String[] charName     = { "Name (String)", "What is the name of the character?"};
    private final String[] potionVal    = { "Potion value (integer)", "What is the value of the potion? (Ex. how much it heals, the attack boost, etc.)"};
    private final String[] nextExitX    = { "Next X", "What is the value of the X coordinate the player should be at in the next map?"};
    private final String[] nextExitY    = { "Next Y", "What is the value of the Y coordinate the player should be at in the next map?"};
    private final String[] direction    = { "Direction", "What is the direction the player should be facing in the next map?"};
    private final String[] mapLoc       = { "Next map file", "What is the location of the next map file? (Ex: Map0-0.json, TestHouseLarge.json)"};

    private Stack<Sprite> removedItems;

    public static Sprite marker;

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button remove;
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
    @FXML private Button load;
    @FXML private ImageView selectedSpriteImage;
    @FXML private ImageView testBackground;
    @FXML private Button treeBorder;
    @FXML private TextField x;
    @FXML private TextField y;
    @FXML private Label filePath;
    @FXML private Button undo;
    @FXML private Button redo;
    @FXML private Button saveJSON;
    @FXML private Button loadJSON;
    @FXML public Button front;

    private final int markerSpeed = 5;


    public MapBuilderController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mapParser = new MapParser();
        this.categories = FXCollections.observableArrayList("Generic Obstacles", "Lootables", "Enemies", "Neutral NPCs", "Miscellaneous", "Structures", "Lower Layer", "Upper Layer","Items");
        this.removedItems = new Stack<>();
        this.marker = new Sprite(0, 0, "file:Images\\Arrow.png");
        marker.setObstacle(false);
        this.mapParser.getMapItems().add(marker);

        this.setOnKeyPressed((KeyEvent key) -> {
            switch(key.getCode().toString()) {
                case "LEFT":
                    marker.setX(marker.getX() - markerSpeed);
                    this.requestFocus();
                    break;
                case "RIGHT":
                    marker.setX(marker.getX() + markerSpeed);
                    this.requestFocus();
                    break;
                case "UP":
                    marker.setY(marker.getY() - markerSpeed);
                    this.requestFocus();
                    break;
                case "DOWN":
                    marker.setY(marker.getY() + markerSpeed);
                    this.requestFocus();
                    break;
            }
            drawMap();
            x.setText(marker.getX() + "");
            y.setText(marker.getY() + "");
        });

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "MapBuilder.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
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

        load.setOnAction(event -> {
            String name = fileName.getText();
            if(name.length() > 0) {
                if(!name.endsWith(".map")) {
                    name += ".map";
                }
                try {
                    mapParser.setMapItems(mapParser.parseMap(name));
                    drawMap();
                    drawBackground(mapParser.getBackground());
                } catch(IOException e) {
                    System.out.println("Error reading file.");
                    System.out.println(e.getMessage());
                }
            }
        });

        treeBorder.setOnAction(event -> {
            mapParser.placeTreeBorder();
            front.fire();
            drawMap();
        });

        clear.setOnAction(event -> {
            mapParser = new MapParser();
            front.fire();
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
                    generateEnemy(filePath.getText());
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

        x.textProperty().addListener(new CoordinateListener(x));
        y.textProperty().addListener(new CoordinateListener(y));

        remove.setOnAction(event -> {
            int xCoord;
            try {
                xCoord = Integer.parseInt(x.getText());
            } catch (NumberFormatException e) {
                xCoord = 0;
            }

            int yCoord;
            try {
                yCoord = Integer.parseInt(y.getText());
            } catch (NumberFormatException e) {
                yCoord = 0;
            }

            Sprite sprite = new Sprite(xCoord, yCoord, "Images\\Arrow.png");
            boolean found = false;
            for(int i = 0; i < mapParser.getMapItems().size() && !found; i++) {
                if(mapParser.getMapItems().get(i).intersects(sprite) && !mapParser.getMapItems().get(i).equals(marker)) {
                    removedItems.push(mapParser.getMapItems().get(i));
                    mapParser.getMapItems().remove(i);
                    drawMap();
                    found = true;
                }
            }
            front.fire();
        });

        front.setOnAction(event -> {
            if(mapParser.getMapItems().contains(marker)) {
                mapParser.getMapItems().remove(marker);
                mapParser.getMapItems().add(marker);
            } else {
                mapParser.getMapItems().add(marker);
            }
            this.requestFocus();
            drawMap();
        });

        undo.setOnAction(event -> {
            if(mapParser.getMapItems().size() > 0) {
                removedItems.push(mapParser.getMapItems().get(mapParser.getMapItems().size()-1));
                mapParser.getMapItems().remove(mapParser.getMapItems().size()-1);
                front.fire();
                drawMap();
            }
        });

        redo.setOnAction(event -> {
            if(removedItems.size() > 0) {
                Sprite sprite = removedItems.pop();
                if(!mapParser.addItem(sprite)) removedItems.push(sprite);
                front.fire();
                drawMap();
            }
        });

        saveJSON.setOnAction(event -> {
            String file = getFileLocation();
            mapParser.getMapItems().remove(marker);
            MapJSONTemplate m = new MapJSONTemplate(mapParser.getMapItems(), mapParser.getBackground(), file);

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try(BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
                GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Item.class, new InterfaceAdapter<Item>())
                .registerTypeAdapter(Character.class, new InterfaceAdapter<Character>())
                .setPrettyPrinting();

                Gson gson = gsonBuilder.create();
                String json = gson.toJson(m);
                br.write(json);
            } catch (IOException e) {
                System.out.println("Error writing JSON!");
                System.out.println(e.getMessage());
            }

            mapParser.getMapItems().add(marker);
        });

        loadJSON.setOnAction(event -> {
            String file = getFileLocation();
            try {
                String json = new Scanner(new File(file)).useDelimiter("\\Z").next(); //"\\Z" Delimiter is the end of file character, loading the entire file into the String with one call to next().

                GsonBuilder gson = new GsonBuilder()
                                .setPrettyPrinting()
                                .setLenient()
                                .disableHtmlEscaping()
                                .registerTypeAdapter(Item.class, new InterfaceAdapter<Item>())
                                .registerTypeAdapter(Character.class, new InterfaceAdapter<Character>());

                MapJSONTemplate m = gson.create().fromJson(json, MapJSONTemplate.class);

                drawBackground(m.getId());

                mapParser.setBackground(m.getId());

                m.getMapItems().forEach(sprite -> {
                    if(sprite.getImageLocation().contains("\\\\")) sprite.setImage(sprite.getImageLocation().replace("\\\\", "\\"));
                    sprite.setImage(sprite.getImageLocation().replace("C:\\Users\\Matthew\\workspace\\MapBuilder\\", ""));
                    if(sprite instanceof NPC) {
                        NPC npc = (NPC) sprite;
                        LinkedList<Trigger> activationTriggerTemp = new LinkedList<>();
                        LinkedList<Trigger> questTriggerTemp = new LinkedList<>();

                        for(Trigger t : npc.getQuestActivationTriggers()) {
                            MasterQuests master = MasterQuests.valueOf(t.getAssociatedWith());
                            if(master != null) {
                                activationTriggerTemp.add(master.getQuest().getQuestAcceptanceTrigger());
                            } else {
                                System.out.println("Failed to parse quest " + t.getAssociatedWith());
                            }
                        }

                        for(Trigger t : npc.getQuestTriggers()) {
                            String[] data = t.getAssociatedWith().split("_");
                            MasterQuests master = MasterQuests.valueOf(data[0]);
                            int taskNum = Integer.parseInt(data[1], 10);
                            taskNum--;
                            if(master != null) {
                                questTriggerTemp.add(master.getQuest().getAllTasks().get(taskNum).getTrigger());
                            } else {
                                System.out.println("Failed to parse quest " + t.getAssociatedWith());
                            }
                        }

                        npc.getQuestActivationTriggers().clear();
                        npc.getQuestTriggers().clear();

                        npc.setQuestActivationTriggers(activationTriggerTemp);
                        npc.setQuestTriggers(questTriggerTemp);
                    }
                });
                mapParser.setMapItems(m.getMapItems());
                drawMap();
            } catch (IOException e) {
                System.out.println("Error loading JSON!");
                System.out.println(e.getMessage());
            }
            front.fire();
        });

        this.setCenter(anchor);
    }

    private String getFileLocation() {
        return fileName.getText() + (fileName.getText().endsWith(".json") ? "" : ".json" );
    }

    private void drawBackground(String backgroundId) {
        switch(backgroundId) {
            case "grass":
                mapView.setStyle("-fx-background-image: url('GrassBackground.png'); " + BACKGROUND_STRING);
                break;
            case "interior-black":
                mapView.setStyle("-fx-background-image: url('BlackBackground.png'); " + BACKGROUND_STRING);
                break;
            case "neonTest":
                mapView.setStyle("-fx-background-image: url('TestBack.png'); " + BACKGROUND_STRING);
                break;
            default:
                break;
        }
    }

    private void generateEnemy(String imageLoc) {
        Enemy enemy = new Enemy(getStringInput(charName), getIntegerInput(level), getIntegerInput(currentHP), getIntegerInput(maxHP), getIntegerInput(currentMana), getIntegerInput(maxMana), getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense),
                getIntegerInput(speed), imageLoc, imageLoc, imageLoc, imageLoc);
        NPC npc = new NPC(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), enemy, getDialogueArray());
        System.out.println(npc.getImageLocation());

        if(!mapParser.addItem(npc)) {
            displayIntersectionError();
        }
    }

    private void generateNeutral(String imageLoc) { //For now, the image location is not used because the neutral npc is created like a random one. Random image and direction are assigned until further notice.
        Neutral neut = new Neutral(getStringInput(charName));
        NPC npc = new NPC(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), neut, getDialogueArray());
        System.out.println(npc.getImageLocation());

        if(!mapParser.addItem(npc)) {
            displayIntersectionError();
        }
    }

    private String[] getDialogueArray() {
        boolean success = false;
        int numDialog = 0;
        while (!success) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Dialog creation");
            dialog.setContentText("How many lines of dialog would you like to create? Max length ~65 characters per line.");
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

        return dialogArray;
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
            i = new Potion(getPotionType(), getIntegerInput(potionVal), getIntegerInput(gold));
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
                        i = new Boots(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "ChestPiece":
                        i = new ChestPiece(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Gloves":
                        i = new Gloves(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Helmet":
                        i = new Helmet(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Shield":
                        i = new Shield(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Legs":
                        i = new Legs(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Axe":
                        i = new Axe(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Dagger":
                        i = new Dagger(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Sword":
                        i = new Sword(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Spear":
                        i = new Spear(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                    case "Mace":
                        i = new Mace(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType(), getStringInput(fileLoc), getStringInput(name), getStringInput(tooltip));
                        break;
                }
            } else if (promptResult.get() == buttonTypeTwo) { //Generic
                switch(resultString) {
                    case "Boots":
                        i = new Boots(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType());
                        break;
                    case "ChestPiece":
                        i = new ChestPiece(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType());
                        break;
                    case "Gloves":
                        i = new Gloves(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType());
                        break;
                    case "Helmet":
                        i = new Helmet(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType());
                        break;
                    case "Shield":
                        i = new Shield(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType());
                        break;
                    case "Legs":
                        i = new Legs(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getArmorType());
                        break;
                    case "Axe":
                        i = new Axe(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType());
                        break;
                    case "Dagger":
                        i = new Dagger(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType());
                        break;
                    case "Sword":
                        i = new Sword(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType());
                        break;
                    case "Spear":
                        i = new Spear(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType());
                        break;
                    case "Mace":
                        i = new Mace(getIntegerInput(attack), getIntegerInput(magic), getIntegerInput(defense), getIntegerInput(speed), getDoubleInput(weight),
                                getIntegerInput(hpBoost), getIntegerInput(manaBoost), getIntegerInput(gold), getRarity(), getWeaponType());
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
            Exit exit = new Exit(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()), getIntegerInput(nextExitX), getIntegerInput(nextExitY), getCardinal(), getStringInput(mapLoc));
            if(!mapParser.addItem(exit)) {
                displayIntersectionError();
            }
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

    private Cardinal getCardinal() {
        Cardinal c = null;

        List<String> choices = new ArrayList<>();
        choices.add("North");
        choices.add("South");
        choices.add("East");
        choices.add("West");

        String resultString = "";
        ChoiceDialog<String> dialog = new ChoiceDialog<>("North", choices);
        dialog.setTitle("Direction");
        dialog.setContentText("Choose direction the player should face when entering new map:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            resultString = result.get();
        }

        switch(resultString) {
            case "North":
                c = Cardinal.North;
                break;
            case "South":
                c = Cardinal.South;
                break;
            case "East":
                c = Cardinal.East;
                break;
            case "West":
                c = Cardinal.West;
                break;
            default:
                c = Cardinal.North;
                break;
        }

        return c;
    }


    private int getIntegerInput(String[] info) {
        int input = 0;

        boolean success = false;
        while (!success) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(info[0]);
            dialog.setContentText(info[1]);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    input = Integer.parseInt(result.get());
                    success = true;
                } catch(NumberFormatException e) {
                    success = false;
                }
            }
        }

        return input;
    }

    private double getDoubleInput(String[] info) {
        double input = 0.0;

        boolean success = false;
        while (!success) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(info[0]);
            dialog.setContentText(info[1]);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    input = Double.parseDouble(result.get());
                    success = true;
                } catch(NumberFormatException e) {
                    success = false;
                }
            }
        }

        return input;
    }

    private String getStringInput(String[] info) {
        String input = "Default name";

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(info[0]);
        dialog.setContentText(info[1]);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            input = result.get();
        }

        return input;
    }

    private class CoordinateListener implements ChangeListener<String> {

        private TextField source;

        public CoordinateListener(TextField source) {
            this.source = source;
        }

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
                source.setText(newValue.replaceAll("[^\\d]", ""));
            }

            int xCoord;
            try {
                xCoord = Integer.parseInt(x.getText());
            } catch (NumberFormatException e) {
                xCoord = 0;
            }

            int yCoord;
            try {
                yCoord = Integer.parseInt(y.getText());
            } catch (NumberFormatException e) {
                yCoord = 0;
            }

            marker.setX(xCoord);
            marker.setY(yCoord);
            drawMap();
        }
    }
}
