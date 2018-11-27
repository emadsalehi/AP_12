package model;

public class CookieBakery extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.COOKIE);
        return secondaryProduct;
    }
}
