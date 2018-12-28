package model;

public class CustomWorkShop extends WorkShop {
    private SecondaryProduct processedProduct;
    private Product rawProduct;



    public void setProcessedProduct(SecondaryProduct processedProduct) {
        this.processedProduct = processedProduct;
    }

    public Product getRawProduct() {
        return rawProduct;
    }

    public void setRawProduct(Product rawProduct) {
        this.rawProduct = rawProduct;
    }

    public CustomWorkShop(int maxTimeToFinish, int throwedProductX, int throwedProductY, int numberOfProcessedProduct) {
        super(maxTimeToFinish, throwedProductX, throwedProductY, numberOfProcessedProduct);
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = processedProduct;
        return secondaryProduct;
    }
}
