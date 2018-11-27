package model;

import java.util.ArrayList;

public class Truck {
    private int level = 1;
    private double speed;
    private int capacity;
    private boolean isAvailable = true;
    private boolean readyToPay = false;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();

    public Truck(int level, double speed, int capacity, boolean isAvailable, boolean readyToPay) {
        this.level = level;
        this.speed = speed;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
        this.readyToPay = readyToPay;
    }

    public int calculatePaidMoney() {
        return 0;
    }
}
