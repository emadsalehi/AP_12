package model;

public enum SecondaryProductType {
    COOKIE(5 , 200 , 100),
    EGG_POWDER(4 , 100 , 50),
    CAKE(6 , 400 , 200),
    THREAD(3 , 300 , 150),
    CLOTHES(8 , 1400 , 1300),
    FABRIC (6 , 400 , 300);
    private final int depotSize;
    private final int buyCost;
    private final int saleCost;

    SecondaryProductType(int depotSize, int buyCost, int saleCost) {
        this.depotSize = depotSize;
        this.buyCost = buyCost;
        this.saleCost = saleCost;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
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
