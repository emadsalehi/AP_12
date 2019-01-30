package model;

public class EggPowderPlant extends WorkShop {
    public EggPowderPlant() {
        super(15, 7, 0, 1);
    }

    @Override
    public int getUpgradeCost() {
        if (this.getLevel() == 0)
            return 250;
        else if (this.getLevel() == 1)
            return 350;
        else if (this.getLevel() == 2)
            return 450;
        else
            return 500;
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        this.setNumberOfProcessedProduct(this.getNumberOfProcessedProduct() + 1);
        this.setNumberOfRawProduct(this.getNumberOfRawProduct() + 1);

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
        SecondaryProduct secondaryProduct = new SecondaryProduct(SecondaryProductType.EGG_POWDER);
        return secondaryProduct;
    }
}
