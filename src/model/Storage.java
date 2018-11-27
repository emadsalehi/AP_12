package model;

import java.util.ArrayList;

public class Storage {
    private int level = 1;
    private int capacity; //todo calculate in level setter.
    private int upgradePrice;
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
}
