package model;

public class SecondaryProduct extends Product {
    private SecondaryProductType secondaryProductType;

    public SecondaryProduct(SecondaryProductType secondaryProductType) {
        this.secondaryProductType = secondaryProductType;
    }
}
