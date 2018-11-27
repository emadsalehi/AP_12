package model;

public class CakeBakery extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CAKE);
        return secondaryProduct;
    }
}
