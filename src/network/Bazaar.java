package network;

import model.Animal;
import model.Product;

import java.util.HashMap;
import java.util.Map;

public class Bazaar {
    HashMap <Product, Integer> bazaarProducts = new HashMap<>();
    HashMap<Animal, Integer> bazaarAnimals = new HashMap<>();


    public boolean sellProducts (Product product, int number) {
        Integer productNumber = number;
        for (Map.Entry<Product, Integer> productIntegerEntry : bazaarProducts.entrySet()) {
            if (productIntegerEntry.getKey().equals(product)) {
                if (productIntegerEntry.getValue() >= number ) {
                    productIntegerEntry.setValue(productIntegerEntry.getValue() - number);
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
}
