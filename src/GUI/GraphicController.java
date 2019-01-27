package GUI;

import controller.FarmController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.FarmAnimal;
import model.FarmAnimalType;
import model.PrimitiveProduct;
import model.PrimitiveProductType;
import model.request.BuyRequest;

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
        Media menuSound = new Media(new File("src/GUI/Textures/menuTextures/" +
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
        mainMenuTitle.setFont(Font.font("Chalkboard", 100));
        mainMenuTitle.setFill(Color.rgb(195, 207, 23));

        Rectangle selectLevelRectangle = new Rectangle(WIDTH / 2 - 70, 210, 300, 100);
        Text selectLevelText = new Text(WIDTH / 2 - 48, 270, "SELECT LEVEL");
        menuButtonMaker(selectLevelRectangle, selectLevelText, menu);

        Rectangle loadGameRectangle = new Rectangle(WIDTH / 2 - 70, 320, 300, 100);
        Text loadGameText = new Text(WIDTH / 2 - 32, 385, "LOAD GAME");
        menuButtonMaker(loadGameRectangle, loadGameText, menu);


        scene.setRoot(menu);
    }

    public void newGame() {
        game.getChildren().clear();
        game.getChildren().add(backGround);

        StringBuilder storageStringBuilder = new StringBuilder("/GUI/Textures/Service/Depot/");
        Image storageImage = new Image
                (serviceLevelImageSelector(farmController.getFarm().getStorage().getLevel(), storageStringBuilder));
        ImageView storageImageView = new ImageView(storageImage);
        storageImageView.setFitWidth(200);
        storageImageView.setFitHeight(150);
        storageImageView.relocate(WIDTH / 2 - 100, HEIGHT / 2 + 150);
        game.getChildren().add(storageImageView);

        StringBuilder helicopterStringBuilder = new StringBuilder("/GUI/Textures/Service/Helicopter/");
        Image helicopterImage = new Image
                (serviceLevelImageSelector(farmController.getFarm().getHelicopter().getLevel(), helicopterStringBuilder));
        //todo: Helicopter seems to be not initiated or its level isn't known
        ImageView helicopterImageView = new ImageView(helicopterImage);
        helicopterImageView.setFitHeight(150);
        helicopterImageView.setFitWidth(150);
        helicopterImageView.relocate(WIDTH / 2 + 120, HEIGHT / 2 + 150);
        game.getChildren().add(helicopterImageView);

        StringBuilder truckStringBuilder = new StringBuilder("/GUI/Textures/Service/Truck/");
        Image truckImage = new Image
                (serviceLevelImageSelector(farmController.getFarm().getTruck().getLevel(), truckStringBuilder));
        ImageView truckImageView = new ImageView(truckImage);
        truckImageView.setFitWidth(180);
        truckImageView.setFitHeight(130);
        truckImageView.relocate(WIDTH / 2 - 300, HEIGHT / 2 + 150);
        game.getChildren().add(truckImageView);

        farmController.getFarm().workshopAdder();
        StringBuilder cookieBakeryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/Cake (Cookie Bakery)/");
        Image cookieBakeryImage = new Image
                (workshopLevelImageSelector(farmController.getFarm().getWorkShops().get(0).getLevel(),
                        cookieBakeryStringBuilder));
        //todo I ADD the workshops JUST FOR TEST
        ImageView cookieBakeryImageView = new ImageView(cookieBakeryImage);
        workShopImageModify(cookieBakeryImageView, cookieBakeryImage);
        cookieBakeryImageView.relocate(WIDTH / 2 - 350, HEIGHT / 2 - 100);
        game.getChildren().add(cookieBakeryImageView);

        StringBuilder sewingFactoryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "CarnivalDress (Sewing Factory)/");
        Image sewingFactoryImage = new Image
                (workshopLevelImageSelector(farmController.getFarm().getWorkShops().get(1).getLevel(),
                        sewingFactoryStringBuilder));
        ImageView sewingFactoryImageView = new ImageView(sewingFactoryImage);
        workShopImageModify(sewingFactoryImageView, sewingFactoryImage);
        cookieBakeryImageView.relocate(WIDTH / 2 - 350, HEIGHT / 2);
        game.getChildren().add(sewingFactoryImageView);

        StringBuilder eggPowderPlantStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "DriedEggs (Egg Powder Plant)/");
        Image eggPowderPlantImage = new Image
                (workshopLevelImageSelector(farmController.getFarm().getWorkShops().get(2).getLevel(),
                        eggPowderPlantStringBuilder));
        ImageView eggPowderPlantImageView = new ImageView(eggPowderPlantImage);
        workShopImageModify(eggPowderPlantImageView, eggPowderPlantImage);
        eggPowderPlantImageView.relocate(WIDTH / 2 - 350, HEIGHT / 2 + 150);
        game.getChildren().add(eggPowderPlantImageView);

        StringBuilder cakeBakeryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "FlouryCake (Cake Bakery)/");
        Image cakeBakeryImage = new Image
                (workshopLevelImageSelector(farmController.getFarm().getWorkShops().get(3).getLevel(),
                        cakeBakeryStringBuilder));
        ImageView cakeBakeryImageView = new ImageView(cakeBakeryImage);
        workShopImageModify(cakeBakeryImageView, cakeBakeryImage);
        cakeBakeryImageView.relocate(WIDTH / 2 + 200, HEIGHT / 2 - 100);
        game.getChildren().add(cakeBakeryImageView);

        StringBuilder spinneryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "Spinnery (Spinnery)/");
        Image spinneryImage = new Image
                (workshopLevelImageSelector(farmController.getFarm().getWorkShops().get(4).getLevel(),
                        spinneryStringBuilder));
        ImageView spinneryImageView = new ImageView(spinneryImage);
        workShopImageModify(spinneryImageView, spinneryImage);
        spinneryImageView.relocate(WIDTH / 2 + 200, HEIGHT / 2);
        game.getChildren().add(spinneryImageView);

        StringBuilder weavingFactoryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "Weaving (Weaving Factory)/");
        Image weavingFactoryImage = new Image
                (workshopLevelImageSelector(farmController.getFarm().getWorkShops().get(5).getLevel(),
                        weavingFactoryStringBuilder));
        ImageView weavingFactoryImageView = new ImageView(weavingFactoryImage);
        workShopImageModify(weavingFactoryImageView, weavingFactoryImage);
        weavingFactoryImageView.relocate(WIDTH / 2 + 200, HEIGHT / 2 + 150);
        game.getChildren().add(weavingFactoryImageView);

        ImageView chickenBuyBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        chickenBuyBoard.relocate(20,20);
        chickenBuyBoard.setFitWidth(80);
        chickenBuyBoard.setFitHeight(100);
        farmAnimalBuyButton(chickenBuyBoard,FarmAnimalType.CHICKEN);
        game.getChildren().add(chickenBuyBoard);

        ImageView chickenIconBoard = new ImageView(new Image("/GUI/Textures/UI/Icons/Products/chicken.png"));
        chickenIconBoard.relocate(28,23);
        chickenIconBoard.setFitHeight(60);
        chickenIconBoard.setFitWidth(60);
        farmAnimalBuyButton(chickenIconBoard,FarmAnimalType.CHICKEN);
        game.getChildren().add(chickenIconBoard);

        Text chickenBuyPrice = new Text(30,103, Integer.toString(FarmAnimalType.CHICKEN.getBuyCost()));
        chickenBuyPrice.setFill(Color.rgb(195,207,23));
        chickenBuyPrice.setFont(Font.font("Chalkboard", FontWeight.BOLD,25));
        farmAnimalBuyButton(chickenBuyPrice,FarmAnimalType.CHICKEN);
        game.getChildren().add(chickenBuyPrice);


        ImageView sheepBuyBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        sheepBuyBoard.relocate(105,20);
        sheepBuyBoard.setFitWidth(80);
        sheepBuyBoard.setFitHeight(100);
        farmAnimalBuyButton(sheepBuyBoard,FarmAnimalType.SHEEP);
        game.getChildren().add(sheepBuyBoard);

        ImageView sheepBuyIcon = new ImageView(new Image("/GUI/Textures/UI/Icons/Products/sheep.png"));
        sheepBuyIcon.relocate(111,23);
        sheepBuyIcon.setFitWidth(60);
        sheepBuyIcon.setFitHeight(60);
        farmAnimalBuyButton(sheepBuyIcon, FarmAnimalType.SHEEP);
        game.getChildren().add(sheepBuyIcon);

        Text sheepBuyText = new Text(111,103,Integer.toString(FarmAnimalType.SHEEP.getBuyCost()));
        sheepBuyText.setFill(Color.rgb(195,207,23));
        sheepBuyText.setFont(Font.font("Chalkboard",FontWeight.BOLD,25));
        farmAnimalBuyButton(sheepBuyText, FarmAnimalType.SHEEP);
        game.getChildren().add(sheepBuyText);

        ImageView cowBuyBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        cowBuyBoard.relocate(190, 20);
        cowBuyBoard.setFitWidth(80);
        cowBuyBoard.setFitHeight(100);
        farmAnimalBuyButton(cowBuyBoard,FarmAnimalType.COW);
        game.getChildren().add(cowBuyBoard);

        ImageView cowBuyIcon = new ImageView(new Image("/GUI/Textures/UI/Icons/Products/brown_cow.png"));
        cowBuyIcon.relocate(195, 20);
        cowBuyIcon.setFitHeight(60);
        cowBuyIcon.setFitWidth(60);
        farmAnimalBuyButton(cowBuyIcon,FarmAnimalType.COW);
        game.getChildren().add(cowBuyIcon);

        Text cowButText = new Text(200, 103, Integer.toString(FarmAnimalType.COW.getBuyCost()));
        cowButText.setFill(Color.rgb(195,207,23));
        cowButText.setFont(Font.font("Chalkboard",FontWeight.BOLD,25));
        farmAnimalBuyButton(cowButText,FarmAnimalType.COW);
        game.getChildren().add(cowButText);

        ImageView coinBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        coinBoard.relocate(WIDTH/2 + 250, 30);
        coinBoard.setFitWidth(100);
        coinBoard.setFitHeight(120);
        game.getChildren().add(coinBoard);

        ImageView coinIcon = new ImageView(new Image("/GUI/Textures/gameButtons/coin.png"));
        coinIcon.relocate(WIDTH/2 + 270 , 40);
        coinIcon.setFitHeight(60);
        coinIcon.setFitWidth(60);
        game.getChildren().add(coinIcon);

        moneyTextUpdater();

        scene.setRoot(game);
    }

    public void farmAnimalBuyButton(Node buttonNode, FarmAnimalType farmAnimalType) {
        if (farmAnimalType.equals(FarmAnimalType.COW)) {
            buttonNode.setOnMouseClicked(event -> {
                farmController.buyAction(new BuyRequest(new FarmAnimal((int)Math.random()*30,(int)Math.random()*30,
                        FarmAnimalType.COW)));
                moneyTextUpdater();
            });
        } else if (farmAnimalType.equals(FarmAnimalType.CHICKEN)) {
            farmController.buyAction(new BuyRequest(new FarmAnimal((int)Math.random()*30,(int)Math.random()*30,
                    FarmAnimalType.CHICKEN)));
            moneyTextUpdater();
        } else {
            farmController.buyAction(new BuyRequest(new FarmAnimal((int)Math.random()*30,(int)Math.random()*30,
                    FarmAnimalType.SHEEP)));
            moneyTextUpdater();
        }
    }

    public void moneyTextUpdater() {
        Text moneyText = new Text(WIDTH/2 + 270, 130, Integer.toString(farmController.getFarm().getMoney()));
        moneyText.setFill(Color.WHITE);
        moneyText.setFont(Font.font("Chalkboard",FontWeight.BOLD,30));
        game.getChildren().add(moneyText);
    }

    public void workShopImageModify(ImageView workshopImageView, Image workshopImage) {
        workshopImageView.setViewport(new Rectangle2D(0, 0, workshopImage.getWidth() / 24,
                workshopImage.getHeight()));
        workshopImageView.setFitHeight(100);
        workshopImageView.setFitWidth(100);
    }

    public String workshopLevelImageSelector(int level, StringBuilder imagePathStringBuilder) {
        switch (level) {
            case 0:
                imagePathStringBuilder.append("01.png");
                break;
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
                break;
        }
        return imagePathStringBuilder.toString();
    }

    public String serviceLevelImageSelector(int level, StringBuilder imagePathStringBuilder) {
        switch (level) {
            //todo : add Level0
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

    public void gameButtonMaker(Rectangle buttonRectangle, Text buttonText, Group menuGroup) {
        ImagePattern onMouseEnterTexture = new ImagePattern(
                new Image("/GUI/Textures/gameButtons/buttonTexture.jpg")
        );
        ImagePattern onMouseExitTexture = new ImagePattern(
                new Image("/GUI/Textures/gameButtons/buttonTexture2.jpg")
        );
        buttonRectangle.setFill(onMouseExitTexture);
        buttonRectangle.setStroke(Color.rgb(255, 255, 255));
        buttonRectangle.setStrokeWidth(5);
        buttonRectangle.setArcHeight(1);
        buttonRectangle.setArcWidth(1);
        buttonRectangle.setOnMouseEntered(event -> {
            buttonRectangle.setFill(onMouseEnterTexture);
        });
        buttonRectangle.setOnMouseExited(event -> {
            buttonRectangle.setFill(onMouseExitTexture);
        });
        buttonText.setOnMouseEntered(event -> {
            buttonRectangle.setFill(onMouseEnterTexture);
        });
        buttonText.setOnMouseExited(event -> {
            buttonRectangle.setFill(onMouseExitTexture);
        });
    }

    public void menuButtonMaker(Rectangle buttonRectangle, Text buttonText, Group menuGroup) {
        ImagePattern onMouseEnterTexture = new ImagePattern(
                new Image("/GUI/Textures/menuTextures/buttonGradient2.jpg"));
        ImagePattern onMouseExitTexture = new ImagePattern(new Image("/GUI/Textures/menuTextures/buttonGradient.jpg"));
        buttonRectangle.setFill(onMouseExitTexture);
        buttonRectangle.setStroke(Color.rgb(207, 101, 6));
        buttonRectangle.setStrokeWidth(5);
        buttonRectangle.setArcWidth(0.5);
        buttonRectangle.setArcHeight(0.5);
        buttonRectangle.setOnMouseEntered(event -> {
            buttonRectangle.setFill(onMouseEnterTexture);
        });
        buttonRectangle.setOnMouseExited(event -> {
            buttonRectangle.setFill(onMouseExitTexture);
        });
        buttonText.setOnMouseEntered(event -> {
            buttonRectangle.setFill(onMouseEnterTexture);
        });
        buttonText.setOnMouseExited(event -> {
            buttonRectangle.setFill(onMouseExitTexture);
        });


        buttonText.setFill(Color.rgb(207, 0, 2));
        buttonText.setFont(Font.font("Chalkboard", 40));
        menuGroup.getChildren().addAll(buttonRectangle, buttonText);
    }

}
