package model;

public class WeavingFactory extends WorkShop {
    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.FABRIC);
        return secondaryProduct;
    }
}
