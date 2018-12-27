package view;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class View {
    public String getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public void logNotEnoughMoney() {
        System.out.println("Not Enough Money");
    }
    public void logInfo(int money, int time, Level level, Storage storage) {
        System.out.println("Money: " + money + " Time: " + time);
        System.out.println("Level: ");
        if (level.getRequiredMoney() != 0) {
            System.out.println("Money: " + money + "/" + level.getRequiredMoney()+' ');
        }
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
            System.out.print(entry.getKey().getClass().getSimpleName()+": "+numberOfAnimals.toString()+"/" +
                    entry.getValue()+" ");

        }
        for (HashMap.Entry<Product, Integer> entry : requiredProduct.entrySet()) {
            Integer numberOfProducts = 0;
            for (Product product : storageProducts) {
                if (product.equals(entry.getKey())) {
                    numberOfProducts++;
                }
            }
            System.out.print(entry.getKey().getClass().getSimpleName()+": "+numberOfProducts.toString()+"/" +
                    entry.getValue()+" ");
        }
    }
    public void logMap (Farm farm) {
        Cell[][] cells = farm.getCells();
        for (int i = 0 ; i < 30 ; i++) {
            for (int j = 0; j < 30 ; j++) {
                Cell cell = cells[i][j];
                if (cell.getAnimals().size() != 0) {
                    System.out.print(cell.getAnimals().get(0).getClass().getSimpleName().codePointAt(0));
                }
                else if (cell.getProducts().size() != 0) {
                    System.out.print(cell.getProducts().get(0).getClass().getSimpleName().codePointAt(0));
                }

                else if (cell.isHasPlant()) {
                    System.out.print("O");
                }
            }
            System.out.printf("\n");
        }
    }
    public void logLevel (Level level) {
        if (level.getRequiredMoney() != 0) {
            System.out.println("Money: " +level.getRequiredMoney()+" ");
        }
        HashMap<Animal, Integer> requiredAnimals = level.getRequiredAnimals();
        HashMap<Product, Integer> requiredProduct = level.getRequiredProduct();
        for (HashMap.Entry<Animal, Integer> entry : requiredAnimals.entrySet()) {
            System.out.print(entry.getKey().getClass().getSimpleName() + ": " +entry.getValue()+" ");
        }
        for (HashMap.Entry<Product, Integer> entry : requiredProduct.entrySet()) {
            System.out.print(entry.getKey().getClass().getSimpleName() + ": " +entry.getValue()+" ");
        }
    }
    public void logWarehouse (Storage storage) {
        ArrayList <Product> products = storage.getProducts();
        ArrayList <Animal> animals = storage.getAnimals();
        PROBLEM
    }
    public void logWell (Well well) {
        System.out.println(well.getWaterLeft()+"/"+well.getCapacity());
    }
    public void logWorkshop (Farm farm) {
        ArrayList<WorkShop> workShops = farm.getWorkShops();
        for (WorkShop workShop : workShops) {
            if (!workShop.getClass().getSimpleName().equals("CustomWorkShop")) {
                System.out.print(workShop.);
                PROBLEM
            }
            else {
                System.out.print(workShop.getClass().getSimpleName()+" ");
            }
        }
    }
    public void logTruck () {

    }


}