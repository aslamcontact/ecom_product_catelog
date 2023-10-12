package com.ecom.product_catelog.exceptions.product;

public class ProductExistException extends RuntimeException{

    private final String productName;
    private final String brandName;
    static final String errorMessage="Product Already Exist";

    public ProductExistException(String productName, String brandName) {
        super(errorMessage);
        this.productName = productName;
        this.brandName = brandName;
    }

    public ProductExistException(Throwable cause, String productName, String brandName) {
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
