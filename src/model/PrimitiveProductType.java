package model;

public enum PrimitiveProductType {
    EGG(1 , 20 , 10),
    MILK(10 , 2000 , 1000),
    FLOUR(2 , 20 , 10),
    PLUME(2 , 200 , 100);
    private final int depotSize;
    private final int buyCost;
    private final int saleCost;

    PrimitiveProductType(int depotSize, int buyCost, int saleCost) {
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
}
