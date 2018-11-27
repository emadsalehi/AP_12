package model;

public class Spinnery extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.THREAD);
        return secondaryProduct;
    }
}
