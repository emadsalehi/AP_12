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


    public abstract SecondaryProduct getProduct();
}
