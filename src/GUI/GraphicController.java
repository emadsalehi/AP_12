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
import model.*;
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
                    game.getChildren().clear();
                    game.getChildren().add(backGround);
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
                            ArrayList<Product> products = cells[i][j].getProducts();
                            for (Product product : products) {
                                ImageView productImageView = getProductImageView(product);
                                game.getChildren().add(productImageView);
                                productImageView.setOnMouseClicked(event -> {
                                    game.getChildren().remove(productImageView);
                                });
                            }
                        }
                    }

                }
            }
        };
        gameRunner.start();
    }

    public ImageView getProductImageView(Product product) {
        StringBuilder pathToImage = new StringBuilder("src/GUI/Textures/Products/");
        if (product instanceof PrimitiveProduct) {
            if (((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.EGG)
                pathToImage.append("Egg/normal_2.png");
            else if (((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.MILK)
                pathToImage.append("Milk.png");
            else if (((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.WOOL)
                pathToImage.append("Wool/normal.png");
            else if (((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.PLUME)
                pathToImage.append("Plume/normal.png");
            else if (((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.FLOUR)
                pathToImage.append("Flour.png");
        } else if (product instanceof SecondaryProduct){
            if (((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.CAKE)
                pathToImage.append("FlouryCake.png");
            else if (((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.EGG_POWDER)
                pathToImage.append("EggPowder.png");
            else if (((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.FABRIC)
                pathToImage.append("Fabric.png");
            else if (((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.CLOTHES)
                pathToImage.append("CarnivalDress.png");
            else if (((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.THREAD)
                pathToImage.append("Sewing.png");
            else if (((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.COOKIE)
                pathToImage.append("Cake.png");
        }
        try {
            ImageView productImageView = new ImageView(new Image(new FileInputStream(pathToImage.toString())));
            return productImageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
