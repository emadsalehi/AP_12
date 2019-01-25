package GUI;

import controller.FarmController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GraphicController extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final String pathToBackGroundImage = "src/GUI/Textures/back.png";
    private Group menu = new Group();
    private Group game = new Group();
    private Scene scene = new Scene(menu, WIDTH, HEIGHT);
    private ImageView backGround = new ImageView(new Image(new FileInputStream(pathToBackGroundImage)));
    private FarmController farmController = new FarmController();

    public GraphicController() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        newMenu();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void newMenu() {
        String backGroundPath = "src/GUI/Textures/menuTextures/cartoon-of-farm-background-vector-8546642.jpg";
        ImageView mainMenuBackground = null;
        try {
            mainMenuBackground = new ImageView(new Image(new FileInputStream(backGroundPath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mainMenuBackground.setFitHeight(HEIGHT);
        mainMenuBackground.setFitWidth(WIDTH);
        menu.getChildren().add(mainMenuBackground);

        Text mainMenuTitle = new Text(WIDTH / 2 - 300, 100, "FARM FRENZY");
        mainMenuTitle.setFont(Font.font("Chalkboard",100));
        mainMenuTitle.setFill(Color.rgb(195,207,23));
//        mainMenuTitle.set

        Rectangle selectLevelRectangle = new Rectangle(WIDTH/2 - 200, 200, 400, 70);
        Text selectLevelText = new Text(WIDTH/2 - 170, 250, "SELECT LEVEL");
        menuButtonMaker(selectLevelRectangle,selectLevelText,menu);

        Rectangle loadGameRectangle = new Rectangle(WIDTH/2 - 200, 300, 400,70);
        Text loadGameText = new Text(WIDTH/2 - 170, 350, "LOAD GAME");
        menuButtonMaker(loadGameRectangle,loadGameText,menu);

        Rectangle 



        scene.setRoot(menu);
    }

    public void newGame() {
        this.game.getChildren().clear();
        this.game.getChildren().add(backGround);
    }

    public void menuButtonMaker(Rectangle buttonRectangle, Text buttonText, Group menuGroup) {
        buttonRectangle.setFill(Color.rgb(100,100,100));
        buttonRectangle.setFill(new ImagePattern(new Image(
                "/GUI/Textures/menuTextures/buttonGradient.jpg"
        )));
        buttonRectangle.setStroke(Color.rgb(207,101,6));
        buttonRectangle.setStrokeWidth(5);
        buttonRectangle.setArcWidth(0.5);
        buttonRectangle.setArcHeight(0.5);

        buttonText.setFill(Color.rgb(207,0,2));
        buttonText.setFont(Font.font("Chalkboard",50));
        menuGroup.getChildren().addAll(buttonRectangle,buttonText);
    }

}
