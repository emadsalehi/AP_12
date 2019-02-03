package model;

import java.util.HashMap;

public class Level {
    private int requiredMoney = 3000;
    private HashMap<Animal, Integer> requiredAnimals = new HashMap<>(); {
        requiredAnimals.put(new FarmAnimal(3, 5,FarmAnimalType.CHICKEN), 5);
    }
    private HashMap<Product, Integer> requiredProduct = new HashMap<>(); {
        requiredProduct.put(new PrimitiveProduct(PrimitiveProductType.EGG), 20);
        requiredProduct.put(new SecondaryProduct(SecondaryProductType.EGG_POWDER), 10);
        requiredProduct.put(new SecondaryProduct(SecondaryProductType.COOKIE), 20);
        requiredProduct.put(new SecondaryProduct(SecondaryProductType.CLOTHES), 5);
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
