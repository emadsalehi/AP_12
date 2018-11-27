package model;

public class Cat extends Animal {
    private int level = 1;

    public Cat(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int destinationX, int destinationY) {

    }

    public int calculateUpgardePrice() {
        return 0;
    }
}
