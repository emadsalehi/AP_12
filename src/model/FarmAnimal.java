package model;

public class FarmAnimal extends Animal {
    private boolean isHungry;
    private int timeTillHungry;
    private FarmAnimalType farmAnimalType;
    private int productionPeriod = farmAnimalType.getProductionPeriod();
    private int productionTimer = productionPeriod;
    private boolean readyToProduce = false;


    public FarmAnimal(int x, int y, FarmAnimalType farmAnimalType) {
        super(x, y);
        this.farmAnimalType = farmAnimalType;
    }

    @Override
    public void move(int destinationX, int destinationY) {
        if (destinationX == 31) {
            randomMove();
        }
        else {
            intendedMove(destinationX, destinationY);
        }
    }

    public void nextTurn(){
        productionTimer--;
        if (productionTimer == 0){
            productionTimer = productionPeriod;
            readyToProduce = true;
        }
    }


    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public int getTimeTillHungry() {
        return timeTillHungry;
    }

    public void setTimeTillHungry(int timeTillHungry) {
        this.timeTillHungry = timeTillHungry;
    }

    public FarmAnimalType getFarmAnimalType() {
        return farmAnimalType;
    }

    public void setFarmAnimalType(FarmAnimalType farmAnimalType) {
        this.farmAnimalType = farmAnimalType;
    }

    public boolean isReadyToProduce() {
        return readyToProduce;
    }

    public void setReadyToProduce(boolean readyToProduce) {
        this.readyToProduce = readyToProduce;
    }
}
