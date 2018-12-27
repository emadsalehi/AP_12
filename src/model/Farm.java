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

    public void updateCells() {
    }

    public Cell checkWildAndFarmCollision(Cell cell) {
        return null;
    }//not mine

    public void displacer() {
    } //not mine

    public Cell catProductCollision(Cell cell) {
        return null;
    } // not mine

    public void irrigate(int x, int y) {
    }  //not mine

    public void userPickUp(int x, int y) {
        Cell cell = cells[x][y];
        if (cell.getProducts().size() != 0) {
            ArrayList<Product> cellProducts = cell.getProducts();
            Product pickedUpProduct = cellProducts.get(cellProducts.size()-1);
            ArrayList<Product> storageProducts = storage.getProducts();
            storageProducts.add(pickedUpProduct);
            storage.setProducts(storageProducts);
            cellProducts.remove(cellProducts.size()-1);
            cell.setProducts(cellProducts);
        }
        //todo If product exist store it in storage and remove it from cells.
    }

    public void throwWorkshopProduct() {
        WorkShop workShop = workShops.get(0);
        if (workShop.getTimeToFinish() == workShop.getMaxTimeToFinish() + 1 && !workShop.isWorking()) {
            Cell destinationCell = cells [workShop.getThrowedProductX()] [workShop.getThrowedProductY()];
            ArrayList<Product> cellProducts = destinationCell.getProducts();
            for ( int i = 0 ; i < workShop.getNumberOfProcessedProduct() ; i++) {
                cellProducts.add(workShop.getProduct());
            }
            cells [workShop.getThrowedProductX()] [workShop.getThrowedProductY()].setProducts(cellProducts);
        }

        //todo Check hoursToFinish equals maxHoursToFinish + 1 and isWorking equals false throw product in destination.
    }

    public void levelPassedChecker() {
    }

    public ArrayList<Animal> getAnimals() {
        return null;
    }
}
