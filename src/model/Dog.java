package model;

public class Dog extends Animal {
    private int level = 1;

    public Dog(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int destinationX, int destinationY) {

    }

    public int calculateUpgradePrice() {
        return 0;
    }
}
