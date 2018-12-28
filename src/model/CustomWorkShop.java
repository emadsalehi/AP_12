package model;

public class CustomWorkShop extends WorkShop {
    private SecondaryProduct processedProduct;
    private Product rawProduct;

    public SecondaryProduct getProcessedProduct() {
        return processedProduct;
    }

    public void setProcessedProduct(SecondaryProduct processedProduct) {
        this.processedProduct = processedProduct;
    }

    public Product getRawProduct() {
        return rawProduct;
    }

    public void setRawProduct(Product rawProduct) {
        this.rawProduct = rawProduct;
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = processedProduct;
        return secondaryProduct;
    }


}
