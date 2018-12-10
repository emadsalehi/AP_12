package model;

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
