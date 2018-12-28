package model;

import model.exceptions.NotPossibleException;

import java.util.ArrayList;

public class Storage {
    private int level = 1;
    private int capacity = 50; //todo calculate in level setter.
    private int upgradePrice = 250;
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void upgradeStorage () {
        switch (this.level ) {
            case 1:
                this.level = 2;
                this.capacity = 150;
                this.upgradePrice = 400;
                break;
            case 2:
                this.level = 3;
                this.capacity = 300;
                this.upgradePrice = 700;
                break;
            case 3:
                this.level = 4;
                this.capacity = 600;
                this.upgradePrice = 900;
                break;
            default:
                throw new NotPossibleException("Storage");
        }
    }
    

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public int calculateUsedCapacity(){
        int usedCapacity = 0;
        for(Product p : products){
            if (p instanceof PrimitiveProduct){
                usedCapacity += ((PrimitiveProduct) p).getPrimitiveProductType().getDepotSize();
            }else if (p instanceof  SecondaryProduct){
                usedCapacity += ((SecondaryProduct) p).getSecondaryProductType().getDepotSize();
            }
        }
        for (Animal a : animals){
            if (a instanceof WildAnimal){
                usedCapacity += ((WildAnimal) a).getWildAnimalType().getDepotSize();
            }
        }
        return usedCapacity;
    }
}
