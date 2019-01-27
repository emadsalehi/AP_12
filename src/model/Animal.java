package model;

import java.util.Random;

public abstract class Animal {
    private int x;
    private int y;
    private int buyPrice;
    private int sellPrice;
    private int speed = 1;
    private String type;
    private int xDirection = -1;
    private int yDirection = 0;
    private boolean isMoving = true;

    public Animal(int x, int y) {
        this.x = x;
        this.y = y;
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
        while (xDirection == 0 && yDirection == 0) {
            xDirection = randomiser(x);
            yDirection = randomiser(y);
        }

        x += speed * xDirection;
        y += speed * yDirection;
    }

    public int randomiser(int value) {
        Random random = new Random();
        if (value <= speed * 4) {
            return random.nextInt(2);
        } else if (value >= 29 - speed * 4) {
            return random.nextInt(2) - 1;
        } else {
            return random.nextInt(3) - 1;
        }
    }

    public void intendedMove(int destinationX, int destinationY) {

        xDirection = (int) Math.round(((double) (destinationX - getX())) /
                Math.sqrt(Math.pow((destinationX - getX()), 2) + Math.pow((destinationY - getY()) , 2)));
        yDirection = (int) Math.round(((double) (destinationY - getY())) /
                Math.sqrt(Math.pow((destinationX - getX()), 2) + Math.pow((destinationY - getY()) , 2)));
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

    public int getXDirection() {
        return xDirection;
    }

    public int getYDirection() {
        return yDirection;
    }
}
