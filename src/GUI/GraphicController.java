package GUI;

import controller.FarmController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import javafx.util.Duration;
import model.Cell;
import model.*;
import model.request.*;
import network.NetworkController;
import network.Reader;
import network.Writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicController extends Application {
    private static TextArea chatArea;
    private final int WIDTH = Utils.sceneWidth;
    private final int HEIGHT = Utils.sceneHeight;
    private final Image grassImage = new Image(new FileInputStream("src/GUI/Textures/Grass/grass1.png"));
    private final String pathToBackGroundImage = "src/GUI/Textures/back.png";
    private Group menu = new Group();
    private Group game = new Group();
    private Group border = new Group();
    private Group multiplayer = new Group();
    private Group leaderBoardGroup = new Group();
    private Group chatGroup = new Group();
    private Scene scene = new Scene(menu, WIDTH, HEIGHT);
    private int timeConstant = 1000;
    private ImageView backGround = new ImageView(new Image(new FileInputStream(pathToBackGroundImage)));
    private FarmController farmController = new FarmController();
    private NetworkController networkController = new NetworkController();
    private Reader reader;
    private Writer writer;


    public GraphicController() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        newMenu();
        scene.setRoot(menu);
//        scene.setRoot(border);
        WriteThread writeThread = new WriteThread(farmController);
        writeThread.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void newMenu() {
        menu.getChildren().clear();
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

        Rectangle newGameRectangle = new Rectangle(WIDTH / 2 - 70, 210, 300, 70);
        Text newGameText = new Text(WIDTH / 2 - 30, 260, "NEW GAME");
        menuButtonMaker(newGameRectangle, newGameText, menu);
        newGameRectangle.setOnMouseClicked(event -> {
            newGame();
            scene.setRoot(border);
        });
        newGameText.setOnMouseClicked(event -> {
            mediaPlayerSound.stop();
            newGame();
            scene.setRoot(border);
        });

        Rectangle loadGameRectangle = new Rectangle(WIDTH / 2 - 70, 290, 300, 70);
        Text loadGameText = new Text(WIDTH / 2 - 32, 340, "LOAD GAME");
        menuButtonMaker(loadGameRectangle, loadGameText, menu);

        Rectangle multiPlayerRectangle = new Rectangle(WIDTH / 2 - 70, 370, 300, 70);
        Text multiPlayerText = new Text(WIDTH / 2 - 50, 420, "MULTIPLAYER");
        menuButtonMaker(multiPlayerRectangle, multiPlayerText, menu);
        multiPlayerRectangle.setOnMouseClicked(event -> {
            mediaPlayerSound.stop();
            multiplayer();
            scene.setRoot(multiplayer);
        });
        multiPlayerText.setOnMouseClicked(event -> {
            mediaPlayerSound.stop();
            multiplayer();
            scene.setRoot(multiplayer);
        });
    }

    public void multiplayer() {
        multiplayer.getChildren().clear();
        String backGroundPath = "src/GUI/Textures/menuTextures/cartoon-of-farm-background-vector-8546642.jpg";
        ImageView multiplayerBackground = null;
        try {
            multiplayerBackground = new ImageView(new Image(new FileInputStream(backGroundPath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        multiplayerBackground.setFitHeight(HEIGHT);
        multiplayerBackground.setFitWidth(WIDTH);

        RadioButton hostRadioButton = new RadioButton("Host");
        hostRadioButton.relocate(WIDTH / 2 - 68, 220);
        hostRadioButton.setFont(Font.font("Chalkboard", FontWeight.BOLD, 20));
        RadioButton joinRadioButton = new RadioButton("Join");
        joinRadioButton.relocate(WIDTH / 2 - 68, 250);
        joinRadioButton.setFont(Font.font("Chalkboard", FontWeight.BOLD, 20));
        final ToggleGroup hostOrJoin = new ToggleGroup();
        hostRadioButton.setToggleGroup(hostOrJoin);
        joinRadioButton.setToggleGroup(hostOrJoin);

        Text portLabel = new Text("Port: ");
        portLabel.relocate(WIDTH / 2 - 68, 290);
        portLabel.setFont(Font.font("Chalkboard", FontWeight.BOLD, 20));
        TextField portTextField = new TextField("ex: 80");
        portTextField.relocate(WIDTH / 2 - 20, 283);

        Text iPLabel = new Text("IP: ");
        iPLabel.relocate(WIDTH / 2 - 68, 330);
        iPLabel.setFont(Font.font("Chalkboard", FontWeight.BOLD, 20));
        TextField iPTextField = new TextField("ex: 100.100.100.100");
        iPTextField.relocate(WIDTH / 2 - 40, 323);
        iPTextField.setEditable(false);

        Text userNameLabel = new Text("Username: ");
        userNameLabel.relocate(WIDTH / 2 - 68, 370);
        userNameLabel.setFont(Font.font("Chalkboard", FontWeight.BOLD, 20));
        TextField userNameTextField = new TextField("ex: Example1");
        userNameTextField.relocate(WIDTH / 2 + 27, 363);

        hostOrJoin.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) hostOrJoin.getSelectedToggle();

            if (selectedRadioButton != null) {
                String selectedString = selectedRadioButton.getText();
                if (selectedString.equals("Host")) {
                    portTextField.setText("8050");
                    iPTextField.setEditable(false);
                    iPTextField.setText("localhost");
                } else if (selectedString.equals("Join")) {
                    portTextField.setText("8060");
                    iPTextField.setText("ex: 100.100.100.100");
                    iPTextField.setEditable(true);
                }
            }
        });

        Rectangle nextRectangle = new Rectangle(WIDTH / 2 + 70, 410, 70, 40);
        Text nextText = new Text(WIDTH / 2 + 73, 440, "Next");

        Rectangle backRectangle = new Rectangle(WIDTH / 2 - 20, 410, 70, 40);
        Text backText = new Text(WIDTH / 2 - 17, 440, "Back");


        multiplayer.getChildren().addAll(multiplayerBackground, hostRadioButton, joinRadioButton
                , portLabel, portTextField, iPLabel, iPTextField, userNameLabel, userNameTextField);

        menuButtonMaker(nextRectangle, nextText, multiplayer);
        menuButtonMaker(backRectangle, backText, multiplayer);
        nextText.setFont(Font.font("Chalkboard", 30));
        backText.setFont(Font.font("Chalkboard", 30));

        nextText.setOnMouseClicked(event -> {
            boolean isHost;
            isHost = hostRadioButton.isSelected();
            networkController.addProfileAction(isHost, Integer.valueOf(portTextField.getText()), iPTextField.getText(),
                    userNameTextField.getText(), this);
            this.farmController = networkController.getProfile().getFarmController();
            newGame();
            Rectangle chatButtonRectangle = new Rectangle(47, HEIGHT - 90, 70, 30);
            Text chatButtonText = new Text(60, HEIGHT - 70, "Chat");
            gameButtonMaker(chatButtonRectangle, chatButtonText);
            chatButtonText.setOnMouseClicked(event1 -> newChat());
            border.getChildren().addAll(chatButtonRectangle, chatButtonText);
            Rectangle leaderBoardRectangle = new Rectangle(20, HEIGHT - 50, 140, 30);
            Text leaderBoardText = new Text(33, HEIGHT - 28, "LeaderBoard");
            gameButtonMaker(leaderBoardRectangle, leaderBoardText);
            leaderBoardText.setOnMouseClicked(event1 -> newLeaderBoard());
            Rectangle marketRectangle = new Rectangle(WIDTH / 2 - 100, 20, 100, 40);
            Text marketText = new Text(WIDTH / 2 - 85, 45, "MARKET");
            TextField friendTextField = new TextField("friend name");
            friendTextField.setMinWidth(40);
            friendTextField.setMaxWidth(100);
            friendTextField.setMinHeight(30);
            friendTextField.setMaxHeight(30);
            friendTextField.relocate(WIDTH - 270, 20);
            Rectangle addFriendRectangle = new Rectangle(WIDTH - 284, 60, 130, 30);
            Text addFriendText = new Text(WIDTH - 275, 80, "ADD FRIEND");
            gameButtonMaker(addFriendRectangle, addFriendText);
            addFriendRectangle.setOnMouseClicked(event1 -> {

            });
            addFriendText.setOnMouseClicked(event1 -> {

            });
            Rectangle friendsRectangle = new Rectangle(WIDTH - 284, 100, 130, 30);
            Text friendsText = new Text(WIDTH - 260, 120, "FRIENDS");
            gameButtonMaker(friendsRectangle, friendsText);
            friendsRectangle.setOnMouseClicked(event1 -> {

            });
            friendsText.setOnMouseClicked(event1 -> {
                
            });
            border.getChildren().addAll(addFriendRectangle, addFriendText, friendTextField, friendsRectangle, friendsText);

            gameButtonMaker(marketRectangle, marketText);
            border.getChildren().addAll(marketRectangle, marketText);
            border.getChildren().addAll(leaderBoardRectangle, leaderBoardText);

            scene.setRoot(border);
        });

        backRectangle.setOnMouseClicked(event -> {
            newMenu();
            scene.setRoot(menu);
        });
        backText.setOnMouseClicked(event -> {
            newMenu();
            scene.setRoot(menu);
        });
    }


    public void newGame() {
        Farm farm = farmController.getFarm();
        border.getChildren().clear();
        border.getChildren().add(backGround);
        StringBuilder storageStringBuilder = new StringBuilder("/GUI/Textures/Service/Depot/");
        Image storageImage = new Image
                (serviceLevelImageSelector(farm.getStorage().getLevel(), storageStringBuilder));
        ImageView storageImageView = new ImageView(storageImage);
        storageImageView.setFitWidth(200);
        storageImageView.setFitHeight(150);
        storageImageView.relocate(WIDTH / 2 - 100, HEIGHT / 2 + 130);
        border.getChildren().add(storageImageView);
        storageImageView.setOnMouseClicked(event -> {
            Stage storageStage = new Stage();
            Group storageGroup = new Group();
            Scene storageScene = new Scene(storageGroup, 600, 900);
            HashMap<String, Integer> storageHashMap = storageHashMapMaker();
            StringBuilder storageStringBuilder1 = new StringBuilder();
            for (Map.Entry<String, Integer> entry : storageHashMap.entrySet()) {
                storageStringBuilder1.append(entry.getKey() + ": " + entry.getKey() + "\n");
            }
            TextArea storageTextArea = new TextArea(storageStringBuilder1.toString());
            storageTextArea.setMinHeight(900);
            storageTextArea.setMinWidth(600);
            storageTextArea.setEditable(false);
            storageGroup.getChildren().add(storageTextArea);

            storageStage.setScene(storageScene);
            storageStage.show();
        });

        StringBuilder helicopterStringBuilder = new StringBuilder("/GUI/Textures/Service/Helicopter/");
        Image helicopterImage = new Image
                (serviceLevelImageSelector(farm.getHelicopter().getLevel(), helicopterStringBuilder));
        //todo: Helicopter seems to be not initiated or its level isn't known
        ImageView helicopterImageView = new ImageView(helicopterImage);
        helicopterImageView.setFitHeight(150);
        helicopterImageView.setFitWidth(150);
        helicopterImageView.relocate(WIDTH / 2 + 100, HEIGHT / 2 + 125);
        border.getChildren().add(helicopterImageView);

        StringBuilder truckStringBuilder = new StringBuilder("/GUI/Textures/Service/Truck/");
        Image truckImage = new Image
                (serviceLevelImageSelector(farm.getTruck().getLevel(), truckStringBuilder));
        ImageView truckImageView = new ImageView(truckImage);
        truckImageView.setFitWidth(130);
        truckImageView.setFitHeight(130);
        truckImageView.relocate(WIDTH / 2 - 240, HEIGHT / 2 + 140);
        border.getChildren().add(truckImageView);

        StringBuilder cookieBakeryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/Cake (Cookie Bakery)/");
        Image cookieBakeryImage = new Image
                (workshopLevelImageSelector(farm.getWorkShops().get(0).getLevel(),
                        cookieBakeryStringBuilder));
        //todo I ADD the workshops JUST FOR TEST
        ImageView cookieBakeryImageView = new ImageView(cookieBakeryImage);
        workShopImageModify(cookieBakeryImageView, cookieBakeryImage);
        cookieBakeryImageView.relocate(WIDTH / 2 - 350, HEIGHT / 2 - 140);
        border.getChildren().add(cookieBakeryImageView);
        cookieBakeryImageView.setOnMouseClicked(event -> {
            if (farmController.startAction(new StartRequest("cookiebakery"))) {
                workshopWellAnimationBuilder(cookieBakeryImage, cookieBakeryImageView, 0);
            }
        });
        if (farmController.getFarm().getWorkShops().get(0).getLevel() == 0) {
            cookieBakeryImageView.setVisible(false);

        }
        Rectangle timeRectangle = new Rectangle(WIDTH - 100, HEIGHT - 80, 100, 80);
        Text timeText = new Text(WIDTH - 70, HEIGHT - 35, "00:00");
        gameButtonMaker(timeRectangle, timeText);
        AnimationTimer timeAnimationtimer = new AnimationTimer() {
            int time = 0;
            long lastCalled = 0;
            long second = 1000000000;

            @Override
            public void handle(long now) {
                if (now > lastCalled + second) {
                    lastCalled = now;
                    time++;
                    timeText.setText(timeSetter(time));
                }
            }
        };
        timeAnimationtimer.start();
        border.getChildren().addAll(timeRectangle, timeText);
        StringBuilder sewingFactoryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "CarnivalDress (Sewing Factory)/");
        Image sewingFactoryImage = new Image
                (workshopLevelImageSelector(farm.getWorkShops().get(1).getLevel(),
                        sewingFactoryStringBuilder));
        ImageView sewingFactoryImageView = new ImageView(sewingFactoryImage);
        workShopImageModify(sewingFactoryImageView, sewingFactoryImage);
        sewingFactoryImageView.relocate(WIDTH / 2.0 - 350, HEIGHT / 2.0);
        border.getChildren().add(sewingFactoryImageView);
        sewingFactoryImageView.setOnMouseClicked(event -> {
            if (farmController.startAction(new StartRequest("sewingfactory"))) {
                workshopWellAnimationBuilder(sewingFactoryImage, sewingFactoryImageView, 1);
            }
        });
        if (farmController.getFarm().getWorkShops().get(1).getLevel() == 0) {
            sewingFactoryImageView.setOpacity(0);
        }

        StringBuilder eggPowderPlantStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "DriedEggs (Egg Powder Plant)/");
        Image eggPowderPlantImage = new Image
                (workshopLevelImageSelector(farm.getWorkShops().get(2).getLevel(),
                        eggPowderPlantStringBuilder));
        ImageView eggPowderPlantImageView = new ImageView(eggPowderPlantImage);
        workShopImageModify(eggPowderPlantImageView, eggPowderPlantImage);
        eggPowderPlantImageView.relocate(WIDTH / 2 - 350, HEIGHT / 2 + 100);
        border.getChildren().add(eggPowderPlantImageView);
        eggPowderPlantImageView.setOnMouseClicked(event -> {
            if (farmController.startAction(new StartRequest("eggpowderplant"))) {
                workshopWellAnimationBuilder(eggPowderPlantImage, eggPowderPlantImageView, 2);
            }
        });
        if (farmController.getFarm().getWorkShops().get(2).getLevel() == 0) {
            eggPowderPlantImageView.setVisible(false);

        }

        StringBuilder cakeBakeryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "FlouryCake (Cake Bakery)/");
        Image cakeBakeryImage = new Image
                (workshopLevelImageSelector(farm.getWorkShops().get(3).getLevel(),
                        cakeBakeryStringBuilder));
        ImageView cakeBakeryImageView = new ImageView(cakeBakeryImage);
        workShopImageModify(cakeBakeryImageView, cakeBakeryImage);
        cakeBakeryImageView.relocate(WIDTH / 2 + 200, HEIGHT / 2 - 140);
        border.getChildren().add(cakeBakeryImageView);
        cakeBakeryImageView.setOnMouseClicked(event -> {
            if (farmController.startAction(new StartRequest("cakebakery"))) {
                workshopWellAnimationBuilder(cakeBakeryImage, cakeBakeryImageView, 2);
            }
        });
        if (farmController.getFarm().getWorkShops().get(3).getLevel() == 0) {
            cakeBakeryImageView.setVisible(false);

        }
        StringBuilder spinneryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "Spinnery (Spinnery)/");
        Image spinneryImage = new Image
                (workshopLevelImageSelector(farm.getWorkShops().get(4).getLevel(),
                        spinneryStringBuilder));
        ImageView spinneryImageView = new ImageView(spinneryImage);
        workShopImageModify(spinneryImageView, spinneryImage);
        spinneryImageView.relocate(WIDTH / 2 + 200, HEIGHT / 2);
        border.getChildren().add(spinneryImageView);
        spinneryImageView.setOnMouseClicked(event -> {
            if (farmController.startAction(new StartRequest("spinnery"))) {
                workshopWellAnimationBuilder(spinneryImage, spinneryImageView, 2);
            }
        });
        if (farmController.getFarm().getWorkShops().get(4).getLevel() == 0) {
            spinneryImageView.setVisible(false);
        }


        StringBuilder weavingFactoryStringBuilder = new StringBuilder("/GUI/Textures/Workshops/" +
                "Weaving (Weaving Factory)/");
        Image weavingFactoryImage = new Image
                (workshopLevelImageSelector(farm.getWorkShops().get(5).getLevel(),
                        weavingFactoryStringBuilder));
        ImageView weavingFactoryImageView = new ImageView(weavingFactoryImage);
        workShopImageModify(weavingFactoryImageView, weavingFactoryImage);
        weavingFactoryImageView.relocate(WIDTH / 2 + 200, HEIGHT / 2 + 100);
        border.getChildren().add(weavingFactoryImageView);
        weavingFactoryImageView.setOnMouseClicked(event -> {
            if (farmController.startAction(new StartRequest("weavingfactory"))) {
                workshopWellAnimationBuilder(weavingFactoryImage, weavingFactoryImageView, 2);
            }
        });
        if (farmController.getFarm().getWorkShops().get(5).getLevel() == 0) {
            weavingFactoryImageView.setVisible(false);
        }

        ImageView coinBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        coinBoard.relocate(WIDTH / 2 + 250, 30);
        coinBoard.setFitWidth(100);
        coinBoard.setFitHeight(120);
        border.getChildren().add(coinBoard);

        Text moneyText = new Text(WIDTH / 2 + 270, 130, Integer.toString(farmController.getFarm().getMoney()));
        moneyTextUpdater(moneyText);
        border.getChildren().add(moneyText);


        ImageView coinIcon = new ImageView(new Image("/GUI/Textures/gameButtons/coin.png"));
        coinIcon.relocate(WIDTH / 2 + 270, 40);
        coinIcon.setFitHeight(60);
        coinIcon.setFitWidth(60);
        border.getChildren().add(coinIcon);

        ImageView chickenBuyBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        chickenBuyBoard.relocate(20, 20);
        chickenBuyBoard.setFitWidth(80);
        chickenBuyBoard.setFitHeight(100);
        farmAnimalBuyButton(chickenBuyBoard, FarmAnimalType.CHICKEN, moneyText);
        border.getChildren().add(chickenBuyBoard);

        ImageView chickenIconBoard = new ImageView(new Image("/GUI/Textures/UI/Icons/Products/chicken.png"));
        chickenIconBoard.relocate(28, 23);
        chickenIconBoard.setFitHeight(60);
        chickenIconBoard.setFitWidth(60);
        farmAnimalBuyButton(chickenIconBoard, FarmAnimalType.CHICKEN, moneyText);
        border.getChildren().add(chickenIconBoard);

        Text chickenBuyPrice = new Text(30, 103, Integer.toString(FarmAnimalType.CHICKEN.getBuyCost()));
        chickenBuyPrice.setFill(Color.rgb(195, 207, 23));
        chickenBuyPrice.setFont(Font.font("Chalkboard", FontWeight.BOLD, 25));
        farmAnimalBuyButton(chickenBuyPrice, FarmAnimalType.CHICKEN, moneyText);
        border.getChildren().add(chickenBuyPrice);


        ImageView sheepBuyBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        sheepBuyBoard.relocate(105, 20);
        sheepBuyBoard.setFitWidth(80);
        sheepBuyBoard.setFitHeight(100);
        farmAnimalBuyButton(sheepBuyBoard, FarmAnimalType.SHEEP, moneyText);
        border.getChildren().add(sheepBuyBoard);

        ImageView sheepBuyIcon = new ImageView(new Image("/GUI/Textures/UI/Icons/Products/sheep.png"));
        sheepBuyIcon.relocate(111, 23);
        sheepBuyIcon.setFitWidth(60);
        sheepBuyIcon.setFitHeight(60);
        farmAnimalBuyButton(sheepBuyIcon, FarmAnimalType.SHEEP, moneyText);
        border.getChildren().add(sheepBuyIcon);

        Text sheepBuyText = new Text(111, 103, Integer.toString(FarmAnimalType.SHEEP.getBuyCost()));
        sheepBuyText.setFill(Color.rgb(195, 207, 23));
        sheepBuyText.setFont(Font.font("Chalkboard", FontWeight.BOLD, 25));
        farmAnimalBuyButton(sheepBuyText, FarmAnimalType.SHEEP, moneyText);
        border.getChildren().add(sheepBuyText);

        ImageView cowBuyBoard = new ImageView(new Image("/GUI/Textures/gameButtons/board.png"));
        cowBuyBoard.relocate(190, 20);
        cowBuyBoard.setFitWidth(80);
        cowBuyBoard.setFitHeight(100);
        farmAnimalBuyButton(cowBuyBoard, FarmAnimalType.COW, moneyText);
        border.getChildren().add(cowBuyBoard);

        ImageView cowBuyIcon = new ImageView(new Image("/GUI/Textures/UI/Icons/Products/brown_cow.png"));
        cowBuyIcon.relocate(195, 20);
        cowBuyIcon.setFitHeight(60);
        cowBuyIcon.setFitWidth(60);
        farmAnimalBuyButton(cowBuyIcon, FarmAnimalType.COW, moneyText);
        border.getChildren().add(cowBuyIcon);

        Text cowButText = new Text(200, 103, Integer.toString(FarmAnimalType.COW.getBuyCost()));
        cowButText.setFill(Color.rgb(195, 207, 23));
        cowButText.setFont(Font.font("Chalkboard", FontWeight.BOLD, 25));
        farmAnimalBuyButton(cowButText, FarmAnimalType.COW, moneyText);
        border.getChildren().add(cowButText);


        ArrayList<Rectangle> upgradeButtonRectangles = new ArrayList<>();
        ArrayList<Text> upgradeButtonTexts = new ArrayList<>();

        StringBuilder wellStringBuilder = new StringBuilder("/GUI/Textures/Service/Well/");
        Image wellImage = new Image
                (serviceLevelImageSelector(farm.getWell().getLevel(), wellStringBuilder));
        ImageView wellImageView = new ImageView(wellImage);
        wellImageView.setFitWidth(120);
        wellImageView.setFitHeight(120);
        wellImageView.setViewport(new Rectangle2D(0, 0,
                wellImage.getWidth() / 4, wellImage.getHeight() / 4));
        wellImageView.relocate(WIDTH / 2, 40);
        wellImageView.setOnMouseClicked(event -> {
            if (farmController.wellAction(new WellRequest())) {
                moneyTextUpdater(moneyText);
                workshopWellAnimationBuilder(wellImage, wellImageView, 7);
            } else {

            }
        });
        border.getChildren().add(wellImageView);

        Rectangle wellUpgradeButtonRectangle = new Rectangle(WIDTH / 2 + 30, 150, 60, 30);
        Text wellUpgradeButtonText = new Text(WIDTH / 2 + 40, 170,
                Integer.toString(farm.getWell().getUpgradePrice()));
        gameButtonMaker(wellUpgradeButtonRectangle, wellUpgradeButtonText);
        wellUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("well"))) {
                StringBuilder wellStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Well/");
                wellImageView.setImage(new Image(serviceLevelImageSelector(farm.getWell().getLevel(),
                        wellStringBuilder1)));
                wellUpgradeButtonText.setText(
                        Integer.toString(farm.getWell().getUpgradePrice())
                );
                moneyTextUpdater(moneyText);
            }
        });
        wellUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("well"))) {
                StringBuilder wellStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Well/");
                wellImageView.setImage(new Image(serviceLevelImageSelector(farm.getWell().getLevel(),
                        wellStringBuilder1)));
                wellUpgradeButtonText.setText(
                        Integer.toString(farm.getWell().getUpgradePrice())
                );
                moneyTextUpdater(moneyText);
            }
        });
        upgradeButtonRectangles.add(wellUpgradeButtonRectangle);
        upgradeButtonTexts.add(wellUpgradeButtonText);

        Rectangle storageUpgradeButtonRectangle = new Rectangle(
                storageImageView.getLayoutX() + storageImageView.getFitWidth() / 2 - 30,
                storageImageView.getLayoutY() + storageImageView.getFitHeight() - 13,
                60, 30);
        Text storageUpgradeButtonText = new Text(
                storageImageView.getLayoutX() + storageImageView.getFitWidth() / 2 - 20,
                storageImageView.getLayoutY() + storageImageView.getFitHeight() + 7,
                Integer.toString(farm.getStorage().getUpgradePrice()));
        gameButtonMaker(storageUpgradeButtonRectangle, storageUpgradeButtonText);
        storageUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("warehouse"))) {
                StringBuilder storageStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Depot/");
                storageImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getStorage().getLevel(), storageStringBuilder1)));
                storageUpgradeButtonText.setText(
                        Integer.toString(farm.getStorage().getUpgradePrice())
                );
                moneyTextUpdater(moneyText);
            }
        });
        storageUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("warehouse"))) {
                StringBuilder storageStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Depot/");
                storageImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getStorage().getLevel(), storageStringBuilder1)));
                storageUpgradeButtonText.setText(
                        Integer.toString(farm.getStorage().getUpgradePrice())
                );
                moneyTextUpdater(moneyText);
            }
        });
        upgradeButtonTexts.add(storageUpgradeButtonText);
        upgradeButtonRectangles.add(storageUpgradeButtonRectangle);


        Rectangle truckUpgradeButtonRectangle = new Rectangle(
                truckImageView.getLayoutX() + truckImageView.getFitWidth() / 2 - 20,
                truckImageView.getLayoutY() + truckImageView.getFitHeight() - 5,
                60, 30);
        Text truckUpgradeButtonText = new Text(
                truckImageView.getLayoutX() + truckImageView.getFitWidth() / 2 - 10,
                truckImageView.getLayoutY() + truckImageView.getFitHeight() + 15,
                Integer.toString(farm.getTruck().getUpgradeCost()));
        gameButtonMaker(truckUpgradeButtonRectangle, truckUpgradeButtonText);
        truckUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("truck"))) {
                StringBuilder truckStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Truck/");
                truckImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getTruck().getLevel(), truckStringBuilder1)));
                truckUpgradeButtonText.setText(
                        Integer.toString(farm.getTruck().getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
            }
        });
        truckUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("truck"))) {
                StringBuilder truckStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Truck/");
                truckImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getTruck().getLevel(), truckStringBuilder1)));
                truckUpgradeButtonText.setText(
                        Integer.toString(farm.getTruck().getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
            }
        });
        upgradeButtonTexts.add(truckUpgradeButtonText);
        upgradeButtonRectangles.add(truckUpgradeButtonRectangle);

        Rectangle helicopterUpgradeButtonRectangle = new Rectangle(WIDTH / 2 + 160, HEIGHT - 40,
                60, 30);
        Text helicopterUpgradeButtonText = new Text(WIDTH / 2 + 170, HEIGHT - 20,
                Integer.toString(farm.getHelicopter().getUpgradeCost()));
        gameButtonMaker(helicopterUpgradeButtonRectangle, helicopterUpgradeButtonText);
        helicopterUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("helicopter"))) {
                StringBuilder helicopterStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Helicopter/");
                helicopterImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getHelicopter().getLevel(), helicopterStringBuilder1)));
                helicopterUpgradeButtonText.setText(
                        Integer.toString(farm.getHelicopter().getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
            }
        });
        helicopterUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("helicopter"))) {
                StringBuilder helicopterStringBuilder1 = new StringBuilder("/GUI/Textures/Service/Helicopter/");
                helicopterImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getHelicopter().getLevel(), helicopterStringBuilder1)));
                helicopterUpgradeButtonText.setText(
                        Integer.toString(farm.getHelicopter().getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
            }
        });
        upgradeButtonRectangles.add(helicopterUpgradeButtonRectangle);
        upgradeButtonTexts.add(helicopterUpgradeButtonText);

        Rectangle cookieBakeryUpgradeButtonRectangle = new Rectangle(10, 170,
                60, 30);
        Text cookieBakeryUpgradeButtonText = new Text(20, 190,
                Integer.toString(farm.getWorkShops().get(0).getUpgradeCost()));
        gameButtonMaker(cookieBakeryUpgradeButtonRectangle, cookieBakeryUpgradeButtonText);
        cookieBakeryUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("cookiebakery"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/Cake (Cookie Bakery)/");
                cookieBakeryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(0).getLevel(), stringBuilder1)));
                cookieBakeryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(0).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                cookieBakeryImageView.setVisible(true);
            }
        });
        cookieBakeryUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("cookiebakery"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/Cake (Cookie Bakery)/");
                cookieBakeryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(0).getLevel(), stringBuilder1)));
                cookieBakeryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(0).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                cookieBakeryImageView.setVisible(true);

            }
        });
        upgradeButtonRectangles.add(cookieBakeryUpgradeButtonRectangle);
        upgradeButtonTexts.add(cookieBakeryUpgradeButtonText);

        Rectangle sewingFactoryUpgradeButtonRectangle = new Rectangle(10, 300,
                60, 30);
        Text sewingFactoryUpgradeButtonText = new Text(20, 320,
                Integer.toString(farm.getWorkShops().get(1).getUpgradeCost()));
        gameButtonMaker(sewingFactoryUpgradeButtonRectangle, sewingFactoryUpgradeButtonText);
        sewingFactoryUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("sewingfactory"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/CarnivalDress (Sewing Factory)/");
                sewingFactoryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(1).getLevel(), stringBuilder1)));
                sewingFactoryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(1).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                sewingFactoryImageView.setVisible(true);

            }
        });
        sewingFactoryUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("sewingfactory"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/CarnivalDress (Sewing Factory)/");
                sewingFactoryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(1).getLevel(), stringBuilder1)));
                sewingFactoryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(1).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                sewingFactoryImageView.setVisible(true);

            }
        });
        upgradeButtonRectangles.add(sewingFactoryUpgradeButtonRectangle);
        upgradeButtonTexts.add(sewingFactoryUpgradeButtonText);

        Rectangle eggPowderPlantUpgradeButtonRectangle = new Rectangle(10, 420,
                60, 30);
        Text eggPowderPlantUpgradeButtonText = new Text(20, 440,
                Integer.toString(farm.getWorkShops().get(2).getUpgradeCost()));
        gameButtonMaker(eggPowderPlantUpgradeButtonRectangle, eggPowderPlantUpgradeButtonText);
        eggPowderPlantUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("eggpowderplant"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/DriedEggs (Egg Powder Plant)/");
                eggPowderPlantImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(2).getLevel(), stringBuilder1)));
                eggPowderPlantUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(2).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                eggPowderPlantImageView.setVisible(true);

            }
        });
        eggPowderPlantUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("eggpowderplant"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/DriedEggs (Egg Powder Plant)/");
                eggPowderPlantImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(2).getLevel(), stringBuilder1)));
                eggPowderPlantUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(2).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                eggPowderPlantImageView.setVisible(true);
            }
        });
        upgradeButtonRectangles.add(eggPowderPlantUpgradeButtonRectangle);
        upgradeButtonTexts.add(eggPowderPlantUpgradeButtonText);

        Rectangle cakeBakeryUpgradeButtonRectangle = new Rectangle(700, 170,
                60, 30);
        Text cakeBakeryUpgradeButtonText = new Text(710, 190,
                Integer.toString(farm.getWorkShops().get(3).getUpgradeCost()));
        gameButtonMaker(cakeBakeryUpgradeButtonRectangle, cakeBakeryUpgradeButtonText);
        cakeBakeryUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("cakebakery"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/FlouryCake (Cake Bakery)/");
                cakeBakeryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(3).getLevel(), stringBuilder1)));
                cakeBakeryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(3).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                cakeBakeryImageView.setVisible(true);

            }
        });
        cakeBakeryUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("cakebakery"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/FlouryCake (Cake Bakery)/");
                cakeBakeryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(3).getLevel(), stringBuilder1)));
                cakeBakeryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(3).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                cakeBakeryImageView.setVisible(true);

            }
        });
        upgradeButtonRectangles.add(cakeBakeryUpgradeButtonRectangle);
        upgradeButtonTexts.add(cakeBakeryUpgradeButtonText);

        Rectangle spinneryUpgradeButtonRectangle = new Rectangle(700, 300,
                60, 30);
        Text spinneryUpgradeButtonText = new Text(710, 320,
                Integer.toString(farm.getWorkShops().get(4).getUpgradeCost()));
        gameButtonMaker(spinneryUpgradeButtonRectangle, spinneryUpgradeButtonText);
        spinneryUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("spinnery"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/Spinnery (Spinnery)/");
                spinneryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(4).getLevel(), stringBuilder1)));
                spinneryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(4).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                spinneryImageView.setVisible(true);

            }
        });
        spinneryUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("spinnery"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/Spinnery (Spinnery)/");
                spinneryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(4).getLevel(), stringBuilder1)));
                spinneryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(4).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                spinneryImageView.setVisible(true);

            }
        });
        upgradeButtonRectangles.add(spinneryUpgradeButtonRectangle);
        upgradeButtonTexts.add(spinneryUpgradeButtonText);

        Rectangle weavingFactoryUpgradeButtonRectangle = new Rectangle(700, 420,
                60, 30);
        Text weavingFactoryUpgradeButtonText = new Text(710, 440,
                Integer.toString(farm.getWorkShops().get(5).getUpgradeCost()));
        gameButtonMaker(weavingFactoryUpgradeButtonRectangle, weavingFactoryUpgradeButtonText);
        weavingFactoryUpgradeButtonRectangle.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("weavingfactory"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/Weaving (Weaving Factory)/");
                weavingFactoryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(5).getLevel(), stringBuilder1)));
                weavingFactoryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(5).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                weavingFactoryImageView.setVisible(true);

            }
        });
        weavingFactoryUpgradeButtonText.setOnMouseClicked(event -> {
            if (farmController.upgradeAction(new UpgradeRequest("weavingfactory"))) {
                StringBuilder stringBuilder1 = new StringBuilder("/GUI/Textures/Workshops/Weaving (Weaving Factory)/");
                weavingFactoryImageView.setImage(new Image(serviceLevelImageSelector(farmController.getFarm()
                        .getWorkShops().get(5).getLevel(), stringBuilder1)));
                weavingFactoryUpgradeButtonText.setText(
                        Integer.toString(farm.getWorkShops().get(5).getUpgradeCost())
                );
                moneyTextUpdater(moneyText);
                weavingFactoryImageView.setVisible(true);

            }
        });
        upgradeButtonRectangles.add(weavingFactoryUpgradeButtonRectangle);
        upgradeButtonTexts.add(weavingFactoryUpgradeButtonText);


        border.getChildren().addAll(upgradeButtonRectangles);
        border.getChildren().addAll(upgradeButtonTexts);
        border.setOnMouseClicked(event -> {
            int x = (int) ((event.getX() - Utils.startX) / Utils.cellXSize);
            int y = (int) ((event.getY() - Utils.startY) / Utils.cellYSize);
            if (x >= 0 && x < 30 && y >= 0 && y < 30) {
                if (!farmController.pickUpAction(new PickUpRequest(x, y))) {
                    if (!farmController.cageAction(new CageRequest(x, y))) {
                        farmController.plantAction(new PlantRequest(x, y));
                    }
                }
            }
        });
//
        Rectangle levelRectangle = new Rectangle(WIDTH - 180, HEIGHT - 120, 180, 40);
        Text levelText = new Text(WIDTH - 150, HEIGHT - 95, "SHOW LEVEL");
        levelText.setFont(Font.font("Chalkboard", 20));
        gameButtonMaker(levelRectangle, levelText);
        levelRectangle.setOnMouseClicked(event -> {
            levelShower();
        });
        levelText.setOnMouseClicked(event -> {
            levelShower();
        });

        border.getChildren().addAll(levelRectangle, levelText);
        AnimationTimer gameRunner = new AnimationTimer() {
            private long lastTime = 0;
            private double time = 0;
            private long second = 1000000000;
            private int numberOfGameNodes = 0;
            private int numberOfBorderNodes = border.getChildren().size();

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (now > lastTime + second / timeConstant * 1000) {
                    lastTime = now;
                    time += 1;
                    farmController.turnAction(new TurnRequest(1));
                    border.getChildren().remove(numberOfBorderNodes - numberOfGameNodes, numberOfBorderNodes);
                    Cell[][] cells = farm.getCells();
                    ArrayList<Animation> animations = new ArrayList<>();
                    for (int i = 0; i < 30; i++) {
                        for (int j = 0; j < 30; j++) {
                            if (cells[i][j].isHasPlant()) {
                                ImageView imageView = getPlantImageView(cells[i][j].getPlantLevel());
                                imageView.setX(Utils.startX + Utils.cellXSize * i - 20);
                                imageView.setY(Utils.startY + Utils.cellYSize * j - 20);
                                game.getChildren().add(imageView);
                            }
                            for (Product product : cells[i][j].getProducts()) {
                                ImageView productImageView = getProductImageView(product);
                                productImageView.setX(Utils.startX + Utils.cellXSize * i - 15);
                                productImageView.setY(Utils.startY + Utils.cellYSize * j - 15);
                                game.getChildren().add(productImageView);
                            }

                        }
                    }

                    for (int i = 0; i < 30; i++) {
                        for (int j = 0; j < 30; j++) {
                            ArrayList<Animal> animals = cells[i][j].getAnimals();
                            for (Animal animal : animals) {
                                Animation animation = new SpriteAnimal(animal, timeConstant, game);
                                animation.setCycleCount(animal.getSpeed());
                                animations.add(animation);
                            }
                        }
                    }

                    for (Animation animation : animations) {
                        animation.play();
                    }
                    numberOfGameNodes = game.getChildren().size();
                    border.getChildren().addAll(game.getChildren());
                    numberOfBorderNodes = border.getChildren().size();
                }
            }
        };
        gameRunner.start();
    }

    public void levelShower() {
        Stage levelStage = new Stage();
        Group levelGroup = new Group();
        Scene levelScene = new Scene(levelGroup, 600, 800);

        TextArea levelTextArea = new TextArea();
        levelTextArea.setMinWidth(600);
        levelTextArea.setMinHeight(800);
        StringBuilder levelStringBuilder = new StringBuilder();
        Level level = farmController.getFarm().getLevel();
        levelStringBuilder.append("RequiredMoney: " + level.getRequiredMoney() + "\n");
        for (Map.Entry<Animal, Integer> entry : level.getRequiredAnimals().entrySet()) {
            if (entry.getKey() instanceof Cat) {
                levelStringBuilder.append("Cat: " + entry.getValue() + "\n");
            } else if (entry.getKey() instanceof Dog) {
                levelStringBuilder.append("Dog: " + entry.getValue() + "\n");
            } else if (entry.getKey() instanceof FarmAnimal) {
                levelStringBuilder.append(((FarmAnimal) entry.getKey()).getFarmAnimalType().toString() + ": " +
                        entry.getValue() + "\n");
            } else if (entry.getKey() instanceof WildAnimal) {
                levelStringBuilder.append(((WildAnimal) entry.getKey()).getWildAnimalType().toString() + ": " +
                        entry.getValue() + "\n");
            }
        }
        for (Map.Entry<Product, Integer> entry : level.getRequiredProduct().entrySet()) {
            if (entry.getKey() instanceof PrimitiveProduct) {
                levelStringBuilder.append(((PrimitiveProduct) entry.getKey()).getPrimitiveProductType().toString()
                        + ": " + entry.getValue() + "\n");
            } else if (entry.getKey() instanceof SecondaryProduct) {
                levelStringBuilder.append(((SecondaryProduct) entry.getKey()).getSecondaryProductType().toString()
                        + ": " + entry.getValue() + "\n");
            }
        }
        levelTextArea.setText(levelStringBuilder.toString());
        levelTextArea.setEditable(false);
        levelTextArea.setFont(Font.font("Chalkboard", FontWeight.BOLD, 40));
        levelGroup.getChildren().add(levelTextArea);
        levelStage.setScene(levelScene);
        levelStage.show();
    }

    private String timeSetter(int time) {
        return String.format("%02d:%02d", time / 60, time % 60);
    }

    public void newChat() {
        Stage chatWindow = new Stage();
        Scene chatScene = new Scene(chatGroup, 600, 900);
        TextField sendTextField = new TextField("");
        sendTextField.setMinWidth(200);
        Button sendButton = new Button("SEND");
        sendButton.setOnMouseClicked(event -> {
            String message = sendTextField.getText();
            if (networkController.getProfile().isHost())
                chatArea.appendText(networkController.getProfile().getProfileName() + ": " + message + "\n");
            networkController.sendMessage(sendTextField.getText());
        });
        HBox hBox = new HBox(20, sendTextField, sendButton);
        hBox.relocate(30, 650);
        hBox.relocate(30, 650);
        chatArea = new TextArea("");
        chatArea.setMinHeight(600);
        chatArea.setMinWidth(600);

        VBox vBox = new VBox(chatArea, hBox);

        chatGroup.getChildren().add(vBox);

        chatWindow.setScene(chatScene);
        chatWindow.show();
    }

    public void showMessage(String name, String text) {
        chatArea.appendText(name + ": " + text + "\n");
    }

    public HashMap<String, Integer> storageHashMapMaker() {
        ArrayList<Product> storageProducts = farmController.getFarm().getStorage().getProducts();
        HashMap<String, Integer> storageHashMap = new HashMap<>();
        for (Product product : storageProducts) {
            if (product instanceof PrimitiveProduct) {
                if (!storageHashMap.containsKey(((PrimitiveProduct) product).getPrimitiveProductType().toString())) {
                    storageHashMap.put(((PrimitiveProduct) product).getPrimitiveProductType().toString(), 1);
                } else {
                    int number = storageHashMap.get(((PrimitiveProduct) product).getPrimitiveProductType().toString()
                    ) + 1;
                    storageHashMap.remove(((PrimitiveProduct) product).getPrimitiveProductType().toString());
                    storageHashMap.put(((PrimitiveProduct) product).getPrimitiveProductType().toString(), number);
                }
            } else {
                if (!storageHashMap.containsKey(((SecondaryProduct) product).getSecondaryProductType().toString())) {
                    storageHashMap.put(((SecondaryProduct) product).getSecondaryProductType().toString(), 1);
                } else {
                    int number = storageHashMap.get(((SecondaryProduct) product).getSecondaryProductType().toString()
                    ) + 1;
                    storageHashMap.remove(((SecondaryProduct) product).getSecondaryProductType().toString());
                    storageHashMap.put(((SecondaryProduct) product).getSecondaryProductType().toString(), number);
                }
            }
        }
        ArrayList<Animal> storageAnimals = farmController.getFarm().getStorage().getAnimals();
        for (Animal animal : storageAnimals) {
            if (animal instanceof WildAnimal) {
                if (!storageHashMap.containsKey(((WildAnimal) animal).getWildAnimalType().toString())) {
                    storageHashMap.put(((WildAnimal) animal).getWildAnimalType().toString(), 1);
                } else {
                    int number = storageHashMap.get(((WildAnimal) animal).getWildAnimalType().toString()) + 1;
                    storageHashMap.remove(((WildAnimal) animal).getWildAnimalType().toString());
                    storageHashMap.put(((WildAnimal) animal).getWildAnimalType().toString(), number);
                }
            }
        }
        return storageHashMap;
    }

    private void workshopWellAnimationBuilder(Image wellWorkshopImage, ImageView wellWorkshopImageView, int index) {
        WellWorkshopSpriteAnimation wellWorkshopSpriteAnimation = new WellWorkshopSpriteAnimation(
                wellWorkshopImageView, Duration.millis(timeConstant), 16,
                4, 4, 0, (int) wellWorkshopImage.getWidth() / 4,
                (int) wellWorkshopImage.getHeight() / 4);
        if (index == 7) {
            wellWorkshopSpriteAnimation.setCycleCount(farmController.getFarm().getWell().getTimeToFill());
        } else {
            wellWorkshopSpriteAnimation.setCycleCount(farmController.getFarm().getWorkShops().get(index)
                    .getMaxTimeToFinish());
        }
        wellWorkshopSpriteAnimation.play();
    }

    public void newLeaderBoard() {
        leaderBoardGroup.getChildren().clear();
        Stage leaderBoardWindow = new Stage();
        Scene leaderBoardScene = new Scene(leaderBoardGroup, 600, 900);
        Text leaderBoardTitle = new Text(200, 10, "LEADERBOARD");
        leaderBoardTitle.setFont(Font.font("Chalkboard", FontWeight.BOLD, 30));
        StringBuilder leaderBoardTextBuilder = new StringBuilder();
        HashMap<String, Integer> leaderBoardHashMap = networkController.showLeaderBoard();
        LeaderboardWrapper leaderboardWrapper = new LeaderboardWrapper(leaderBoardHashMap);
        for (Map.Entry<String, Integer> entry : leaderboardWrapper.getLeaderboardHashMap().entrySet()) {
            leaderBoardTextBuilder.append(entry.getKey() + ":        " + entry.getValue() + "\n");
        }
        TextArea leaderBoardTextArea = new TextArea(leaderBoardTextBuilder.toString());
        leaderBoardTextArea.setMinWidth(600);
        leaderBoardTextArea.setMinHeight(800);
        leaderBoardTextArea.setEditable(false);
        leaderBoardTextArea.relocate(0, 100);

        Button sortByMoneyButton = new Button("SORT BY MONEY");
        Button sortByNameButton = new Button("SORT BY NAME");

        sortByMoneyButton.relocate(100, 60);
        sortByNameButton.relocate(200, 60);

        sortByMoneyButton.setOnMouseClicked(event -> {
            leaderboardWrapper.sortByValue();
            leaderBoardTextBuilder.delete(0, leaderBoardTextBuilder.toString().length());
            for (Map.Entry<String, Integer> entry : leaderboardWrapper.getLeaderboardHashMap().entrySet()) {
                leaderBoardTextBuilder.append(entry.getKey() + ":        " + entry.getValue() + "\n");
            }
            leaderBoardTextArea.clear();
            leaderBoardTextArea.setText(leaderBoardTextBuilder.toString());
        });
        sortByNameButton.setOnMouseClicked(event -> {
            leaderboardWrapper.sortByKey();
            leaderBoardTextBuilder.delete(0, leaderBoardTextBuilder.toString().length());
            for (Map.Entry<String, Integer> entry : leaderboardWrapper.getLeaderboardHashMap().entrySet()) {
                leaderBoardTextBuilder.append(entry.getKey() + ":        " + entry.getValue() + "\n");
            }
            leaderBoardTextArea.clear();
            leaderBoardTextArea.setText(leaderBoardTextBuilder.toString());

        });

        leaderBoardGroup.getChildren().addAll(leaderBoardTextArea, sortByMoneyButton, sortByNameButton, leaderBoardTitle);
        leaderBoardWindow.setScene(leaderBoardScene);
        leaderBoardWindow.setOnCloseRequest(event -> leaderBoardWindow.close());
        leaderBoardWindow.show();
    }


    public void farmAnimalBuyButton(Node buttonNode, FarmAnimalType farmAnimalType, Text moneyText) {
        if (farmAnimalType.equals(FarmAnimalType.COW)) {
            buttonNode.setOnMouseClicked(event -> {
                farmController.buyAction(new BuyRequest(new FarmAnimal((int) (Math.random() * 30), (int) (Math.random() * 30),
                        FarmAnimalType.COW)));
                moneyTextUpdater(moneyText);
            });
        } else if (farmAnimalType.equals(FarmAnimalType.CHICKEN)) {
            buttonNode.setOnMouseClicked(event -> {
                farmController.buyAction(new BuyRequest(new FarmAnimal((int) (Math.random() * 30), (int) (Math.random() * 30),
                        FarmAnimalType.CHICKEN)));
                moneyTextUpdater(moneyText);
            });
        } else {
            buttonNode.setOnMouseClicked(event -> {
                farmController.buyAction(new BuyRequest(new FarmAnimal((int) (Math.random() * 30), (int) (Math.random() * 30),
                        FarmAnimalType.SHEEP)));
                moneyTextUpdater(moneyText);
            });
        }
    }

    public void moneyTextUpdater(Text moneyText) {
        moneyText.setText(Integer.toString(farmController.getFarm().getMoney()));
        moneyText.setFill(Color.WHITE);
        moneyText.setFont(Font.font("Chalkboard", FontWeight.BOLD, 30));

    }

    public void workShopImageModify(ImageView workshopImageView, Image workshopImage) {
        workshopImageView.setViewport(new Rectangle2D(0, 0, workshopImage.getWidth() / 4,
                workshopImage.getHeight() / 4));
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
                imagePathStringBuilder.append("02.png");
                break;
            case 2:
                imagePathStringBuilder.append("03.png");
                break;
            case 3:
                imagePathStringBuilder.append("04.png");
                break;
            default:
                imagePathStringBuilder.append("01.png");
        }
        return imagePathStringBuilder.toString();
    }

    public void gameButtonMaker(Rectangle buttonRectangle, Text buttonText) {
        ImagePattern onMouseEnterTexture = new ImagePattern(
                new Image("/GUI/Textures/gameButtons/buttonTexture.jpg")
        );
        ImagePattern onMouseExitTexture = new ImagePattern(
                new Image("/GUI/Textures/gameButtons/buttonTexture2.jpg")
        );
        buttonText.setFill(Color.rgb(255, 255, 255));
        buttonText.setFont(Font.font("Chalkboard", 20));
        buttonRectangle.setFill(onMouseExitTexture);
        buttonRectangle.setStroke(Color.rgb(255, 255, 255));
        buttonRectangle.setStrokeWidth(1);
        buttonRectangle.setArcHeight(30);
        buttonRectangle.setArcWidth(30);
        gameButtonOnMouseMaker(buttonRectangle, buttonText, onMouseEnterTexture, onMouseExitTexture);
    }

    private void gameButtonOnMouseMaker(Rectangle buttonRectangle, Text buttonText, ImagePattern onMouseEnterTexture, ImagePattern onMouseExitTexture) {
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
        gameButtonOnMouseMaker(buttonRectangle, buttonText, onMouseEnterTexture, onMouseExitTexture);


        buttonText.setFill(Color.rgb(207, 0, 2));
        buttonText.setFont(Font.font("Chalkboard", 40));
        menuGroup.getChildren().addAll(buttonRectangle, buttonText);
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
        } else if (product instanceof SecondaryProduct) {
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
            return new ImageView(new Image(new FileInputStream(pathToImage.toString())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ImageView getPlantImageView(int plantLevel) {
        ImageView grassView = new ImageView(grassImage);
        grassView.setScaleX(0.4);
        grassView.setScaleY(0.4);
        if (plantLevel == 1)
            grassView.setViewport(new Rectangle2D(148, 0, 47, 47));
        else if (plantLevel == 2)
            grassView.setViewport(new Rectangle2D(148, 47, 47, 47));
        else if (plantLevel == 3)
            grassView.setViewport(new Rectangle2D(148, 94, 47, 47));
        else if (plantLevel >= 4)
            grassView.setViewport(new Rectangle2D(148, 148, 47, 47));
        return grassView;
    }
}
