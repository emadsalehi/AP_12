package GUI;

import controller.FarmController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
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
        newGame();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void newMenu() {
        Media menuSound = new Media (new File("src/GUI/Textures/menuTextures/" +
                "The Trouble Notes - Barney Rubble - Daytrotter Session - 11 21 2018.mp3").toURI().toString());
        MediaPlayer mediaPlayerSound = new MediaPlayer(menuSound);
        mediaPlayerSound.play();
        mediaPlayerSound.setAutoPlay(true);
        MediaView menuMediaView = new MediaView(mediaPlayerSound);
        menu.getChildren().add(menuMediaView);
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

        Rectangle selectLevelRectangle = new Rectangle(WIDTH/2 - 70, 210, 300, 100);
        Text selectLevelText = new Text(WIDTH/2 - 48, 270, "SELECT LEVEL");
        menuButtonMaker(selectLevelRectangle,selectLevelText,menu);

        Rectangle loadGameRectangle = new Rectangle(WIDTH/2 - 70, 320, 300,100);
        Text loadGameText = new Text(WIDTH/2 - 32, 385, "LOAD GAME");
        menuButtonMaker(loadGameRectangle,loadGameText,menu);




        scene.setRoot(menu);
    }

    public void newGame() {
        game.getChildren().clear();
        game.getChildren().add(backGround);

        StringBuilder storageStringBuilder = new StringBuilder("/GUI/Textures/Service/Depot/");
        Image storageImage = new Image
                (levelImageSelector(farmController.getFarm().getStorage().getLevel(),storageStringBuilder));
        ImageView storageImageView = new ImageView(storageImage);
        storageImageView.setFitWidth(200);
        storageImageView.setFitHeight(150);
        storageImageView.relocate(WIDTH/2 -100, HEIGHT/2 + 150);
        game.getChildren().add(storageImageView);

        StringBuilder helicopterStringBuilder = new StringBuilder("/GUI/Textures/Service/Helicopter/");
        Image helicopterImage = new Image
                (levelImageSelector(farmController.getFarm().getHelicopter().getLevel(),helicopterStringBuilder));
        //todo: Helicopter seems to be not initiated or its level isn't known
        ImageView helicopterImageView = new ImageView(helicopterImage);
        helicopterImageView.setFitHeight(150);
        helicopterImageView.setFitWidth(150);
        helicopterImageView.relocate(WIDTH/2 + 120 , HEIGHT/2 + 150);
        game.getChildren().add(helicopterImageView);

        StringBuilder truckStringBuilder = new StringBuilder("/GUI/Textures/Service/Truck/");
        Image truckImage = new Image
                (levelImageSelector(farmController.getFarm().getTruck().getLevel(), truckStringBuilder));
        ImageView truckImageView = new ImageView(truckImage);
        truckImageView.setFitWidth(180);
        truckImageView.setFitHeight(130);
        truckImageView.relocate(WIDTH/2 -300, HEIGHT/2 + 150);
        game.getChildren().add(truckImageView);




        scene.setRoot(game);
    }

    public String levelImageSelector (int level, StringBuilder imagePathStringBuilder) {
        switch (level) {
            case 1:
                imagePathStringBuilder.append("01.png");
                break;
            case 2:
                imagePathStringBuilder.append("02.png");
                break;
            case 3:
                imagePathStringBuilder.append("03.png");
                break;
            default:
                imagePathStringBuilder.append("04.png");
        }
        return imagePathStringBuilder.toString();
    }

    public void menuButtonMaker(Rectangle buttonRectangle, Text buttonText, Group menuGroup) {
        ImagePattern onMouseEnterTexture = new ImagePattern(
                new Image("/GUI/Textures/menuTextures/buttonGradient2.jpg"));
        ImagePattern onMouseTexture = new ImagePattern(new Image("/GUI/Textures/menuTextures/buttonGradient.jpg"));
        buttonRectangle.setFill(Color.rgb(100,100,100));
        buttonRectangle.setFill(onMouseTexture);
        buttonRectangle.setStroke(Color.rgb(207,101,6));
        buttonRectangle.setStrokeWidth(5);
        buttonRectangle.setArcWidth(0.5);
        buttonRectangle.setArcHeight(0.5);
        buttonRectangle.setOnMouseEntered(event -> {
            buttonRectangle.setFill(onMouseEnterTexture);
        });
        buttonRectangle.setOnMouseExited(event -> {
            buttonRectangle.setFill(onMouseTexture);
        });
        buttonText.setOnMouseEntered(event -> {
            buttonRectangle.setFill(onMouseEnterTexture);
        });
        buttonText.setOnMouseExited(event -> {
            buttonRectangle.setFill(onMouseTexture);
        });



        buttonText.setFill(Color.rgb(207,0,2));
        buttonText.setFont(Font.font("Chalkboard",40));
        menuGroup.getChildren().addAll(buttonRectangle,buttonText);
    }

}
