package model;

public abstract class Animal {
    private int x;
    private int y;

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
