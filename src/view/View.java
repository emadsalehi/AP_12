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

    public void logInfo(String string) {
        System.out.println(string);
    }
  
    public void logMap (String string) {
        System.out.println(string);
    }
  
    public void logLevel (String string) {
        System.out.println(string);
    }
  
    public void logWarehouse (String string) {
        System.out.println(string);
    }
  
    public void logWell (String string) {
        System.out.println(string);
    }
  
    public void logWorkshop (String string) {
        System.out.println(string);
    }
  
    public void logTruck (String string) {
        System.out.println(string);
    }
    public void logHelicopter (String string) {

    }



public void logNoWildAnimalFound(){
        System.out.println("No Wild Animal Found");
    }


    public void logWrongCommand() {
        System.out.println("Wrong command!");
    }

    public void logWorkShopIsWorking() {
        System.out.println("WorkShop is working!");
    }

    public void logRequirementsIsNotEnough() {
        System.out.println("Requirements is not enough!");
    }

    public void logNotEnoughMoney() {
        System.out.println("Not enough money!");
    }

    public void logWellIsWorking() {
        System.out.println("Well is working!");
    }

    public void logLevelIsHighest() {
        System.out.println("Level is highest.");
    }

    public void logTruckIsNotAvailable() {
        System.out.println("Truck is not available!");
    }

    public void logHelicopterIsNotAvailable() {
        System.out.println("Helicopter is not available!");
    }
}
