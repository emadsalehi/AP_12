package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Truck implements Serializable {
    private int level = 0;
    private double speed = 1 / (20 - 5 * level);
    private int capacity = (level + 2) * 20;
    private boolean isAvailable = true;
    private boolean readyToPay = false;
    private int upgradeCost = 250;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private int travelDuration = 20;
    private int travelCounter = 20;

    public int getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(int travelDuration) {
        this.travelDuration = travelDuration;
    }

    public int getTravelCounter() {
        return travelCounter;
    }

    public void setTravelCounter(int travelCounter) {
        this.travelCounter = travelCounter;
    }

    /*public Truck(int level, double speed, int capacity, boolean isAvailable, boolean readyToPay) {
        this.level = level;
        this.speed = speed;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
        this.readyToPay = readyToPay;
    }*/

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.speed = 1 / (20 - 5 * level);   //speed = 1/travelDuration
        this.travelDuration = 20 - 5 * level;
        this.travelCounter = this.travelDuration;
        this.capacity = (level + 2) * 20;    //TODO: Capacity consists of slots
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
            } else if (animal instanceof  FarmAnimal){
                paidMoney += ((FarmAnimal) animal).getFarmAnimalType().getBuyCost() / 2;
            }
        }
        return paidMoney;
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
            } else if (a instanceof FarmAnimal){
                usedCapacity += ((FarmAnimal) a).getFarmAnimalType().getDepotSize();
            }
        }
        return usedCapacity;
    }

    public void upgrade(){
        setLevel(getLevel() + 1);
    }
    public void nextTurn(){
        if(!isAvailable){
            travelCounter--;
            //System.out.println(travelCounter);
            //System.out.println(calculatePaidMoney());
            if (travelCounter == 0){
                isAvailable = true;
                readyToPay = true;
                travelCounter = travelDuration;
            }
        }
    }
    public void clearProducts(){
        products.clear();
    }
    public void clearAnimals(){
        animals.clear();
    }

}
