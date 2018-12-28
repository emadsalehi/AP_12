package model;

public class SewingFactory extends WorkShop {

    public SewingFactory(int maxTimeToFinish, int throwedProductX, int throwedProductY, int numberOfProcessedProduct) {
        //todo replace variables with number for each workshop.
        super(maxTimeToFinish, throwedProductX, throwedProductY, numberOfProcessedProduct);
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CLOTHES);
        return secondaryProduct;
    }
}
