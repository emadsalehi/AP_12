package GUI;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpriteAnimal extends Transition {
    private Animal animal;
    private Group game;
    private ImageView imageView;
    private int timeConstant;
    private int count = 24;
    private int columns = 24;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;
    private int lastIndex;
    private final int xCellSize = Utils.cellXSize;
    private final int yCellSize = Utils.cellYSize;

    public SpriteAnimal(Animal animal, int timeConstant, Group game) {
        this.animal = animal;
        this.timeConstant = timeConstant;
        this.imageView = getAnimalImageView();
        this.game = game;
        this.imageView.setViewOrder(0);
        game.getChildren().add(this.imageView);
        this.setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setVisible(false);
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            imageView.setVisible(true);
            if (animal.isMoving()) {
                imageView.setX(imageView.getX() + (double)animal.getXDirection() * (double) xCellSize / (double)count);
                imageView.setY(imageView.getY() + (double)animal.getYDirection() * (double) yCellSize / (double)count);
            }
            lastIndex = index;
        }
    }

    public ImageView getAnimalImageView(){
        String pathToImage = "src/GUI/Textures/Animals/Africa/";
        if (animal instanceof Cat)
            pathToImage = pathToImage.concat("Cat");
        else if (animal instanceof Dog)
            pathToImage = pathToImage.concat("Dog");
        else if (animal instanceof WildAnimal) {
            if (((WildAnimal) animal).getWildAnimalType() == WildAnimalType.BEAR)
                pathToImage = pathToImage.concat("Grizzly");
            else
                pathToImage = pathToImage.concat("Lion");
        } else if (animal instanceof FarmAnimal) {
            if (((FarmAnimal) animal).getFarmAnimalType() == FarmAnimalType.SHEEP)
                pathToImage = pathToImage.concat("Sheep");
            else if (((FarmAnimal) animal).getFarmAnimalType() == FarmAnimalType.COW)
                pathToImage = pathToImage.concat("Cow");
            else
                pathToImage = pathToImage.concat("Turkey");
        }
        pathToImage = pathToImage.concat("/");
        if (animal.isMoving())
            pathToImage = pathToImage.concat(getDirectionString(animal.getXDirection(), animal.getYDirection()));
        else {
            if (animal instanceof WildAnimal)
                pathToImage = pathToImage.concat("cage.png");
            else if (animal instanceof FarmAnimal)
                pathToImage = pathToImage.concat("eat.png");
        }

        ImageView animalView = null;
        try {
            animalView = new ImageView(new Image(new FileInputStream(pathToImage)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (animal.isMoving()) {
            animalView.setX(Utils.startX + xCellSize * animal.getX() + animal.getXDirection() * animal.getSpeed() - 25);
            animalView.setY(Utils.startY + yCellSize * animal.getY() + animal.getYDirection() * animal.getSpeed() - 60);
            this.setCycleDuration(Duration.millis(timeConstant / animal.getSpeed()));
        } else {
            animalView.setX(Utils.startX + xCellSize * animal.getX() - 25);
            animalView.setY(Utils.startY + yCellSize * animal.getY() - 60);
            this.setCycleDuration(Duration.millis(timeConstant));
        }
        width = (int)animalView.getImage().getWidth() / columns;
        height = (int) animalView.getImage().getHeight() / (count / columns);
        animalView.setViewport(new Rectangle2D(0, 0, width, height));
        return animalView;
    }

    public String getDirectionString (int xDirection, int yDirection) {
        if (xDirection == 1) {
            if (yDirection == 1)
                return "down_right.png";
            else if (yDirection == 0)
                return "right.png";
            else
                return "up_right.png";

        } else if (xDirection == 0) {
            if (yDirection == 1)
                return "down.png";
            else if (yDirection == -1)
                return "up.png";
            else
                return "eat.png";

        } else if (xDirection == -1) {
            if (yDirection == 1)
                return "down_left.png";
            else if (yDirection == 0)
                return "left.png";
            else
                return "up_left.png";
        }
        return "";
    }
}
