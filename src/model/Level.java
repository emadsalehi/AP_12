package model;

import java.util.HashMap;

public class Level {
    private int requiredMoney;
    private HashMap<Animal, Integer> requiredAnimals = new HashMap<>();
    private HashMap<Product, Integer> requiredProduct = new HashMap<>();

    public int getRequiredMoney() {
        return requiredMoney;
    }

    public void setRequiredMoney(int requiredMoney) {
        this.requiredMoney = requiredMoney;
    }

    public HashMap<Animal, Integer> getRequiredAnimals() {
        return requiredAnimals;
    }

    public void setRequiredAnimals(HashMap<Animal, Integer> requiredAnimals) {
        this.requiredAnimals = requiredAnimals;
    }

    public HashMap<Product, Integer> getRequiredProduct() {
        return requiredProduct;
    }

    public void setRequiredProduct(HashMap<Product, Integer> requiredProduct) {
        this.requiredProduct = requiredProduct;
    }
}
