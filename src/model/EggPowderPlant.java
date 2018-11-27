package model;

public class EggPowderPlant extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.EGG_POWDER);
        return secondaryProduct;
    }
}
