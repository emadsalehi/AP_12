package model.request;

import model.FarmAnimalType;

public class BuyRequest extends Request {
    private FarmAnimalType farmAnimalType;

    public BuyRequest(FarmAnimalType farmAnimalType) {
        this.farmAnimalType = farmAnimalType;
    }

    public FarmAnimalType getFarmAnimalType() {
        return farmAnimalType;
    }
}
