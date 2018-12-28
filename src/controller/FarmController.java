package controller;
import com.google.gson.Gson;
import model.*;
import model.request.*;
import view.View;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    public void loadCustomAction() {

    }

    public void loadGameAction() {

    }

    public void pickUpAction() {

    }

    public void plantAction() {

    }

    public void printAction() {

    }

    public void runAction() {

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
