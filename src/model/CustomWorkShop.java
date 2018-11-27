package model;

public class CustomWorkShop extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CAKE);
        return secondaryProduct;
    }
}
