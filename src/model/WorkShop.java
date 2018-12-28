package model;

public abstract class WorkShop {
    private boolean isWorking = false;
    private int timeToFinish = 0;
    private int maxTimeToFinish;
    private int level = 1;
    private int throwedProductX;
    private int throwedProductY;
    private int numberOfRawProduct = 1;
    private int numberOfProcessedProduct = 1;


    public WorkShop(int maxTimeToFinish, int throwedProductX, int throwedProductY, int numberOfProcessedProduct) {
        this.maxTimeToFinish = maxTimeToFinish;
        this.throwedProductX = throwedProductX;
        this.throwedProductY = throwedProductY;
        this.numberOfProcessedProduct = numberOfProcessedProduct;
    }

    public void upgrade() {
        this.level += 1;
    }

    public void nextTurn() {
        if (this.isWorking) {
            if (this.timeToFinish != 0)
                this.timeToFinish -= 1;
            else {
                this.timeToFinish = this.maxTimeToFinish;
                this.isWorking = false;
            }
        }
    }

    public boolean readyForDelivery(){
        if (!this.isWorking && this.timeToFinish == this.maxTimeToFinish )
            return true;
        return false;
    }

    public void startWorkShop(){
        this.isWorking = true;
        this.timeToFinish = this.maxTimeToFinish - 1;
    }

    //todo complete this method for each workshop.
    public abstract int getUpgradeCost();

    public abstract SecondaryProduct getProduct();

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
}
