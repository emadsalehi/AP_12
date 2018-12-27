package model;

public class CakeBakery extends WorkShop {

    public CakeBakery(int maxTimeToFinish, int throwedProductX, int throwedProductY, int numberOfProcessedProduct) {
        //todo replace variables with number for each workshop.
        super(maxTimeToFinish, throwedProductX, throwedProductY, numberOfProcessedProduct);
    }

    @Override
    public int getRepairCost() {
        return 0;
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.CAKE);
        return secondaryProduct;
    }
}
