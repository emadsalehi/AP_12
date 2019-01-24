package model;

public class Dog extends Animal {
    private int level = 1;

    public Dog(int x, int y) {
        super(x, y);
        setBuyPrice(2000);
        setSellPrice(1500);
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

}
