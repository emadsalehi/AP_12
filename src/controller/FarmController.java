package controller;

import com.gilecode.yagson.YaGson;
import com.google.gson.Gson;
import model.*;
import model.request.*;
import view.View;

import java.io.*;
import java.util.*;

public class FarmController {
    ArrayList<WorkShop> workShops = new ArrayList<>(); {
        workShops.add(new CookieBakery());
        workShops.add(new SewingFactory());
        workShops.add(new EggPowderPlant());
        workShops.add(new CakeBakery());
        workShops.add(new Spinnery());
        workShops.add(new WeavingFactory());
    }
    private Farm farm = new Farm(workShops);
    private View view = new View();
    private CommandAnalyzer commandAnalyzer = new CommandAnalyzer();

    public void listenForCommand() {
        boolean requestsOnTheWay = true;
        while (requestsOnTheWay) {
            String command = view.getInput().trim();
            Request request = commandAnalyzer.getRequest(command);
            if (request instanceof AddRequest) {
                addAction((AddRequest) request);
            } else if (request instanceof BadRequest) {
                badAction((BadRequest) request);
            } else if (request instanceof BuyRequest) {
                buyAction((BuyRequest) request);
            } else if (request instanceof CageRequest) {
                cageAction((CageRequest) request);
            } else if (request instanceof ClearRequest) {
                clearAction((ClearRequest) request);
            } else if (request instanceof GoRequest) {
                goAction((GoRequest) request);
            } else if (request instanceof LoadCustomRequest) {
                loadCustomAction((LoadCustomRequest) request);
            } else if (request instanceof LoadGameRequest) {
                loadGameAction((LoadGameRequest) request);
            } else if (request instanceof PickUpRequest) {
                pickUpAction((PickUpRequest) request);
            } else if (request instanceof PlantRequest) {
                plantAction((PlantRequest) request);
            } else if (request instanceof PrintRequest) {
                printAction((PrintRequest) request);
            } else if (request instanceof RunRequest) {
                runAction((RunRequest) request);
            } else if (request instanceof SaveGameRequest) {
                saveGameAction((SaveGameRequest) request);
            } else if (request instanceof StartRequest) {
                startAction((StartRequest) request);
            } else if (request instanceof TurnRequest) {
                turnAction((TurnRequest) request);
            } else if (request instanceof UpgradeRequest) {
                upgradeAction((UpgradeRequest) request);
            } else if (request instanceof WellRequest) {
                wellAction((WellRequest) request);
            } else if (request instanceof ExitRequest) {
                requestsOnTheWay = false;
            }
        }

    }

    public void addAction(AddRequest request) {
        Truck truck = farm.getTruck();
        Helicopter helicopter = farm.getHelicopter();
        if (request.getVehicleTypeName().equals("truck")) {
            for (int q = 0 ; q < request.getCount() ; q++){
                for (WildAnimalType wildAnimalType : WildAnimalType.values()) {
                    if (wildAnimalType.name().equals(request.getItemName())) {
                        if (truck.getCapacity() >= truck.calculateUsedCapacity() + wildAnimalType.getDepotSize()) {
                            ArrayList<Animal> animals = truck.getAnimals();
                            animals.add(new WildAnimal(0, 0, wildAnimalType));
                            truck.setAnimals(animals);
                            farm.setTruck(truck);
                        } //TODO else ---> print not enough capacity
                    }
                }
                for (FarmAnimalType farmAnimalType : FarmAnimalType.values()) {
                    //System.out.println(farmAnimalType.name() + " ");
                    if (farmAnimalType.name().equals(request.getItemName().toUpperCase())) {
                        //System.out.println("1");
                        if (truck.getCapacity() >= truck.calculateUsedCapacity() + farmAnimalType.getDepotSize()) {
                            for (int i = 0 ; i < 30 ; i++){
                                for (int j = 0 ; j < 30 ; j++){
                                    Iterator<Animal> iterator = (farm.getCells())[i][j].getAnimals().iterator();
                                    while(iterator.hasNext()){
                                        Animal animal =iterator.next();
                                        if (animal instanceof FarmAnimal){
                                            FarmAnimal farmAnimal = (FarmAnimal)animal;
                                            if (farmAnimal.getFarmAnimalType().name().equals(farmAnimalType.name())){
                                                //System.out.println("1");
                                                ArrayList<Animal> animals = truck.getAnimals();
                                                animals.add(new FarmAnimal(0, 0, farmAnimalType));
                                                truck.setAnimals(animals);
                                                farm.setTruck(truck);
                                                iterator.remove();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for (PrimitiveProductType primitiveProductType : PrimitiveProductType.values()) {
                    if (primitiveProductType.name().equals(request.getItemName())) {
                        if (truck.getCapacity() >= truck.calculateUsedCapacity() + primitiveProductType.getDepotSize()) {
                            ArrayList<Product> products = truck.getProducts();
                            products.add(new PrimitiveProduct(primitiveProductType));
                            truck.setProducts(products);
                            farm.setTruck(truck);
                        }
                    }
                }
                for (SecondaryProductType secondaryProductType : SecondaryProductType.values()) {
                    if (secondaryProductType.name().equals(request.getItemName())) {
                        if (truck.getCapacity() >= truck.calculateUsedCapacity() + secondaryProductType.getDepotSize()) {
                            ArrayList<Product> products = truck.getProducts();
                            products.add(new SecondaryProduct(secondaryProductType));
                            truck.setProducts(products);
                            farm.setTruck(truck);
                        }
                    }
                }
            }

        } else if (request.getVehicleTypeName().equals("helicopter")) {
            for (int q = 0 ; q < request.getCount() ; q++){
                for (PrimitiveProductType primitiveProductType : PrimitiveProductType.values()) {
                    if (primitiveProductType.name().equals(request.getItemName().toUpperCase())) {
                        if (helicopter.getCapacity() > helicopter.calculateUsedCapacity() + primitiveProductType.getDepotSize()
                                && farm.getMoney() >= helicopter.calculateRequiredMoney() + primitiveProductType.getBuyCost()) {
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
    }

    public void badAction(BadRequest request) {
        view.logWrongCommand();
    }

    public boolean buyAction(BuyRequest request) {
        int buyCost;
        if (request.getAnimal() instanceof FarmAnimal) {
            buyCost = ((FarmAnimal) request.getAnimal()).getFarmAnimalType().getBuyCost();
        } else {
            buyCost = 2500;
        }
        if (farm.getMoney() < buyCost) {
            view.logNotEnoughMoney();
            return false;
        } else {
            farm.addAnimal(request.getAnimal());
            farm.setMoney(farm.getMoney() - buyCost);
            return true;
        }
    }

    public boolean cageAction(CageRequest request) {
        boolean wildFoundFlag = false;
        Storage storage = farm.getStorage();
        Cell[][] cells = farm.getCells();
        ArrayList<Animal> cellAnimals = cells[request.getX()][request.getY()].getAnimals();
        Iterator<Animal> animalIterator = cellAnimals.iterator();
        while (animalIterator.hasNext()) {
            Animal animal = animalIterator.next();
            if (animal instanceof WildAnimal) {
                wildFoundFlag = true;
                if (((WildAnimal) animal).getWildAnimalType().getDepotSize() + storage.calculateUsedCapacity() <= storage.getCapacity()) {
                    ArrayList<Animal> animals = storage.getAnimals();
                    animals.add(animal);
                    storage.setAnimals(animals);
                    animalIterator.remove();
                } else {
                    animal.setMoving(false);
                    //TODO Cage for some turns
                }
            }
        }
        farm.setStorage(storage);
        cells[request.getX()][request.getY()].setAnimals(cellAnimals);
        farm.setCells(cells);
        return wildFoundFlag;
    }

    public void clearAction(ClearRequest request) {
        if (request.getVehicleTypeName().equals("truck")) {
            Truck truck = farm.getTruck();
            Storage storage = farm.getStorage();
            ArrayList<Animal> truckAnimals = truck.getAnimals();
            ArrayList<Product> truckProducts = truck.getProducts();
            ArrayList<Animal> storageAnimals = storage.getAnimals();
            ArrayList<Product> storageProducts = storage.getProducts();
            for (Animal animal : truck.getAnimals()) {
                storageAnimals.add(animal);
            }
            for (Product product : truck.getProducts()) {
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
        } else {
            Helicopter helicopter = farm.getHelicopter();
            ArrayList<Product> helicopterProducts = helicopter.getProducts();
            farm.setMoney(farm.getMoney() + helicopter.calculateRequiredMoney());
            helicopterProducts.clear();
            helicopter.setProducts(helicopterProducts);
            farm.setHelicopter(helicopter);
        }
    }

    public void goAction(GoRequest request) {
        if (request.getVehiclePartName().equals("truck")) {
            Truck truck = farm.getTruck();
            truck.setAvailable(false);
            farm.setTruck(truck);
        } else {
            Helicopter helicopter = farm.getHelicopter();
            helicopter.setAvailable(false);
            farm.setHelicopter(helicopter);
        }
    }


    public void loadCustomAction(LoadCustomRequest request) {
        String path = request.getPathToCustomDirectory();
        Gson gson = new Gson();
        ArrayList<WorkShop> workShops = farm.getWorkShops();
        CustomWorkShop customWorkShop = null;
        try {
            customWorkShop = gson.fromJson(new FileReader(path), CustomWorkShop.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        workShops.add(customWorkShop);
        farm.setWorkShops(workShops);
    }


    public void loadGameAction(LoadGameRequest request) {
        String path = request.getPathToJsonFile();
        Gson gson = new Gson();
        YaGson yaGson = new YaGson();
        try {
            //farm = gson.fromJson(new FileReader(path), Farm.class);
            InputStream inputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(inputStream);
            String farmJson = scanner.nextLine();
            farm = yaGson.fromJson(farmJson , Farm.class);
        } catch (FileNotFoundException e) {
            view.logFileNotFound();
        }
    }
    public boolean pickUpAction(PickUpRequest request) {
        int x = request.getX();
        int y = request.getY();
        if (x < 0 || x > 29 || y < 0 || y > 29) {
            view.logNotPossible();
            return false;
        } else {
            return farm.userPickUp(x, y);
        }
    }

    public boolean plantAction(PlantRequest request) {
        int x = request.getX();
        int y = request.getY();
        int[] xDisplace = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        int[] yDisplace = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        if (farm.getWell().getWaterLeft() == 0 || farm.getWell().isWorking())
            return false;
        farm.getWell().setWaterLeft(farm.getWell().getWaterLeft() - 1);
        for (int i = 0; i < 9; i++) {
            if (x + xDisplace[i] < 0 || x + xDisplace[i] > 29 || y + yDisplace[i] < 0 || y + yDisplace[i] > 29) {
                continue;
            } else {
                farm.irrigate(x + xDisplace[i], y + yDisplace[i]);
            }
        }
        return true;
    }

    public void printAction(PrintRequest request) {
        String printFrom = request.getPartToPrintName();
        if (printFrom.equals("info")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Money: " + farm.getMoney() + " Time: " + farm.getMoney());
            stringBuilder.append("Level: ");
            if (farm.getLevel().getRequiredMoney() != 0) {
                stringBuilder.append("Money: " + farm.getMoney() + "/" + farm.getLevel().getRequiredMoney() + ' ');
            }
            HashMap<Animal, Integer> requiredAnimals = farm.getLevel().getRequiredAnimals();
            HashMap<Product, Integer> requiredProduct = farm.getLevel().getRequiredProduct();

            ArrayList<Animal> storageAnimals = farm.getStorage().getAnimals();
            ArrayList<Product> storageProducts = farm.getStorage().getProducts();

            for (HashMap.Entry<Animal, Integer> entry : requiredAnimals.entrySet()) {
                Integer numberOfAnimals = 0;
                for (Animal animal : storageAnimals) {
                    if (animal.equals(entry.getKey())) {
                        numberOfAnimals++;
                    }
                }
                stringBuilder.append(entry.getKey().getClass().getSimpleName() + ": " + numberOfAnimals.toString() + "/" +
                        entry.getValue() + " ");

            }
            for (HashMap.Entry<Product, Integer> entry : requiredProduct.entrySet()) {
                Integer numberOfProducts = 0;
                for (Product product : storageProducts) {
                    if (product.equals(entry.getKey())) {
                        numberOfProducts++;
                    }
                }
                stringBuilder.append(entry.getKey().getClass().getSimpleName() + ": " + numberOfProducts.toString() + "/" +
                        entry.getValue() + " ");
            }
            view.logInfo(stringBuilder.toString());
        } else if (printFrom.equals("map")) {
            StringBuilder stringBuilder = new StringBuilder();
            Cell[][] cells = farm.getCells();
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    Cell cell = cells[i][j];
                    if (cell.getAnimals().size() != 0) {
                        stringBuilder.append(cell.getAnimals().get(0).getClass().getSimpleName().charAt(0));
                    } else if (cell.getProducts().size() != 0) {
                        stringBuilder.append(cell.getProducts().get(0).getClass().getSimpleName().charAt(0));
                    } else if (cell.isHasPlant()) {
                        stringBuilder.append("O");
                    } else {
                        stringBuilder.append(".");
                    }
                }
                stringBuilder.append("\n");
            }
            view.logMap(stringBuilder.toString());
        } else if (printFrom.equals("levels")) {
            Level level = farm.getLevel();
            StringBuilder stringBuilder = new StringBuilder();
            if (level.getRequiredMoney() != 0) {
                stringBuilder.append("Money: " + level.getRequiredMoney() + " ");
            }
            HashMap<Animal, Integer> requiredAnimals = level.getRequiredAnimals();
            HashMap<Product, Integer> requiredProduct = level.getRequiredProduct();
            for (HashMap.Entry<Animal, Integer> entry : requiredAnimals.entrySet()) {
                stringBuilder.append(entry.getKey().getClass().getSimpleName() + ": " + entry.getValue() + " ");
            }
            for (HashMap.Entry<Product, Integer> entry : requiredProduct.entrySet()) {
                stringBuilder.append(entry.getKey().getClass().getSimpleName() + ": " + entry.getValue() + " ");
            }
            view.logLevel(stringBuilder.toString());
        } else if (printFrom.equals("warehouse")) {
            ArrayList<Product> products = farm.getStorage().getProducts();
            ArrayList<Animal> animals = farm.getStorage().getAnimals();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Warehouse: Products: ");
            for (Product product : products) {
                if (product instanceof PrimitiveProduct) {
                    stringBuilder.append(((PrimitiveProduct) product).getPrimitiveProductType().toString());
                } else {
                    stringBuilder.append(((SecondaryProduct) product).getSecondaryProductType().toString());
                }
            }
            stringBuilder.append("\nAnimals: ");
            for (Animal animal : animals) {
                if (animal instanceof FarmAnimal) {
                    stringBuilder.append(((FarmAnimal) animal).getFarmAnimalType().toString());
                } else if (animal instanceof WildAnimal) {
                    stringBuilder.append(((WildAnimal) animal).getWildAnimalType().toString());
                }
            }
            view.logWarehouse(stringBuilder.toString());
        } else if (printFrom.equals("well")) {
            Well well = farm.getWell();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(well.getWaterLeft() + "/" + well.getCapacity());
            view.logWell(stringBuilder.toString());
        } else if (printFrom.equals("workshops")) {
            ArrayList<WorkShop> workShops = farm.getWorkShops();
            StringBuilder stringBuilder = new StringBuilder();
            for (WorkShop workShop : workShops) {
                if (workShop instanceof CustomWorkShop) {
                    stringBuilder.append("Custom: Products: " + ((CustomWorkShop) workShop).getProcessedProduct()
                            .getSecondaryProductType().toString());
                    if (((CustomWorkShop) workShop).getRawProduct() instanceof PrimitiveProduct) {
                        stringBuilder.append(" Raw: " + ((PrimitiveProduct) ((CustomWorkShop) workShop).getRawProduct())
                                .getPrimitiveProductType().toString());
                    } else {
                        stringBuilder.append(" Raw: " + ((SecondaryProduct) ((CustomWorkShop) workShop).getRawProduct())
                                .getSecondaryProductType().toString());
                    }
                } else {
                    stringBuilder.append(workShop.getClass().getSimpleName() + " ");
                }
            }
            view.logWorkshop(stringBuilder.toString());
        } else if (printFrom.equals("truck")) {
            Truck truck = farm.getTruck();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Level: " + truck.getLevel() + "Capacity: " + truck.getCapacity() + "isAvailable: "
                    + truck.isAvailable() + "time until arrived: " + truck.getTravelCounter());
            for (Product product : truck.getProducts()) {
                if (product instanceof PrimitiveProduct) {
                    stringBuilder.append(((PrimitiveProduct) product).getPrimitiveProductType().toString());
                } else {
                    stringBuilder.append(((SecondaryProduct) product).getSecondaryProductType().toString());
                }
            }
            stringBuilder.append("\nAnimals: ");
            for (Animal animal : truck.getAnimals()) {
                if (animal instanceof FarmAnimal) {
                    stringBuilder.append(((FarmAnimal) animal).getFarmAnimalType().toString());
                } else if (animal instanceof WildAnimal) {
                    stringBuilder.append(((WildAnimal) animal).getWildAnimalType().toString());
                }
            }
            view.logTruck(stringBuilder.toString());
        } else if (printFrom.equals("helicopter")) {
            Helicopter helicopter = farm.getHelicopter();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\nLevel: " + helicopter.getLevel() + "Capacity: " + helicopter.getCapacity() + "isAvailable: "
                    + helicopter.isAvailable() + "time until arrived: " + helicopter.getTravelCounter());
            for (Product product : helicopter.getProducts()) {
                if (product instanceof PrimitiveProduct) {
                    stringBuilder.append(((PrimitiveProduct) product).getPrimitiveProductType().toString());
                } else {
                    stringBuilder.append(((SecondaryProduct) product).getSecondaryProductType().toString());
                }
            }
            view.logHelicopter(stringBuilder.toString());
        }

    }

    public void runAction(RunRequest request) {
        String path = request.getMapName();
        Gson gson = new Gson();
        YaGson yaGson = new YaGson();
        try {
            //farm = gson.fromJson(new FileReader(path), Farm.class);
            farm = yaGson.fromJson(new FileReader(path), Farm.class);
        } catch (FileNotFoundException e) {
            view.logFileNotFound();
        }
    }

    public void saveGameAction(SaveGameRequest request) {
        Gson gson = new Gson();
        YaGson yaGson = new YaGson();
        String farmJson = yaGson.toJson(farm , Farm.class);

        try {
            //gson.toJson(farm, new FileWriter(request.getPathToJsonFile()));
            //yaGson.toJson(farm, new FileWriter(request.getPathToJsonFile()));
            OutputStream outputStream = new FileOutputStream(request.getPathToJsonFile());
            Formatter formatter = new Formatter(outputStream);
            formatter.format(farmJson);
            formatter.flush();

        } catch (IOException e) {
            view.logFileNotFound();
        }
    }

    public boolean startAction(StartRequest request) {
        //todo set in farm workshop not handled.
        WorkShop selectedWorkShop = null;
        boolean isSelectedWorkShopFound = false;
        for (WorkShop workShop : farm.getWorkShops()) {
            if (request.getWorkShopName().equals("sewingfactory") && workShop instanceof SewingFactory) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            } else if (request.getWorkShopName().equals("spinnery") && workShop instanceof Spinnery) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            }else if (request.getWorkShopName().equals("eggpowderplant") && workShop instanceof EggPowderPlant) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            } else if (request.getWorkShopName().equals("weavingfactory") && workShop instanceof WeavingFactory) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            }else if (request.getWorkShopName().equals("cookiebakery") && workShop instanceof CookieBakery) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            }else if (request.getWorkShopName().equals("cakebakery") && workShop instanceof CakeBakery) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            }else if (request.getWorkShopName().equals("customworkshop") && workShop instanceof CustomWorkShop) {
                isSelectedWorkShopFound = true;
                selectedWorkShop = workShop;
            }
        }
        if (!isSelectedWorkShopFound) {
            view.logWrongCommand();
            return false;
        }
        if (selectedWorkShop.isWorking()) {
            view.logWorkShopIsWorking();
            return false;
        }
        Storage storage = farm.getStorage();
        if (selectedWorkShop instanceof SewingFactory) {
            Product product1 = null;
            Product product2 = null;
            for (Product product : storage.getProducts()) {
                if (product instanceof SecondaryProduct && ((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.CLOTHES)
                    product1 = product;
                else if (product instanceof PrimitiveProduct && ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.PLUME)
                    product2 = product;
            }
            if (product1 == null || product2 == null) {
                view.logRequirementsIsNotEnough();
                return false;
            }
            storage.getProducts().remove(product1);
            storage.getProducts().remove(product2);
        } else if (selectedWorkShop instanceof Spinnery) {
            Product product1 = null;
            for (Product product : storage.getProducts())
                if (product instanceof PrimitiveProduct &&
                        ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.WOOL)
                    product1 = product;
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return false;
            }
            storage.getProducts().remove(product1);
        } else if (selectedWorkShop instanceof EggPowderPlant) {
            Product product1 = null;
            for (Product product : storage.getProducts())
                if (product instanceof PrimitiveProduct &&
                        ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.EGG)
                    product1 = product;
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return false;
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
                return false;
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
                return false;
            }
            storage.getProducts().remove(product1);
        } else if (selectedWorkShop instanceof CakeBakery) {
            Product product1 = null;
            Product product2 = null;
            for (Product product : storage.getProducts()) {
                if (product instanceof PrimitiveProduct &&
                        ((PrimitiveProduct) product).getPrimitiveProductType() == PrimitiveProductType.FLOUR)
                    product1 = product;
                else if (product instanceof SecondaryProduct &&
                        ((SecondaryProduct) product).getSecondaryProductType() == SecondaryProductType.COOKIE)
                    product2 = product;
            }
            if (product1 == null || product2 == null) {
                view.logRequirementsIsNotEnough();
                return false;
            }
            storage.getProducts().remove(product1);
            storage.getProducts().remove(product2);
        } else if (selectedWorkShop instanceof CustomWorkShop) {
            Product product1 = null;
            if (((CustomWorkShop) selectedWorkShop).getRawProduct() instanceof PrimitiveProduct) {
                PrimitiveProductType type = ((PrimitiveProduct) ((CustomWorkShop) selectedWorkShop).getRawProduct()).getPrimitiveProductType();
                for (Product product : storage.getProducts())
                    if (product instanceof PrimitiveProduct && ((PrimitiveProduct) product).getPrimitiveProductType() == type)
                        product1 = product;
            } else if (((CustomWorkShop) selectedWorkShop).getRawProduct() instanceof SecondaryProduct) {
                SecondaryProductType type = ((SecondaryProduct) ((CustomWorkShop) selectedWorkShop).getRawProduct()).getSecondaryProductType();
                for (Product product : storage.getProducts())
                    if (product instanceof SecondaryProduct && ((SecondaryProduct) product).getSecondaryProductType() == type)
                        product1 = product;
            }
            if (product1 == null) {
                view.logRequirementsIsNotEnough();
                return false;
            }
            storage.getProducts().remove(product1);
        }
        farm.setStorage(storage);
        selectedWorkShop.startWorkShop();
        return true;
    }

    public void turnAction(TurnRequest request) {
        for (int i = 0; i < request.getNumberOfTurns(); i++) {
            Cell[][] cells = farm.getCells();
            for (int j = 0; j < 30; j++) {
                for (int k = 0; k < 30; k++) {
                    for (Animal animal : cells[j][k].getAnimals()) {
                        Integer[] destination = getAnimalDestination(animal, cells);
                        animal.move(destination[0], destination[1]);
                    }
                }
            }
            for (WorkShop workShop : farm.getWorkShops()) {
                workShop.nextTurn();
                if (workShop.readyForDelivery()) {
                    int throwX = workShop.getThrowedProductX();
                    int throwY = workShop.getThrowedProductY();
                    ArrayList<Product> products = cells[throwX][throwY].getProducts();
                    for (int j = 0; j < workShop.getNumberOfProcessedProduct(); j++)
                        products.add(workShop.getProduct());
                    cells[throwX][throwY].setProducts(products);
                    farm.setCells(cells);
                }
            }
            farm.getWell().render();
            farm.updateCells();
            farm.getHelicopter().nextTurn();
            if (farm.getHelicopter().isReadyToDeliver()) {
                //System.out.println("Ready to deliver in farm");
                for (Product product : farm.getHelicopter().getProducts()) {
                    int randomX = (int) (Math.random() * 30);
                    int randomY = (int) (Math.random() * 30);
                    farm.getCells()[randomX][randomY].addProduct(product);
                }
                farm.getHelicopter().setReadyToDeliver(false);
                farm.getHelicopter().clearProducts();
            }
            farm.getTruck().nextTurn();
            if (farm.getTruck().isReadyToPay()) {
                //System.out.println("Ready to pay in farm");
                farm.setMoney(farm.getMoney() + farm.getTruck().calculatePaidMoney());
                farm.getTruck().setReadyToPay(false);
                farm.getTruck().clearAnimals();
                farm.getTruck().clearProducts();
            }
            farm.setTime(farm.getTime() + 1);
//            if (farm.levelPassedChecker()) {
//                view.logLevelPassed();
//                return;
//            }
        }
    }


    public boolean upgradeAction(UpgradeRequest request) {
        if (request.getPartTOUpgradeName().equals("cat")) {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    for (Animal animal : farm.getCells()[i][j].getAnimals())
                        if (animal instanceof Cat) {
                            if (((Cat) animal).getLevel() == 2)
                                view.logLevelIsHighest();
                            else if (farm.getMoney() < ((Cat) animal).calculateUpgardePrice())
                                view.logNotEnoughMoney();
                            else {
                                farm.setMoney(farm.getMoney() - ((Cat) animal).calculateUpgardePrice());
                                ((Cat) animal).upgrade();
                            }
                        }
                }
            }
        } else if (request.getPartTOUpgradeName().equals("well")) {
            if (farm.getWell().getLevel() == 4) {
                view.logLevelIsHighest();
                return false;
            }
            else if (farm.getWell().isWorking()) {
                view.logWellIsWorking();
                return false;
            }
            else if (farm.getMoney() < farm.getWell().getUpgradePrice()) {
                view.logNotEnoughMoney();
                return false;
            }
            else {
                farm.setMoney(farm.getMoney() - farm.getWell().getUpgradePrice());
                farm.getWell().upgradeLevel();
                return true;
            }
        } else if (request.getPartTOUpgradeName().equals("truck")) {
            if (farm.getTruck().getLevel() == 4) {
                view.logLevelIsHighest();
                return false;
            }
            else if (!farm.getTruck().isAvailable()) {
                view.logTruckIsNotAvailable();
                return false;
            }
            else if (farm.getMoney() < farm.getTruck().getUpgradeCost()) {
                view.logNotEnoughMoney();
                return false;
            }
            else {
                farm.setMoney(farm.getMoney() - farm.getTruck().getUpgradeCost());
                farm.getTruck().upgrade();
                return true;
            }
        } else if (request.getPartTOUpgradeName().equals("helicopter")) {
            if (farm.getHelicopter().getLevel() == 4) {
                view.logLevelIsHighest();
                return false;
            }
            else if (!farm.getHelicopter().isAvailable()) {
                view.logHelicopterIsNotAvailable();
                return false;
            }
            else if (farm.getMoney() < farm.getHelicopter().getUpgradeCost()) {
                view.logNotEnoughMoney();
                return false;
            }
            else {
                farm.setMoney(farm.getMoney() - farm.getHelicopter().getUpgradeCost());
                farm.getHelicopter().upgrade();
                return true;
            }
        } else if (request.getPartTOUpgradeName().equals("warehouse")) {
            if (farm.getStorage().getLevel() == 4) {
                view.logLevelIsHighest();
                return false;
            }
            else if (farm.getMoney() < farm.getStorage().getUpgradePrice()) {
                view.logNotEnoughMoney();
                return false;
            }
            else {
                farm.setMoney(farm.getMoney() - farm.getStorage().getUpgradePrice());
                farm.getStorage().upgradeStorage();
                return true;
            }
        } else {
            WorkShop selectedWorkShop = null;
            boolean isWorshopSelected = false;
            for (WorkShop workShop : farm.getWorkShops()) {

                if (request.getPartTOUpgradeName().equals("sewingfactory") && workShop instanceof SewingFactory) {
                    selectedWorkShop = workShop;
                    isWorshopSelected = true;
                }else if (request.getPartTOUpgradeName().equals("spinnery") && workShop instanceof Spinnery) {
                    selectedWorkShop = workShop;
                    isWorshopSelected = true;
                }else if (request.getPartTOUpgradeName().equals("eggpowderplant") && workShop instanceof EggPowderPlant) {
                    selectedWorkShop = workShop;
                    isWorshopSelected = true;
                }else if (request.getPartTOUpgradeName().equals("weavingfactory") && workShop instanceof WeavingFactory) {
                    selectedWorkShop = workShop;
                    isWorshopSelected = true;
                }else if (request.getPartTOUpgradeName().equals("cookiebakery") && workShop instanceof CookieBakery) {
                    selectedWorkShop = workShop;
                    isWorshopSelected = true;
                }else if (request.getPartTOUpgradeName().equals("cakebakery") && workShop instanceof CakeBakery) {
                    selectedWorkShop = workShop;
                    isWorshopSelected = true;
                }
            }
            if (!isWorshopSelected) {
                view.logWrongCommand();
                return false;
            }
            if (selectedWorkShop.getLevel() == 4) {
                view.logLevelIsHighest();
                return false;
            }
            else if (selectedWorkShop.isWorking()) {
                view.logWorkShopIsWorking();
                return false;
            }
            else if (farm.getMoney() < selectedWorkShop.getUpgradeCost()) {
                view.logNotEnoughMoney();
                return false;
            }
            else {
                farm.setMoney(farm.getMoney() - selectedWorkShop.getUpgradeCost());
                selectedWorkShop.upgrade();
                return true;
            }
        }
        return true;
    }

    public boolean wellAction(WellRequest request) {
        if (farm.getMoney() < farm.getWell().getFillPrice()) {
            view.logNotEnoughMoney();
            return false;
        }
        if (farm.getWell().getCapacity() == farm.getWell().getWaterLeft()) {
            return false;
        }
        if (!farm.getWell().isWorking()) {
            farm.setMoney(farm.getMoney() - farm.getWell().getFillPrice());
            farm.getWell().fill();
        } else {
            view.logWellIsWorking();
            return false;
        }
        return true;
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
        for (int i = 0; i < 30; i++) {
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
        if (cells[farmAnimalX][farmAnimalY].isHasPlant()) {
            destination[0] = farmAnimalX;
            destination[1] = farmAnimalY;
            return destination;
        }
        for (int i = 0; i < 30; i++) {
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
        for (int i = 0; i < 30; i++) {
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

    public Farm getFarm() {
        return farm;
    }
}
