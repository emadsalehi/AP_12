package model;
import model.exceptions.*;

public class Well {
    private int level ;
    private int fillPrice;
    private int upgradePrice;
    private int timeToFill;
    private int timeLeftToFill;
    private int capacity;
    private int waterLeft;
    private boolean isWorking = false;//todo: calculate in level setter

    public Well () {
        this.level = 1;
        this.fillPrice = 19;
        this.upgradePrice = 250;
        this.timeToFill = 4;
        this.capacity = 4;
        this.waterLeft = capacity;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public int getTimeLeftToFill() {
        return timeLeftToFill;
    }

    public void setTimeLeftToFill(int timeLeftToFill) {
        this.timeLeftToFill = timeLeftToFill;
    }

    public void fill () {
        this.isWorking = true;
        this.timeLeftToFill = this.timeLeftToFill;
    }

    public void render () {
        if (this.isWorking) {
            if (timeLeftToFill == 0) {
                this.isWorking = false;
                this.waterLeft = capacity;
            }
            else {
                this.timeLeftToFill -= 1;
            }
        }
    }

    public int getWaterLeft() {
        return waterLeft;
}

    public void setWaterLeft(int waterLeft) {
        this.waterLeft = waterLeft;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFillPrice() {
        return fillPrice;
    }

    public void setFillPrice(int fillPrice) {
        this.fillPrice = fillPrice;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public int getTimeToFill() {
        return timeToFill;
    }

    public void setTimeToFill(int timeToFill) {
        this.timeToFill = timeToFill;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void upgradeLevel () {
        switch (this.level) {
            case 1 :
                this.level = 2;
                this.upgradePrice = 300;
                this.timeToFill = 80;
                this.upgradePrice = 17;
                this.capacity = 7;
                break;
            case 2 :
                this.level = 3;
                this.upgradePrice = 350;
                this.timeToFill = 60;
                this.upgradePrice = 15;
                this.capacity = 9;
                break;
            default:
                throw new NotPossibleException("Well");

        }
    }
}
