package model;

import model.request.Request;

public class PrimitiveProduct extends Product {
    private PrimitiveProductType primitiveProductType;

    public PrimitiveProductType getPrimitiveProductType() {
        return primitiveProductType;
    }

    public void setPrimitiveProductType(PrimitiveProductType primitiveProductType) {
        this.primitiveProductType = primitiveProductType;
    }

    public PrimitiveProduct(PrimitiveProductType primitiveProductType) {
        this.primitiveProductType = primitiveProductType;
    }
}
