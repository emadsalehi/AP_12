package model;

public abstract class WorkShop {
    private boolean isWorking = false;
    private int timeToFinish;
    private int maxTimeToFinish;
    private int level;
    private int throwedProductX;
    private int throwedProductY;
    private int numberOfRawProduct = 1;
    private int numberOfProcessedProduct;

    public void updateWorkShop() {
        //todo Set hoursToFinish to maxHoursToFinish + 1 if work done.

    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public int getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(int timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public int getMaxTimeToFinish() {
        return maxTimeToFinish;
    }

    public void setMaxTimeToFinish(int maxTimeToFinish) {
        this.maxTimeToFinish = maxTimeToFinish;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getThrowedProductX() {
        return throwedProductX;
    }

    public void setThrowedProductX(int throwedProductX) {
        this.throwedProductX = throwedProductX;
    }

    public int getThrowedProductY() {
        return throwedProductY;
    }

    public void setThrowedProductY(int throwedProductY) {
        this.throwedProductY = throwedProductY;
    }

    public int getNumberOfRawProduct() {
        return numberOfRawProduct;
    }

    public void setNumberOfRawProduct(int numberOfRawProduct) {
        this.numberOfRawProduct = numberOfRawProduct;
    }

    public int getNumberOfProcessedProduct() {
        return numberOfProcessedProduct;
    }

    public void setNumberOfProcessedProduct(int numberOfProcessedProduct) {
        this.numberOfProcessedProduct = numberOfProcessedProduct;
    }

    public abstract SecondaryProduct getProduct();
}
