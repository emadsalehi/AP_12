package model;

public class CustomWorkShop extends WorkShop {
    SecondaryProduct processedProduct;
    Product rawProduct;

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CAKE);
        return secondaryProduct;
    }


}
