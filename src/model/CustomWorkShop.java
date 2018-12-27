package model;

public class CustomWorkShop extends WorkShop {

    public CustomWorkShop(int maxTimeToFinish, int throwedProductX, int throwedProductY, int numberOfProcessedProduct) {
        //todo replace variables with number for each workshop.
        super(maxTimeToFinish, throwedProductX, throwedProductY, numberOfProcessedProduct);
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CAKE);
        return secondaryProduct;
    }
}
