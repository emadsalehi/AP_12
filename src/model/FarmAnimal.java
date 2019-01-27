package model;

public class FarmAnimal extends Animal {
    private boolean isHungry = false;
    private int timeTillHungry = 10;
    private int timeTillFill = 5;
    private FarmAnimalType farmAnimalType;
    private int productionPeriod;
    private int productionTimer = productionPeriod;
    private boolean readyToProduce = false;


    public FarmAnimal(int x, int y, FarmAnimalType farmAnimalType) {
        super(x, y);
        this.farmAnimalType = farmAnimalType;
        this.productionPeriod = farmAnimalType.getProductionPeriod();
    }

    @Override
    public void move(int destinationX, int destinationY) {
        if (!isHungry) {
            randomMove();
        }
        else {
            intendedMove(destinationX, destinationY);
        }
    }

    public void nextTurn(){
        productionTimer--;
        timeTillHungry--;
        if (timeTillHungry == 0) {
            isHungry = true;
        }
        if (isHungry == false && timeTillHungry <= 0) {
            timeTillHungry = 10;
        }
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

    public int getTimeTillFill() {
        return timeTillFill;
    }

    public void setTimeTillFill(int timeTillFill) {
        this.timeTillFill = timeTillFill;
    }
}
