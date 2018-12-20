package model;

import java.util.ArrayList;

public class Truck {
    private int level = 0;
    private double speed;
    private int capacity;
    private boolean isAvailable = true;
    private boolean readyToPay = false;
    private int upgradeCost = 250;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();

    public Truck(int level, double speed, int capacity, boolean isAvailable, boolean readyToPay) {
        this.level = level;
        this.speed = speed;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
        this.readyToPay = readyToPay;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.speed = 1 / (20 - 5 * level);   //speed = 1/travelDuration
        this.capacity = level + 2;
        this.upgradeCost = (level - 1) * 100 + 500;
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

    public boolean isReadyToPay() {
        return readyToPay;
    }

    public void setReadyToPay(boolean readyToPay) {
        this.readyToPay = readyToPay;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public int calculatePaidMoney() {
        int paidMoney = 0;
        for (Product product : products){
            if (product instanceof PrimitiveProduct){
                paidMoney += ((PrimitiveProduct) product).getPrimitiveProductType().getSaleCost();
            }else if(product instanceof SecondaryProduct){
                paidMoney += ((SecondaryProduct) product).getSecondaryProductType().getSaleCost();
            }
        }
        for (Animal animal : animals){
            if (animal instanceof WildAnimal){
                paidMoney += ((WildAnimal) animal).getWildAnimalType().getSaleCost();
            }
        }
        return paidMoney;
    }
}
