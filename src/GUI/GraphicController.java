package GUI;

import controller.FarmController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GraphicController extends Application {

    private Group menu = new Group();
    private Group game = new Group();
    private Scene scene = new Scene(menu, 800, 600);
    private final String pathToBackGroundImage = "D:\\University\\AP\\Project\\AP_12\\src\\GUI\\Textures\\back.png";
    private ImageView backGround = new ImageView(new Image(new FileInputStream(pathToBackGroundImage)));
    private FarmController farmController = new FarmController();

    public GraphicController() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        newGame();
        scene.setRoot(game);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void newMenu() {

    }

    public void newGame() {
        this.game.getChildren().clear();
        this.game.getChildren().add(backGround);
    }

}
