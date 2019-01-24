package GUI;

import controller.FarmController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Animal;
import model.Cell;
import model.Farm;
import model.request.TurnRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GraphicController extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private Group menu = new Group();
    private Group game = new Group();
    private Scene scene = new Scene(menu, WIDTH, HEIGHT);
    private int timeConstant = 1000;
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
        AnimationTimer gameRunner = new AnimationTimer() {
            private long lastTime = 0;
            private double time = 0;
            private long second = 1000000000;

            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now > lastTime + second / timeConstant * 1000) {
                    lastTime = now;
                    time += 1;
                    farmController.turnAction(new TurnRequest(1));
                    Farm farm = farmController.getFarm();
                    Cell[][] cells = farm.getCells();
                    for (int i = 0; i < 30; i++) {
                        for (int j = 0; j < 30; j++) {
                            ArrayList<Animal> animals = cells[i][j].getAnimals();
                            ArrayList<Animation> animations = new ArrayList<>();
                            for (Animal animal : animals) {
                                Animation animation = new SpriteAnimal(animal, timeConstant);
                                animation.setCycleCount(animal.getSpeed());
                                animations.add(animation);
                            }
                            for (Animation animation : animations) {
                                animation.play();
                            }
                            
                        }
                    }
                }
            }
        };
        gameRunner.start();
    }
}
