package model;

import java.io.Serializable;
import java.util.HashMap;

public class Level implements Serializable {
    private int requiredMoney = 3000;
    private HashMap<Animal, Integer> requiredAnimals = new HashMap<>(); {
        requiredAnimals.put(new Cat(3, 5), 20);
    }
    private HashMap<Product, Integer> requiredProduct = new HashMap<>(); {
        requiredProduct.put(new PrimitiveProduct(PrimitiveProductType.EGG), 20);
    }

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
