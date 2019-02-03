package network;

import com.gilecode.yagson.YaGson;
import model.*;

import java.io.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bazaar implements Serializable{
    //private  HashMap <Product, Integer> bazaarProducts = new HashMap<>();
    //private  HashMap<Animal, Integer> bazaarAnimals = new HashMap<>();
    private HashMap<String , Integer> bazaar = new HashMap<>();
    private HashMap<String , Integer> priceList = new HashMap<>();


    public boolean sell (String Item , int number){
        Integer itemNumber = number;
        for(Map.Entry<String , Integer> stringIntegerEntry : bazaar.entrySet()){
            if (Item.equals(stringIntegerEntry.getKey())){
                if(stringIntegerEntry.getValue() >= number){
                    stringIntegerEntry.setValue(stringIntegerEntry.getValue() - number);
                    return true;
                } else {
                    return false;
                }

            }
        }
        return false;
    }

    public void buy(String item , int number){
        for (Map.Entry<String , Integer> stringIntegerEntry : bazaar.entrySet()){
            if(stringIntegerEntry.getKey().equals(item)){
                stringIntegerEntry.setValue(stringIntegerEntry.getValue() + number);
            }
        }
    }

    /*public boolean sellProducts (Product product, int number) {
        Integer productNumber = number;
        String name = "";
        if (product instanceof PrimitiveProduct){
            name = ((PrimitiveProduct) product).getPrimitiveProductType().name();
        }else if (product instanceof SecondaryProduct){
            name = ((SecondaryProduct) product).getSecondaryProductType().name();
        }
        for (Map.Entry<Product, Integer> productIntegerEntry : bazaarProducts.entrySet()) {
            if (productIntegerEntry.getKey() instanceof PrimitiveProduct){
                if (((PrimitiveProduct) productIntegerEntry.getKey()).getPrimitiveProductType().name().equals(name)){
                    if (productIntegerEntry.getValue() >= number ) {
                        productIntegerEntry.setValue(productIntegerEntry.getValue() - number);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }else if(productIntegerEntry.getKey() instanceof SecondaryProduct){
                if (((SecondaryProduct) productIntegerEntry.getKey()).getSecondaryProductType().name().equals(name)){
                    if (productIntegerEntry.getValue() >= number ) {
                        productIntegerEntry.setValue(productIntegerEntry.getValue() - number);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean sellAnimals (Animal animal, int number) {
        Integer animalNumber = number;
        String name = "";
        if (animal instanceof FarmAnimal){
            name = ((FarmAnimal) animal).getFarmAnimalType().name();
        }else if (animal instanceof WildAnimal){
            name = ((WildAnimal) animal).getWildAnimalType().name();
        }
        for (Map.Entry<Animal, Integer> animalIntegerEntry : bazaarAnimals.entrySet()) {
            if (animalIntegerEntry.getKey() instanceof FarmAnimal){
                if (((FarmAnimal) animalIntegerEntry.getKey()).getFarmAnimalType().name().equals(name)){
                    if (animalIntegerEntry.getValue() >= number ) {
                        animalIntegerEntry.setValue(animalIntegerEntry.getValue() - number);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }else if (animalIntegerEntry.getKey() instanceof WildAnimal){
                if (((WildAnimal) animalIntegerEntry.getKey()).getWildAnimalType().name().equals(name)){
                    if (animalIntegerEntry.getValue() >= number ) {
                        animalIntegerEntry.setValue(animalIntegerEntry.getValue() - number);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void buyProducts(Product product, int number) {
        Integer productNumber = number;
        for (Map.Entry<Product , Integer> productIntegerEntry : bazaarProducts.entrySet()){
            if (productIntegerEntry.)
        }
    }

    public void buyAnimals(Animal animal, int number) {
        Integer AnimalNumber = number;
        for (Map.Entry<Animal , Integer> animalIntegerEntry : bazaarAnimals.entrySet()){
            if (animalIntegerEntry.getKey().getClass().toString().equals(animal.getClass().toString())){
                animalIntegerEntry.setValue(animalIntegerEntry.getValue() + number);
            }
        }
    }*/

    public void serializeBazaar(){
        YaGson yaGson = new YaGson();
        String bazaarJson = yaGson.toJson(bazaar , HashMap.class);

        try {
            OutputStream outputStream1 = new FileOutputStream("/bazaar.json");
            Formatter formatter1 = new Formatter(outputStream1);
            formatter1.format(bazaarJson);
            formatter1.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializePrice(){
        YaGson yaGson = new YaGson();
        String priceJson = yaGson.toJson(priceList , HashMap.class);

        try {
            OutputStream outputStream1 = new FileOutputStream("/price.json");
            Formatter formatter1 = new Formatter(outputStream1);
            formatter1.format(priceJson);
            formatter1.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String , Integer> deserializeBazaar(){
        YaGson yaGson = new YaGson();
        InputStream inputStream = null;
        HashMap<String , Integer> bazaar = new HashMap<>();
        try {
            inputStream = new FileInputStream("/bazaar.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        String bazaarJson = scanner.nextLine();
        bazaar = yaGson.fromJson(bazaarJson , HashMap.class);
        return bazaar;
    }

    public HashMap<String , Integer> deserializePrice(){
        YaGson yaGson = new YaGson();
        InputStream inputStream = null;
        HashMap<String , Integer> price = new HashMap<>();
        try {
            inputStream = new FileInputStream("/price.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream);
        String priceJson = scanner.nextLine();
        price = yaGson.fromJson(priceJson , HashMap.class);
        return bazaar;
    }


}
