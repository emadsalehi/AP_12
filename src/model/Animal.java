package model;

import java.util.Random;

public abstract class Animal {
    private int x;
    private int y;
    private int buyPrice;
    private int sellPrice;
    private int speed;


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

    public Animal(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void randomMove() {
        int randomX;
        int randomY;

        randomX = randomiser(x);
        randomY = randomiser(y);

        x = x + (int)(speed*(double)randomX/Math.sqrt(Math.pow(randomX,2)+Math.pow(randomY,2)));
        y = y + (int)(speed*(double)randomY/Math.sqrt(Math.pow(randomX,2)+Math.pow(randomY,2)));
    }

    public int randomiser(int value) {
        Random random = new Random();
        if ( value == 0 ) {
            return random.nextInt(2);
        }
        else if ( value == 29) {
            return random.nextInt(2) - 1;
        }
        else {
            return random.nextInt(3) -1;
        }
    }

    public void intendedMove(int destinationX, int destinationY) {
        double xSpeed = ((double)(destinationX- getX()))/Math.sqrt((destinationX-getX())^2+(destinationY-getY())^2)
                *getSpeed();
        double ySpeed = ((double)(destinationY-getY()))/Math.sqrt((destinationX-getX())^2+(destinationY-getY())^2)
                *getSpeed();
        setX((int)Math.round(getX() + xSpeed));
        setY((int)Math.round(getY() + ySpeed));
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
