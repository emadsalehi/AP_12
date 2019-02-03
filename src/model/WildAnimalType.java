package model;

public enum WildAnimalType {
    LION(20 , 150 , 150),
    BEAR(20 , 100 , 100);

    private final int depotSize;
    private final int buyCost;
    private final int saleCost;

    WildAnimalType(int depotSize, int buyCost, int saleCost) {
        this.depotSize = depotSize;
        this.buyCost = buyCost;
        this.saleCost = saleCost;
    }

    public int getDepotSize() {
        return depotSize;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSaleCost() {
        return saleCost;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
