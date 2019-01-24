package model;

import java.util.Random;

public abstract class Animal {
    private int x;
    private int y;
    private int buyPrice;
    private int sellPrice;
    private int speed;
    private String type;
    private int movesTillNewRandom;
    private int xDirection = 0;
    private int yDirection = 0;
    private boolean isMoving;

    public Animal(int x, int y) {
        this.x = x;
        this.y = y;
        this.movesTillNewRandom = 5;
        this.isMoving = false;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void randomMove() {
        if (isMoving) {

        } else {
            if (movesTillNewRandom == 0) {
                movesTillNewRandom = 5;

                xDirection = randomiser(x);
                yDirection = randomiser(y);

                x += speed * xDirection;
                y += speed * yDirection;
            } else {
                movesTillNewRandom--;
                x += speed * xDirection;
                y += speed * yDirection;
            }
        }

    }

    public int randomiser(int value) {
        Random random = new Random();
        if (value == speed) {
            return random.nextInt(2);
        } else if (value == 29 - speed) {
            return random.nextInt(2) - 1;
        } else {
            return random.nextInt(3) - 1;
        }
    }

    public void intendedMove(int destinationX, int destinationY) {

        xDirection = (int) Math.round(((double) (destinationX - getX())) /
                Math.sqrt((destinationX - getX()) ^ 2 + (destinationY - getY()) ^ 2));
        yDirection = (int) Math.round(((double) (destinationY - getY())) /
                Math.sqrt((destinationX - getX()) ^ 2 + (destinationY - getY()) ^ 2));
        int xSpeed = xDirection * getSpeed();
        int ySpeed = yDirection * getSpeed();
        setX(getX() + xSpeed);
        setY(getY() + ySpeed);

    }


    public abstract void move(int destinationX, int destinationY);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
