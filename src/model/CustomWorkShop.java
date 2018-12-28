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

    public CustomWorkShop() {
        super(15, 0, 0, 1);
    }

    @Override
    public int getUpgradeCost() {
        if (this.getLevel() == 0)
            return 3500;
        else if (this.getLevel() == 1)
            return 4000;
        else if (this.getLevel() == 2)
            return 4500;
        else
            return 5000;
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        this.setNumberOfProcessedProduct(this.getNumberOfProcessedProduct() + 1);
        if (this.getLevel() == 0)
            this.setMaxTimeToFinish(14);
        else if (this.getLevel() == 1)
            this.setMaxTimeToFinish(13);
        else if (this.getLevel() == 2)
            this.setMaxTimeToFinish(11);
        else
            this.setMaxTimeToFinish(8);
    }

    @Override
    public SecondaryProduct getProduct() {
        SecondaryProduct secondaryProduct = processedProduct;
        return secondaryProduct;
    }

    public SecondaryProduct getProcessedProduct() {
        return processedProduct;
    }
}
