package controller;
import model.*;
import model.request.*;
import view.View;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void loadGameAction(LoadGameRequest request) {

    }

    public void pickUpAction(PickUpRequest request) {

    }

    public void plantAction(PlantRequest  request) {

    }

    public void printAction(PrintRequest request) {

    }

    public void runAction(RunRequest request) {

    }

    public void saveGameAction(SaveGameRequest request) {

    }

    public void startAction(StartRequest request) {

    }

    public void turnAction(TurnRequest request) {

    }

    public void upgradeAction(UpgradeRequest request) {

    }

    public void wellAction(WellRequest request) {

    }
}
