package model;

public class WildAnimal extends Animal {
    private WildAnimalType wildAnimalType;

    public WildAnimal(int x, int y, WildAnimalType wildAnimalType) {
        super(x, y);
        this.wildAnimalType = wildAnimalType;
    }

    @Override
    public void move(int destinationX, int destinationY) {

    }

    public WildAnimalType getWildAnimalType() {
        return wildAnimalType;
    }

    public void setWildAnimalType(WildAnimalType wildAnimalType) {
        this.wildAnimalType = wildAnimalType;
    }
}
