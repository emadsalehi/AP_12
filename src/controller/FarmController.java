package controller;
import com.google.gson.Gson;
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


import model.request.*;
import view.View;

import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;

public class FarmController {
    private Farm farm = new Farm();
    private View view = new View();
    private int truckTravelTimer = (int)(1.0 / farm.getTruck().getSpeed());
    private int helicopterTravelTimer = (int)(1.0 / (int)farm.getHelicopter().getSpeed());
    private CommandAnalyzer commandAnalyzer = new CommandAnalyzer();

    public void listenForCommand() {
        boolean requestsOnTheWay = true;
        while (requestsOnTheWay) {
            String command = view.getInput().trim();
            Request request = commandAnalyzer.getRequest(command);
            if (request instanceof AddRequest){
                addAction((AddRequest) request);
            }else if (request instanceof BadRequest){
                badAction((BadRequest) request);
            }else if (request instanceof BuyRequest){
                buyAction((BuyRequest) request);
            }else if (request instanceof CageRequest){
                cageAction((CageRequest) request);
            }else if (request instanceof ClearRequest){
                clearAction((ClearRequest) request);
            }else if (request instanceof GoRequest){
                goAction((GoRequest) request);
            }else if (request instanceof LoadCustomRequest){
                loadCustomAction((LoadCustomRequest) request);
            }else if (request instanceof LoadGameRequest){
                loadGameAction((LoadGameRequest) request);
            }else if (request instanceof PickUpRequest){
                pickUpAction((PickUpRequest) request);
            }else if (request instanceof PlantRequest){
                plantAction((PlantRequest) request);
            }else if (request instanceof PrintRequest){
                printAction((PrintRequest) request);
            }else if (request instanceof RunRequest){
                runAction((RunRequest) request);
            }else if (request instanceof SaveGameRequest){
                saveGameAction((SaveGameRequest) request);
            }else if (request instanceof StartRequest) {
                startAction((StartRequest) request);
            }else if (request instanceof TurnRequest){
                turnAction((TurnRequest) request);
            }else if (request instanceof UpgradeRequest){
                upgradeAction((UpgradeRequest) request);
            }else if (request instanceof WellRequest){
                wellAction((WellRequest) request);
            }else if (request instanceof ExitRequest){
                requestsOnTheWay = false;
            }
        }

    }

    public void addAction(AddRequest request) {
        Truck truck = farm.getTruck();
        Helicopter helicopter = farm.getHelicopter();
        if (request.getVehicleTypeName().equals("truck")){
            for (WildAnimalType wildAnimalType : WildAnimalType.values()){
                if (wildAnimalType.name().equals(request.getItemName())){
                    if (truck.getCapacity() >= truck.calculateUsedCapacity() + wildAnimalType.getDepotSize()){
                        ArrayList<Animal> animals = truck.getAnimals();
                        animals.add(new WildAnimal(0 , 0 , wildAnimalType));
                        truck.setAnimals(animals);
                        farm.setTruck(truck);
                    } //TODO else ---> print not enough capacity
                }
            }
            for (FarmAnimalType farmAnimalType : FarmAnimalType.values()){
                if(farmAnimalType.name().equals(request.getItemName())){
                    if(truck.getCapacity() >= truck.calculateUsedCapacity() + farmAnimalType.getDepotSize()){
                        ArrayList<Animal> animals = truck.getAnimals();
                        animals.add(new FarmAnimal(0 , 0 , farmAnimalType));
                        truck.setAnimals(animals);
                        farm.setTruck(truck);
                    }
                }
            }
            for (PrimitiveProductType primitiveProductType : PrimitiveProductType.values()){
                if(primitiveProductType.name().equals(request.getItemName())){
                    if(truck.getCapacity() >= truck.calculateUsedCapacity() + primitiveProductType.getDepotSize()){
                        ArrayList<Product> products = truck.getProducts();
                        products.add(new PrimitiveProduct(primitiveProductType));
                        truck.setProducts(products);
                        farm.setTruck(truck);
                    }
                }
            }
            for (SecondaryProductType secondaryProductType : SecondaryProductType.values()){
                if(secondaryProductType.name().equals(request.getItemName())){
                    if(truck.getCapacity() >= truck.calculateUsedCapacity() + secondaryProductType.getDepotSize()){
                        ArrayList<Product> products = truck.getProducts();
                        products.add(new SecondaryProduct(secondaryProductType));
                        truck.setProducts(products);
                        farm.setTruck(truck);
                    }
                }
            }
        }else if (request.getVehicleTypeName().equals("helicopter")){
            for (PrimitiveProductType primitiveProductType : PrimitiveProductType.values()){
                if(primitiveProductType.name().equals(request.getItemName())){
                    if(helicopter.getCapacity() > helicopter.calculateUsedCapacity() + primitiveProductType.getDepotSize()
                            && farm.getMoney() >= helicopter.calculateRequiredMoney() + primitiveProductType.getBuyCost()){
                        ArrayList<Product> products = helicopter.getProducts();
                        products.add(new PrimitiveProduct(primitiveProductType));
                        helicopter.setProducts(products);
                        farm.setMoney(farm.getMoney() - primitiveProductType.getBuyCost());
                        farm.setHelicopter(helicopter);
                    }
                }
            }
        }
    }

    public void badAction(BadRequest request) {

    }

    public void buyAction(BuyRequest request) {
        int buyCost;
        if(request.getAnimal() instanceof FarmAnimal){
            buyCost = ((FarmAnimal) request.getAnimal()).getFarmAnimalType().getBuyCost();
        }else {
            buyCost = 2500;
        }
        if(farm.getMoney() < buyCost){
            view.logNotEnoughMoney();
        }else {
            farm.addAnimal(request.getAnimal());
            farm.setMoney(farm.getMoney() - buyCost);
        }
    }

    public void cageAction(CageRequest request) {
        boolean wildFoundFlag = false;
        Storage storage = farm.getStorage();
        Cell[][] cells = farm.getCells();
        ArrayList<Animal> cellAnimals = cells[request.getX()][request.getY()].getAnimals();
        Iterator<Animal> animalIterator = cellAnimals.iterator();
        while (animalIterator.hasNext()){
            Animal animal = animalIterator.next();
            if (animal instanceof WildAnimal){
                wildFoundFlag = true;
                if (((WildAnimal) animal).getWildAnimalType().getDepotSize() + storage.calculateUsedCapacity() <= storage.getCapacity()){
                    ArrayList<Animal> animals = storage.getAnimals();
                    animals.add(animal);
                    storage.setAnimals(animals);
                    animalIterator.remove();
                }else {
                    //TODO Cage for some turns
                }
            }
        }
        farm.setStorage(storage);
        cells[request.getX()][request.getY()].setAnimals(cellAnimals);
        farm.setCells(cells);
    }

    public void clearAction(ClearRequest request) {
        if (request.getVehicleTypeName().equals("truck")){
            Truck truck = farm.getTruck();
            Storage storage = farm.getStorage();
            ArrayList<Animal> truckAnimals = truck.getAnimals();
            ArrayList<Product> truckProducts = truck.getProducts();
            ArrayList<Animal> storageAnimals = storage.getAnimals();
            ArrayList<Product> storageProducts = storage.getProducts();
            for (Animal animal : truck.getAnimals()){
                storageAnimals.add(animal);
            }
            for (Product product : truck.getProducts()){
                storageProducts.add(product);
            }
            storage.setAnimals(storageAnimals);
            storage.setProducts(storageProducts);
            farm.setStorage(storage);
            truckAnimals.clear();
            truckProducts.clear();
            truck.setAnimals(truckAnimals);
            truck.setProducts(truckProducts);
            farm.setTruck(truck);
        }else{
            Helicopter helicopter = farm.getHelicopter();
            ArrayList<Product> helicopterProducts = helicopter.getProducts();
            farm.setMoney(farm.getMoney() + helicopter.calculateRequiredMoney());
            helicopterProducts.clear();
            helicopter.setProducts(helicopterProducts);
            farm.setHelicopter(helicopter);
        }
    }

    public void goAction(GoRequest request) {
        if(request.getVehivlePartName().equals("truck")){
            Truck truck = farm.getTruck();
            truck.setAvailable(false);
            farm.setTruck(truck);
        }else{
            Helicopter helicopter = farm.getHelicopter();
            helicopter.setAvailable(false);
            farm.setHelicopter(helicopter);
        }
    }

    public void loadCustomAction(LoadCustomRequest request) {

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

    public void saveGameAction(SaveGameRequest request) {
        Gson gson = new Gson();
        try {
            gson.toJson(farm, new FileWriter(request.getPathToJsonFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAction(StartRequest request) {
        //todo custom workshop not handled.
        //todo set in farm workshop not handled.
        WorkShop selectedWorkShop = null;
        for (WorkShop workShop : farm.getWorkShops()) {
            if (request.getWorkShopName().equals("sewingfactory") && workShop instanceof SewingFactory)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("spinnery") && workShop instanceof Spinnery)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("eggpowderplant") && workShop instanceof EggPowderPlant)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("weavingfactory") && workShop instanceof WeavingFactory)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("cookiebakery") && workShop instanceof CookieBakery)
                selectedWorkShop = workShop;
            else if (request.getWorkShopName().equals("cakebakery") && workShop instanceof CakeBakery)
                selectedWorkShop = workShop;
            else {
                view.logWrongCommand();
                return;
            }
        }
        if (selectedWorkShop.isWorking()) {
            view.logWorkShopIsWorking();
            return;
        }
        Storage storage = farm.getStorage();
        if (selectedWorkShop instanceof SewingFactory) {
            Product product1 = null;
            Product product2 = null;
            for (Product product : storage.getProducts()) {
                if (product instanceof SecondaryProduct && ((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.CLOTHES)
                    product1 = product;
                else if (product instanceof PrimitiveProduct && ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.plume)
                    product2 = product;
            }
            if (product1 == null || product2 == null) {
                view.logRequirementsIsNotEnough();
                return;
            }
            storage.getProducts().remove(product1);
            storage.getProducts().remove(product2);
        } else if (selectedWorkShop instanceof Spinnery) {
            Product product1 = null;
            for (Product product : storage.getProducts())
                if (product instanceof PrimitiveProduct &&
                        ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.wool)
                    product1 = product;
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return;
            }
            storage.getProducts().remove(product1);
        } else if (selectedWorkShop instanceof EggPowderPlant) {
            Product product1 = null;
            for (Product product : storage.getProducts())
                if (product instanceof PrimitiveProduct &&
                        ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.egg)
                    product1 = product;
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return;
            }
            storage.getProducts().remove(product1);
        } else if (selectedWorkShop instanceof WeavingFactory) {
            Product product1 = null;
            for (Product product : storage.getProducts())
                if (product instanceof SecondaryProduct &&
                        ((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.THREAD)
                    product1 = product;
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return;
            }
            storage.getProducts().remove(product1);
        } else if (selectedWorkShop instanceof CookieBakery) {
            Product product1 = null;
            for (Product product : storage.getProducts())
                if (product instanceof SecondaryProduct &&
                        ((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.EGG_POWDER)
                    product1 = product;
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return;
            }
            storage.getProducts().remove(product1);
        } else if (selectedWorkShop instanceof CakeBakery) {
            Product product1 = null;
            Product product2 = null;
            for (Product product : storage.getProducts()) {
                if (product instanceof PrimitiveProduct &&
                        ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.flour)
                    product1 = product;
                else if (product instanceof SecondaryProduct &&
                        ((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.COOKIE)
                    product2 = product;
            }
            if (product1 == null || product2 == null) {
                view.logRequirementsIsNotEnough();
                return;
            }
            storage.getProducts().remove(product1);
            storage.getProducts().remove(product2);
        }
        farm.setStorage(storage);
        selectedWorkShop.startWorkShop();
    }

    public void turnAction(TurnRequest request) {
        for (int i = 0 ; i < request.getNumberOfTurns() ; i++) {
            for (Animal animal : farm.getAnimals()) {
                Integer[] destination = getAnimalDestination(animal, farm.getCells());
                animal.move(destination[0], destination[1]);
            }
            for (WorkShop workShop : farm.getWorkShops()) {
                workShop.nextTurn();
                if (workShop.readyForDelivery()) {
                    Cell[][] cells = farm.getCells();
                    int throwX = workShop.getThrowedProductX();
                    int throwY = workShop.getThrowedProductY();
                    ArrayList<Product> products = cells[throwX][throwY].getProducts();
                    for (int j = 0 ; j < workShop.getNumberOfProcessedProduct() ; j++)
                        products.add(workShop.getProduct());
                    cells[throwX][throwY].setProducts(products);
                    farm.setCells(cells);
                }
            }
            farm.getWell().rander();
            farm.updateCells();
            farm.getHelicopter().nextTrun();
            if (farm.getHelicopter().isReadyToDeliver()) {
                for (Product product : farm.getHelicopter().getProducts()) {
                    int randomX = (int)(Math.random() * 30);
                    int randomY = (int)(Math.random() * 30);
                    farm.getCells()[randomX][randomY].addProduct(product);
                }
                farm.getHelicopter().setReadyToDeliver(false);
            }
            farm.getTruck().nextTurn();
            if (farm.getTruck().isReadyToPay()) {
                farm.setMoney(farm.getMoney() + farm.getTruck().calculatePaidMoney());
                farm.getTruck().setReadyToPay(false);
            }
        }
    }


    public void upgradeAction(UpgradeRequest request) {
        if (request.getPartTOUpgradeName().equals("cat")) {
            for (Animal animal : farm.getAnimals())
                if (animal instanceof Cat) {
                    if (animal.getLevel == 2)
                        view.logLevelIsHighest();
                    else if (farm.getMoney() < ((Cat) animal).getUpgardeCost())
                        view.logNotEnoughMoney();
                    else {
                        farm.setMoney(farm.getMoney() - ((Cat) animal).getUpgradeost());
                        animal.upgrade();
                    }
                }
        } else if (request.getPartTOUpgradeName().equals("well")) {
            if (farm.getWell().getLevel() == 4)
                view.logLevelIsHighest();
            else if (farm.getWell().isWorking())
                view.logWellIsWorking();
            else if (farm.getMoney() < farm.getWell().getUpgardeCost())
                view.logNotEnoughMoney();
            else {
                farm.setMoney(farm.getMoney() - farm.getWell().getUpgardeCost());
                farm.setWell(farm.getWell().upgarde());
            }
        } else if (request.getPartTOUpgradeName().equals("truck")) {
            if (farm.getTruck().getLevel() == 4)
                view.logLevelIsHighest();
            else if (!farm.getTruck().isAvailable())
                view.logTruckIsNotAvailable();
            else if (farm.getMoney() < farm.getTruck().getUpgradeCost())
                view.logNotEnoughMoney();
            else {
                farm.setMoney(farm.getMoney() - farm.getTruck().getUpgradeCost());
                farm.setTruck(farm.getTruck().upgarde());
            }
        } else if (request.getPartTOUpgradeName().equals("helicopter")) {
            if (farm.getHelicopter().getLevel() == 4)
                view.logLevelIsHighest();
            else if (!farm.getHelicopter().isAvailable())
                view.logHelicopterIsNotAvailable();
            else if (farm.getMoney() < farm.getHelicopter().getUpgradeCost())
                view.logNotEnoughMoney();
            else {
                farm.setMoney(farm.getMoney() - farm.getHelicopter().getUpgradeCost());
                farm.setHelicopter(farm.getHelicopter().upgrade());
            }
        } else if (request.getPartTOUpgradeName().equals("warehouse")) {
            if (farm.getStorage().getLevel() == 4)
                view.logLevelIsHighest();
            else if (farm.getMoney() < farm.getStorage().getUpgradeCost())
                view.logNotEnoughMoney();
            else {
                farm.setMoney(farm.getMoney() - farm.getStorage().getUpgradeCost());
                farm.setStorage(farm.getStorage().upgrade());
            }
        } else {
            WorkShop selectedWorkShop = null;
            for (WorkShop workShop : farm.getWorkShops()) {
                if (request.getPartTOUpgradeName().equals("sewingfactory") && workShop instanceof SewingFactory)
                    selectedWorkShop = workShop;
                else if (request.getPartTOUpgradeName().equals("spinnery") && workShop instanceof Spinnery)
                    selectedWorkShop = workShop;
                else if (request.getPartTOUpgradeName().equals("eggpowderplant") && workShop instanceof EggPowderPlant)
                    selectedWorkShop = workShop;
                else if (request.getPartTOUpgradeName().equals("weavingfactory") && workShop instanceof WeavingFactory)
                    selectedWorkShop = workShop;
                else if (request.getPartTOUpgradeName().equals("cookiebakery") && workShop instanceof CookieBakery)
                    selectedWorkShop = workShop;
                else if (request.getPartTOUpgradeName().equals("cakebakery") && workShop instanceof CakeBakery)
                    selectedWorkShop = workShop;
                else {
                    view.logWrongCommand();
                    return;
                }
            }
            if (selectedWorkShop.getLevel() == 4)
                view.logLevelIsHighest();
            else if (selectedWorkShop.isWorking())
                view.logWorkShopIsWorking();
            else if (farm.getMoney() < selectedWorkShop.getUpgradeCost())
                view.logNotEnoughMoney();
            else {
                farm.setMoney(farm.getMoney() - selectedWorkShop.getUpgradeCost());
                selectedWorkShop.upgrade();
            }
        }
    }

    public void wellAction(WellRequest request) {
        if (farm.getMoney() < farm.getWell().getFillPrice()) {
            view.logNotEnoughMoney();
            return;
        }
        if (!farm.getWell().isWorking()) {
            farm.setMoney(farm.getMoney() - farm.getWell().getFillPrice());
            farm.getWell().fill();
        } else {
            view.logWellIsWorking();
        }
    }


    public Integer[] getAnimalDestination(Animal animal, Cell[][] cells) {
        Integer[] destination = {-1, -1};
        if (animal instanceof Cat) {
            destination = getCatDestination(animal.getX(), animal.getY(), cells);
        } else if (animal instanceof FarmAnimal) {
            destination = getFarmAnimalDestination(animal.getX(), animal.getY(), cells);
        } else if (animal instanceof Dog) {
            destination = getDogDestination(animal.getX(), animal.getY(), cells);
        }
        return destination;
    }

    public Integer[] getCatDestination(int catX, int catY, Cell[][] cells) {
        Integer[] destination = {-1, -1};
        double distance = 10000;
        for (int i  = 0 ; i < 30 ; i++) {
            for (int j = 0; j < 30; j++) {
                for (Product product : cells[i][j].getProducts())
                    if (product != null)
                        if (distance > Math.sqrt(Math.pow(catX - i, 2) + Math.pow(catY - j, 2))) {
                            distance = Math.sqrt(Math.pow(catX - i, 2) + Math.pow(catY - j, 2));
                            destination[0] = i;
                            destination[1] = j;
                        }
            }
        }
        return destination;
    }


    public Integer[] getFarmAnimalDestination(int farmAnimalX, int farmAnimalY, Cell[][] cells) {
        Integer[] destination = {-1, -1};
        double distance = 10000;
        for (int i  = 0 ; i < 30 ; i++) {
            for (int j = 0; j < 30; j++) {
                if (cells[i][j].isHasPlant())
                    if (distance > Math.sqrt(Math.pow(farmAnimalX - i, 2) + Math.pow(farmAnimalY - j, 2))) {
                        distance = Math.sqrt(Math.pow(farmAnimalX - i, 2) + Math.pow(farmAnimalY - j, 2));
                        destination[0] = i;
                        destination[1] = j;
                    }
            }
        }
        return destination;
    }

    public Integer[] getDogDestination(int dogX, int dogY, Cell[][] cells) {
        Integer[] destination = {-1, -1};
        double distance = 10000;
        for (int i  = 0 ; i < 30 ; i++) {
            for (int j = 0; j < 30; j++) {
                for (Animal animal : cells[i][j].getAnimals())
                    if (animal instanceof WildAnimal)
                        if (distance > Math.sqrt(Math.pow(dogX - i, 2) + Math.pow(dogY - j, 2))) {
                            distance = Math.sqrt(Math.pow(dogX - i, 2) + Math.pow(dogY - j, 2));
                            destination[0] = i;
                            destination[1] = j;
                        }
            }
        }
        return destination;
    }
}
