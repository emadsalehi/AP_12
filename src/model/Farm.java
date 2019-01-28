package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Farm {
    private final int maxPlantLevel = 4;
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
                                cell.setPlantLevel(cell.getPlantLevel() - 2);
                                if (((FarmAnimal) animal).getTimeTillFill() == 0) {
                                    ((FarmAnimal) animal).setHungry(false);
                                    ((FarmAnimal) animal).setTimeTillFill(5);
                                    ((FarmAnimal) animal).setTimeTillHungry(17);
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
        boolean hasWild = false, hasFarm = false;
        for (Animal animal : cellAnimals) {
            if (animal instanceof WildAnimal)
                hasWild = true;
            else if (animal instanceof FarmAnimal)
                hasFarm = true;
        }
        if (hasFarm && hasWild) {
            cellAnimals.clear();
            cell.setAnimals(cellAnimals);
        }
        return cell;
    }

    public Cell checkWildAndProductCollision(Cell cell) {
        ArrayList<Product> cellProducts = cell.getProducts();
        for (Animal animal : cell.getAnimals()) {
            if (animal instanceof WildAnimal) {
                cellProducts.clear();
                cell.setProducts(cellProducts);
                return cell;
            }
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
            }
    }

    public Cell catProductCollision(Cell cell) {
        ArrayList<Product> products = cell.getProducts();
        for (Animal animal : cell.getAnimals()) {
            if(animal instanceof Cat) {
                for (Product product : products)
                    storage.getProducts().add(product);
                products.clear();
                cell.setProducts(products);
                return cell;
            }
        }
        return cell;
    }

    public void irrigate(int x, int y) {
        this.cells[x][y].setPlantLevel(maxPlantLevel);
        this.cells[x][y].setHasPlant(true);
    }

    public boolean userPickUp(int x, int y) {
        Cell cell = cells[x][y];
        if (cell.getProducts().size() != 0) {
            ArrayList<Product> cellProducts = cell.getProducts();
            Product pickedUpProduct = cellProducts.get(cellProducts.size() - 1);
            ArrayList<Product> storageProducts = storage.getProducts();
            storageProducts.add(pickedUpProduct);
            storage.setProducts(storageProducts);
            cellProducts.remove(cellProducts.size() - 1);
            cell.setProducts(cellProducts);
            return true;
        } else
            return false;
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
