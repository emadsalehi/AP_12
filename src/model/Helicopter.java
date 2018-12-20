package model;

import java.util.ArrayList;

public class Helicopter {
    //todo modify speed and capacity in set level method.
    private int level = 0;
    private double speed;
    private int capacity;
    private boolean isAvailable = true;
    private int upgradeCost = 400;
    private ArrayList<Product> products = new ArrayList<>();
    private boolean readyToDeliver = false;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.speed = 1 / (12 - 3 * level);   //speed = 1/travelDuration
        this.capacity = level + 2;
        this.upgradeCost = level * 100 + 400;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public boolean isReadyToDeliver() {
        return readyToDeliver;
    }

    public void setReadyToDeliver(boolean readyToDeliver) {
        this.readyToDeliver = readyToDeliver;
    }

    public int calculateRequiredMoney() {
        int requiredMoney = 0;
        for (Product product : products){
            PrimitiveProduct primitiveProduct = (PrimitiveProduct) product;
            requiredMoney += primitiveProduct.getPrimitiveProductType().getBuyCost();
        }
        return requiredMoney;
    }
}
