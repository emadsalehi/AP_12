package model;

import java.util.ArrayList;

public class Helicopter {
    //todo modify speed and capacity in set level method.
    private int level = 1;
    private double speed;
    private int capacity;
    private boolean isAvailable = true;
    private ArrayList<Product> products = new ArrayList<>();
    private boolean readyToDeliver = false;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int calculateRquiredMoney() {
        return 0;
    }
}
