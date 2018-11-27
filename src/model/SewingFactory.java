package model;

public class SewingFactory extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CLOTHES);
        return secondaryProduct;
    }
}
