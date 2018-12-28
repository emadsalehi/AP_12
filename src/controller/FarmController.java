package controller;
import model.*;
import model.exceptions.NotPossibleException;
import model.request.PrintRequest;
import view.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonStreamParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class FarmController {
    private Farm farm = new Farm();
    private View view = new View();
    private CommandAnalyzer commandAnalyzer = new CommandAnalyzer();

    public void listenForCommand() {
        String command = view.getInput();
    }

    public void addAction() {

    }

    public void buyAction() {

    }

    public void cageAction() {

    }

    public void clearAction() {

    }

    public void goAction() {

    }

    public void loadCustomAction(String path) throws FileNotFoundException {
        Gson gson = new Gson();
        ArrayList<WorkShop> workShops = farm.getWorkShops();
        CustomWorkShop customWorkShop = gson.fromJson(new FileReader(path), CustomWorkShop.class);
        workShops.add(customWorkShop);
        farm.setWorkShops(workShops);
    }

    public void loadGameAction(String path) throws FileNotFoundException {
        Gson gson = new Gson();
        farm = gson.fromJson(new FileReader(path), Farm.class);
    }

    public void pickUpAction(int x, int y) {
        if ( x < 0 || x > 29 || y < 0 || y > 29) {
            throw  new NotPossibleException("pickUp");
        }
        else {
            farm.userPickUp(x, y);
        }
    }

    public void plantAction(int x, int y) {
        int[] xDisplace = {-1,0,1,-1,0,1,-1,0,1};
        int[] yDisplace = {-1,-1,-1,0,0,0,1,1,1};

        for ( int i = 0 ; i < 9 ; i++) {
            if (x + xDisplace[i] < 0 || x+xDisplace[i]>29 || y+yDisplace[i] < 0 || y+yDisplace[i] > 29) {
                continue;
            }
            else {
                farm.irrigate(x + xDisplace[i], y + yDisplace[i]);
            }
        }
    }

    public void printAction(PrintRequest request) {
        String printFrom = request.getPartToPrintName();
        if (printFrom.equals("info")) {
            view.logInfo(farm.getMoney(), farm.getTime(), farm.getLevel(),farm.getStorage());
        }
        else if (printFrom.equals("map")) {
            view.logMap(farm);
        }
        else if (printFrom.equals("levels")){
            view.logLevel(farm.getLevel());
        }
        else if (printFrom.equals("warehouse")) {
            view.logWarehouse(farm.getStorage());
        }
        else if (printFrom.equals("well")) {
            view.logWell(farm.getWell());
        }
        else if (printFrom.equals("workshops")) {
            view.logWorkshop(farm);
        }
        else if (printFrom.equals("truck")) {
            view.logTruck(farm.getTruck());
        }
        else if (printFrom.equals("helicopter")) {
            view.logHelicopter(farm.getHelicopter());
        }
        //print [info|map|levels|warehouse|well|workshops|truck|helicopter]
    }

    public void runAction(String path) throws FileNotFoundException {
        Gson gson = new Gson();
        farm = gson.fromJson(new FileReader(path), Farm.class);
    }

    public void saveGameAction() {

    }

    public void startAction() {

    }

    public void turnAction() {

    }

    public void upgradeAction() {

    }

    public void wellAction() {

    }
}
