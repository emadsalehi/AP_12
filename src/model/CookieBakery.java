package model;

public class CookieBakery extends WorkShop {

    public CookieBakery(int maxTimeToFinish, int throwedProductX, int throwedProductY, int numberOfProcessedProduct) {
        //todo replace variables with number for each workshop.
        super(maxTimeToFinish, throwedProductX, throwedProductY, numberOfProcessedProduct);
    }

    @Override
    public int getRepairCost() {
        return 0;
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.COOKIE);
        return secondaryProduct;
    }
}
