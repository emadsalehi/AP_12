package model;

import model.exceptions.NotPossibleException;

public class Cat extends Animal {
    private int level = 1;

    public Cat(int x, int y) {
        super(x, y);
        setBuyPrice(2500); // TODO
        setSellPrice(2000);//TODO
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
        if (level == 1) {
            return 2000;
        }
        else {
            throw new NotPossibleException("catCalculateUpgradePrice");
        }
    }

    public int getLevel() {
        return level;
    }
}
