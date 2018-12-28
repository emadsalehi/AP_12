package model;

import java.util.ArrayList;

public class Cell {
    private java.util.ArrayList<Animal> animals = new ArrayList<>();
    private java.util.ArrayList<Product> products = new ArrayList<>();
    private boolean hasPlant = false;
    private int plantLevel;



    public void addProduct(Product product) {
        this.products.add(product);
    }

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

    public boolean isHasPlant() {
        return hasPlant;
    }

    public void setHasPlant(boolean hasPlant) {
        this.hasPlant = hasPlant;
    }

    public int getPlantLevel() {
        return plantLevel;
    }

    public void setPlantLevel(int plantLevel) {
        this.plantLevel = plantLevel;
    }
}
