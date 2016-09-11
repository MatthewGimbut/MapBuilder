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
import sprites.GenericObstacle;
import sprites.Sprite;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class MapBuilderController extends BorderPane {

    private Stage primaryStage;
    private final String BACKGROUND_STRING = "-fx-background-position: center center; " +
            "-fx-background-repeat: stretch;";
    private GraphicsContext gc;
    private ObservableList<String> categories;
    private MapParser mapParser;
    private final String GENERIC_DIRECTORY = "Sprites\\Generic Obstacles";
    private final String LOOTABLE_DIRECTORY = "Sprites\\Lootables";
    private final String ENEMY_DIRECTORY = "Sprites\\Enemies";
    private final String NEUTRAL_DIRECTORY = "Sprites\\Neutral NPCs";
    private final String MISC_DIRECTORY = "Sprites\\Miscellaneous";
    private final String STRUCTURE_DIRECTORY = "Sprites\\Structures";
    private final String ITEM_DIRECTORY = "Sprites\\Items";

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
        this.categories = FXCollections.observableArrayList("Generic Obstacles", "Lootables", "Enemies", "Neutral NPCs", "Miscellaneous", "Structures", "Items");
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

                    break;
                case "Enemies":

                    break;
                case "Neutral NPCs":

                    break;
                case "Miscellaneous":

                    break;
                case "Structures":

                    break;
                case "Items":

                    break;
            }
            drawMap();
        });

        categoryChoice.setItems(categories);

        categoryChoice.valueProperty().addListener(event -> {
            switch(categoryChoice.getSelectionModel().getSelectedItem()) {
                case"Generic Obstacles":
                    imageFlow.getChildren().clear();
                    loadImageArray(GENERIC_DIRECTORY);
                    break;
                case "Lootables":
                    imageFlow.getChildren().clear();
                    loadImageArray(LOOTABLE_DIRECTORY);
                    break;
                case "Enemies":
                    imageFlow.getChildren().clear();
                    loadImageArray(ENEMY_DIRECTORY);
                    break;
                case "Neutral NPCs":
                    imageFlow.getChildren().clear();
                    loadImageArray(NEUTRAL_DIRECTORY);
                    break;
                case "Miscellaneous":
                    imageFlow.getChildren().clear();
                    loadImageArray(MISC_DIRECTORY);
                    break;
                case "Structures":
                    imageFlow.getChildren().clear();
                    loadImageArray(STRUCTURE_DIRECTORY);
                    break;
                case "Items":
                    imageFlow.getChildren().clear();
                    loadImageArray(ITEM_DIRECTORY);
                    break;
            }
        });

        undo.setOnAction(event -> {
            removedItems.push(mapParser.getMapItems().get(mapParser.getMapItems().size()-1));
            mapParser.getMapItems().remove(mapParser.getMapItems().size()-1);
            drawMap();
        });

        redo.setOnAction(event -> {
            Sprite sprite = removedItems.pop();
            if(!mapParser.addItem(sprite)) removedItems.push(sprite);
            drawMap();
        });

        this.setCenter(anchor);
    }

    private void generateGenericObstacle(String imageLoc) {
        GenericObstacle go = new GenericObstacle(0, 0, imageLoc);

        go.setX(Integer.parseInt(x.getText()));
        go.setY(Integer.parseInt(y.getText()));

        if(!mapParser.addItem(go)) {
            displayIntersectionError();
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
}
