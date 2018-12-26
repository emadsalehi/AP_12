package model;

import java.util.ArrayList;

public class Cell {
    private java.util.ArrayList<Animal> animals = new ArrayList<>();
    private java.util.ArrayList<Product> products = new ArrayList<>();
    private boolean hasPlant = false;
    private int plantLevel;

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
