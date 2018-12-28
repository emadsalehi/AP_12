package model;

import java.util.ArrayList;

public class Farm {
    private Cell[][] cells = new Cell[30][30];
    private int money = 1000;
    private Storage storage;
    private Well well;
    private java.util.ArrayList<WorkShop> workShops = new ArrayList<>();
    private Helicopter helicopter;
    private Truck truck;
    private Level level;
    private final int maxPlantLevel = 5;

    public void updateCells() {
    }

    public Cell checkWildAndFarmCollision(Cell cell) {
        for(int i = 0 ; i < 30 ; i++){
            for (int j = 0 ; j < 30 ; j++){
                ArrayList<Animal> cellAnimals = cells[i][j].getAnimals();
                ArrayList<Animal> farmAnimals = new ArrayList<Animal>();
                ArrayList<Animal> wildAnimals = new ArrayList<Animal>();
                for (Animal animal : cellAnimals){
                    if(animal instanceof FarmAnimal){
                        farmAnimals.add(animal);
                    }else {
                        wildAnimals.add(animal);
                    }
                }
                if (!farmAnimals.isEmpty() && !wildAnimals.isEmpty()){
                    cellAnimals.clear();
                    cells[i][j].setAnimals(cellAnimals);
                }
            }
        }
        return null;
    }

    public void displacer() {
        for (int i = 0 ; i < 30 ; i++)
            for (int j = 0 ; j < 30 ; j++)
                for (Animal animal : this.cells[i][j].getAnimals()) {
                    this.cells[animal.getX()][animal.getY()].getAnimals().add(animal);
                    this.cells[i][j].getAnimals().remove(animal);
                }
    }

    public Cell catProductCollision(Cell cell) {
        ArrayList<Product> products = cell.getProducts();
        ArrayList<Product> storageProducts = this.getStorage().getProducts();
        for (Product product : products)
            storageProducts.add(product);
        this.storage.setProducts(storageProducts);
        cell.setProducts(new ArrayList<Product>());
        return cell;
    }

    public void irrigate(int x, int y) {
        for (int i = x - 1 ; i <= x + 1 ; i++)
            for (int j = y - 1 ; j <= y + 1 ; j++)
                if (i >= 0 && i < 30 && j >= 0 && j < 30) {
                    this.cells[i][j].setPlantLevel(maxPlantLevel);
                    this.cells[i][j].setHasPlant(true);
                }
    }

    public void userPickUp(int x, int y) {
        //todo If product exist store it in storage and remove it from cells.
    }

    public void throwWorkshopProduct() {
        //todo Check hoursToFinish equals maxHoursToFinish + 1 and isWorking equals false throw product in destination.
    }

    public void levelPassedChecker() {
    }

    public ArrayList<Animal> getAnimals() {
        return null;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addAnimal(Animal animal){
        ArrayList<Animal> animals = cells[animal.getX()][animal.getY()].getAnimals();
        animals.add(animal);
        cells[animal.getX()][animal.getY()].setAnimals(animals);
    }


    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public ArrayList<WorkShop> getWorkShops() {
        return workShops;
    }

    public void setWorkShops(ArrayList<WorkShop> workShops) {
        this.workShops = workShops;
    }


    public Helicopter getHelicopter() {
        return helicopter;
    }

    public void setHelicopter(Helicopter helicopter) {
        this.helicopter = helicopter;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }


    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
