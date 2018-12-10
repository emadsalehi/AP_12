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
            double xSpeed = ((double) (destinationX - getX())) / Math.sqrt((destinationX - getX()) ^ 2 + (destinationY - getY()) ^ 2)
                    * getSpeed();
            double ySpeed = ((double) (destinationY - getY())) / Math.sqrt((destinationX - getX()) ^ 2 + (destinationY - getY()) ^ 2)
                    * getSpeed();
            setX((int) Math.round(getX() + xSpeed));
            setY((int) Math.round(getY() + ySpeed));
        }
        else {

        }
    }

    public int calculateUpgardePrice() {

    }
}
