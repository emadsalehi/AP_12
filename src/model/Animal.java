package model;

public abstract class Animal {
    private int x;
    private int y;

    public void randomMove() {
    }

    public abstract void move(int destinationX, int destinationY);
}
