package model.request;

import model.Animal;
import model.FarmAnimalType;

public class BuyRequest extends Request {
    private Animal animal;

    public BuyRequest(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }
}
