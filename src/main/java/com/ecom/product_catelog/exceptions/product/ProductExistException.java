package com.ecom.product_catelog.exceptions.product;

public class ProductExistException extends RuntimeException{

    private final String productName;
    private final String brandName;
    static final String message="Already Exist";

    public ProductExistException(String productName, String brandName) {
        super(message);
        this.productName = productName;
        this.brandName = brandName;
    }

    public ProductExistException(Throwable cause, String productName, String brandName) {
        super(message, cause);
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
