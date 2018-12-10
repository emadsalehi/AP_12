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
        double xSpeed = ((double)(destinationX- getX()))/Math.sqrt((destinationX-getX())^2+(destinationY-getY())^2)
                *getSpeed();
        double ySpeed = ((double)(destinationY-getY()))/Math.sqrt((destinationX-getX())^2+(destinationY-getY())^2)
                *getSpeed();
        setX((int)Math.round(getX() + xSpeed));
        setY((int)Math.round(getY() + ySpeed));
    }

    public int calculateUpgradePrice() {
        return 0;
    }
}
