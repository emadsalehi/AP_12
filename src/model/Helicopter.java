package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Helicopter implements Serializable {
    //todo modify speed and capacity in set level method.
    private int level = 0;
    private double speed = 1 / (12 - 3 * level);
    private int capacity = (level + 2) * 10;
    private boolean isAvailable = true;
    private int upgradeCost = 400;
    private ArrayList<Product> products = new ArrayList<>();
    private boolean readyToDeliver = false;
    private int travelDuration = 12;
    private int travelCounter = 12;

    public int getLevel() {
        return level;
    }

    public int getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(int travelDuration) {
        this.travelDuration = travelDuration;
    }

    public int getTravelCounter() {
        return travelCounter;
    }

    public void setTravelCounter(int travelCounter) {
        this.travelCounter = travelCounter;
    }

    public void setLevel(int level) {
        this.level = level;
        this.speed = 1 / (12 - 3 * level);   //speed = 1/travelDuration
        this.travelDuration = 12 - 3 * level;
        this.travelCounter = this.travelDuration;
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

    public int calculateUsedCapacity(){
        int usedCapacity = 0;
        for(Product product : products){
            if (product instanceof PrimitiveProduct){
                usedCapacity += ((PrimitiveProduct) product).getPrimitiveProductType().getDepotSize();
            }else if (product instanceof  SecondaryProduct){
                usedCapacity += ((SecondaryProduct) product).getSecondaryProductType().getDepotSize();
            }
        }
        return  usedCapacity;
    }

    public void upgrade(){
        setLevel(getLevel() + 1);
    }

    public void nextTurn(){
        if (!isAvailable){
            travelCounter--;
            //System.out.println(travelCounter);
            if (travelCounter == 0){
                isAvailable = true;
                readyToDeliver = true;
                travelCounter = travelDuration;
            }
        }
    }
    public void clearProducts(){
        products.clear();
    }
}
