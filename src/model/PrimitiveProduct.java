package model;

import model.request.Request;

public class PrimitiveProduct extends Product {
    private PrimitiveProductType primitiveProductType;

    public PrimitiveProduct(PrimitiveProductType primitiveProductType) {
        this.primitiveProductType = primitiveProductType;
    }

    public PrimitiveProductType getPrimitiveProductType() {
        return primitiveProductType;
    }
}
