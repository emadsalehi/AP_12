package network;

import com.gilecode.yagson.YaGson;
import model.*;

import java.io.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bazaar implements Serializable{

    private HashMap<String , Integer> bazaar = new HashMap<>(); {
        bazaar.put("chicken", 20);
        bazaar.put("sheep", 20);
        bazaar.put("cow", 20);
        bazaar.put("egg", 20);
        bazaar.put("milk", 20);
        bazaar.put("flour", 20);
        bazaar.put("plume", 20);
        bazaar.put("wool", 20);
        bazaar.put("cookie", 20);
        bazaar.put("egg_powder", 20);
        bazaar.put("cake", 20);
        bazaar.put("thread", 20);
        bazaar.put("clothes", 20);
        bazaar.put("fabric", 20);
    }
    private HashMap<String , Integer> priceList = new HashMap<>(); {
        bazaar.put("chicken", 100);
        bazaar.put("sheep", 1000);
        bazaar.put("cow", 10000);
        bazaar.put("egg", 10);
        bazaar.put("milk", 1000);
        bazaar.put("flour", 10);
        bazaar.put("plume", 100);
        bazaar.put("wool", 100);
        bazaar.put("cookie", 100);
        bazaar.put("egg_powder", 50);
        bazaar.put("cake", 200);
        bazaar.put("thread", 150);
        bazaar.put("clothes", 1300);
        bazaar.put("fabric", 300);
    }


    public int buy (String item , int number, int money){
        for(Map.Entry<String , Integer> stringIntegerEntry : bazaar.entrySet()){
            if (item.equals(stringIntegerEntry.getKey())){
                if(stringIntegerEntry.getValue() >= number){
                    for (Map.Entry<String, Integer> entry : priceList.entrySet()) {
                        if(item.equals(entry.getKey())) {
                            if (money >= entry.getValue() * number) {
                                bazaar.put(item, stringIntegerEntry.getValue() - number);
                                priceList.put(item, stringIntegerEntry.getValue() - number);
                                return entry.getValue() * number;
                            }
                        }
                    }

                }
            }
        }
        return -1;
    }

    public int sell (String item , int number){
        for(Map.Entry<String , Integer> stringIntegerEntry : bazaar.entrySet()){
            if (item.equals(stringIntegerEntry.getKey())){
                for (Map.Entry<String, Integer> entry : priceList.entrySet()) {
                    if(item.equals(entry.getKey())) {
                        bazaar.put(item, stringIntegerEntry.getValue() + number);
                        priceList.put(item, stringIntegerEntry.getValue() + number);
                        return entry.getValue() * number;
                    }
                }
            }
        }
        return -1;
    }

    public HashMap<String, Integer> getBazaar() {
        return bazaar;
    }

    public void setBazaar(HashMap<String, Integer> bazaar) {
        this.bazaar = bazaar;
    }

    public HashMap<String, Integer> getPriceList() {
        return priceList;
    }
}
