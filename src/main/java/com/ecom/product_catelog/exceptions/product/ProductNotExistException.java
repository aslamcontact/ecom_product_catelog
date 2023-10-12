package com.ecom.product_catelog.exceptions.product;

public class ProductNotExistException extends RuntimeException{
    private final String productName;
    private final String brandName;
    static final String errorMessage="There is No Product";

    public ProductNotExistException(String productName, String brandName) {
        super(errorMessage);
        this.productName = productName;
        this.brandName = brandName;
    }

    public ProductNotExistException( Throwable cause,  String productName, String brandName) {
        super(errorMessage, cause);
        this.productName = productName;
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }
}
