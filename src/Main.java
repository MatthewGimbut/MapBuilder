import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(new MapBuilderController(primaryStage), 1700, 760);

        Image icon = new Image("file:Images\\Nature\\Tree.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Map Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
