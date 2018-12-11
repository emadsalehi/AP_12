package model;

public class Cat extends Animal {
    private int level = 1;

    public Cat(int x, int y) {
        super(x, y);
        setBuyPrice(100000000); // TODO
        setSellPrice(300000000);//TODO
    }

    @Override
    public void move(int destinationX, int destinationY) {
        if (level == 2) {
            intendedMove(destinationX, destinationY);
        } else {
            randomMove();
        }
    }

    public int calculateUpgardePrice() {

    }
}
