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
    }

    public void displacer() {
    }

    public Cell catProductCollision(Cell cell) {
        return null;
    }

    void irrigate(int x, int y) {
    }

    public void userPickUp(int x, int y) {
        //todo If product exist store it in storage and remove it from cells.
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
}
