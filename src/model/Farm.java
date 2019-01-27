package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Farm {
    private final int maxPlantLevel = 5;
    private Cell[][] cells = new Cell[30][30]; {
        for (int i = 0; i < 30; i++)
            for (int j = 0; j < 30; j++)
                cells[i][j] = new Cell();
    }
    private int money = 1000;
    private Storage storage = new Storage();
    private Well well = new Well();
    private java.util.ArrayList<WorkShop> workShops = new ArrayList<>();
    private Helicopter helicopter = new Helicopter();
    private Truck truck = new Truck();
    private Level level = new Level();
    private int time;

    public Farm(ArrayList<WorkShop> workShops) {
        this.workShops = workShops;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void updateCells() {
        displacer();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                Cell cell = cells[i][j];
                checkWildAndFarmCollision(cell);
                checkWildAndProductCollision(cell);
                catProductCollision(cell);
                for (Animal animal : cell.getAnimals()) {
                    if (animal instanceof FarmAnimal) {
                        if (cell.isHasPlant()) {
                            if (((FarmAnimal) animal).isHungry()) {
                                animal.setMoving(false);
                                ((FarmAnimal) animal).setTimeTillFill(((FarmAnimal) animal).getTimeTillFill() - 1);
                                cell.setPlantLevel(cell.getPlantLevel() - 1);
                                if (((FarmAnimal) animal).getTimeTillFill() == 0) {
                                    ((FarmAnimal) animal).setHungry(false);
                                    ((FarmAnimal) animal).setTimeTillFill(5);
                                    animal.setMoving(true);
                                }
                            }
                            if (cell.getPlantLevel() == 0) {
                                cell.setHasPlant(false);
                            }
                        }
                        ((FarmAnimal) animal).nextTurn();
                        if (((FarmAnimal) animal).isReadyToProduce()) {
                            ((FarmAnimal) animal).setReadyToProduce(false);
                            ArrayList<Product> products = cell.getProducts();
                            if (((FarmAnimal) animal).getFarmAnimalType().equals(FarmAnimalType.CHICKEN)) {
                                products.add(new PrimitiveProduct(PrimitiveProductType.EGG));
                                cell.setProducts(products);
                            }
                            else if (((FarmAnimal) animal).getFarmAnimalType().equals(FarmAnimalType.COW)) {
                                products.add(new PrimitiveProduct(PrimitiveProductType.MILK));
                                cell.setProducts(products);
                            }
                            else {
                                products.add(new PrimitiveProduct(PrimitiveProductType.WOOL));
                                cell.setProducts(products);
                            }
                        }
                    }
                }

            }
        }
    }

    public Cell checkWildAndFarmCollision(Cell cell) {

        ArrayList<Animal> cellAnimals = cell.getAnimals();
        ArrayList<Animal> farmAnimals = new ArrayList<Animal>();
        ArrayList<Animal> wildAnimals = new ArrayList<Animal>();
        for (Animal animal : cellAnimals) {
            if (animal instanceof FarmAnimal) {
                farmAnimals.add(animal);
            } else {
                wildAnimals.add(animal);
            }
        }
        if (!farmAnimals.isEmpty() && !wildAnimals.isEmpty()) {
            cellAnimals.clear();
            cell.setAnimals(cellAnimals);
        }
        return cell;
    }

    public Cell checkWildAndProductCollision(Cell cell) {
        ArrayList<Animal> cellAnimals = cell.getAnimals();
        ArrayList<Product> cellProducts = cell.getProducts();
        ArrayList<Animal> wildAnimals = new ArrayList<>();
        for (Animal animal : cellAnimals) {
            if (animal instanceof WildAnimal) {
                wildAnimals.add(animal);
            }
        }
        if (!wildAnimals.isEmpty()) {
            cellProducts.clear();
            cell.setProducts(cellProducts);
        }
        return cell;
    }

    public void displacer() {
        for (int i = 0; i < 30; i++)
            for (int j = 0; j < 30; j++) {
                Iterator<Animal> animalIterator = cells[i][j].getAnimals().iterator();
                while (animalIterator.hasNext()) {
                    Animal animal = animalIterator.next();
                    if (animal.getX() != i && animal.getY() != j) {
                        cells[animal.getX()][animal.getY()].getAnimals().add(animal);
                        animalIterator.remove();
                    }
                }
//                for (Animal animal : this.cells[i][j].getAnimals()) {
//                    this.cells[animal.getX()][animal.getY()].getAnimals().add(animal);
//                    this.cells[i][j].getAnimals().remove(animal);
//                }
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
        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if (i >= 0 && i < 30 && j >= 0 && j < 30) {
                    this.cells[i][j].setPlantLevel(maxPlantLevel);
                    this.cells[i][j].setHasPlant(true);
                }
    }

    public void userPickUp(int x, int y) {
        Cell cell = cells[x][y];
        if (cell.getProducts().size() != 0) {
            ArrayList<Product> cellProducts = cell.getProducts();
            Product pickedUpProduct = cellProducts.get(cellProducts.size() - 1);
            ArrayList<Product> storageProducts = storage.getProducts();
            storageProducts.add(pickedUpProduct);
            storage.setProducts(storageProducts);
            cellProducts.remove(cellProducts.size() - 1);
            cell.setProducts(cellProducts);
        }
        //todo If product exist store it in storage and remove it from cells.
    }

    public boolean levelPassedChecker() {
        if (money > level.getRequiredMoney()) {
            HashMap<Animal, Integer> requiredAnimals = level.getRequiredAnimals();
            HashMap<Product, Integer> requiredProduct = level.getRequiredProduct();

            ArrayList<Animal> storageAnimals = storage.getAnimals();
            ArrayList<Product> storageProducts = storage.getProducts();

            for (HashMap.Entry<Animal, Integer> entry : requiredAnimals.entrySet()) {
                Integer numberOfAnimals = 0;
                for (Animal animal : storageAnimals) {
                    if (animal.equals(entry.getKey())) {
                        numberOfAnimals++;
                    }
                }
                if (numberOfAnimals < entry.getValue()) {
                    return false;
                }
            }
            for (HashMap.Entry<Product, Integer> entry : requiredProduct.entrySet()) {
                Integer numberOfProducts = 0;
                for (Product product : storageProducts) {
                    if (product.equals(entry.getKey())) {
                        numberOfProducts++;
                    }
                }
                if (numberOfProducts < entry.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addAnimal(Animal animal) {
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
