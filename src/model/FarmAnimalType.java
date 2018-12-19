package model;

public enum FarmAnimalType {
    CHICKEN(50 , 110 , 20 , 10 , 2 , 3 , 50 , 100),
    SHEEP(55 , 110 , 20 , 10 , 2 , 9 , 150 , 1000),
    COW(40 , 80 , 20 , 10 , 2 , 18 , 300 , 10000);

    private final int speed;
    private final int hungryMovingSpeed;
    private final int productionPeriod;
    private final int oneTimeBite;
    private final int waitTime;
    private final int hungrySpeed;
    private final int hungryValue;
    private final int buyCost;

    FarmAnimalType(int speed, int hungryMovingSpeed, int productionPeriod, int oneTimeBite, int waitTime, int hungrySpeed, int hungryValue, int buyCost) {
        this.speed = speed;
        this.hungryMovingSpeed = hungryMovingSpeed;
        this.productionPeriod = productionPeriod;
        this.oneTimeBite = oneTimeBite;
        this.waitTime = waitTime;
        this.hungrySpeed = hungrySpeed;
        this.hungryValue = hungryValue;
        this.buyCost = buyCost;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHungryMovingSpeed() {
        return hungryMovingSpeed;
    }

    public int getProductionPeriod() {
        return productionPeriod;
    }

    public int getOneTimeBite() {
        return oneTimeBite;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getHungrySpeed() {
        return hungrySpeed;
    }

    public int getHungryValue() {
        return hungryValue;
    }

    public int getBuyCost() {
        return buyCost;
    }
}
