package model;

public class FarmAnimal extends Animal {
    private boolean isHungry;
    private int timeTillHungry;
    private FarmAnimalType farmAnimalType;
    private int hoursToCreateProduct;
    private int maxTimeToproduce;

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

}
