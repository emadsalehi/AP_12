package model;

public class Dog extends Animal {
    private int level = 1;

    public Dog(int x, int y) {
        super(x, y);
        setBuyPrice();
        setSellPrice();
    }


    @Override
    public void move(int destinationX, int destinationY) {
        if (destinationX == 31) {
            randomMove();
        }
        else {
            intendedMove(destinationX, destinationY);
        }
    }


    public int calculateUpgradePrice() {
        return 0;
    }
}
