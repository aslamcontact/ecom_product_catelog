package com.ecom.product_catelog.exceptions.images;

public class ProductCategoryNotExist extends RuntimeException {
    private final String productName;
    private final String brandName;
    static final String message="Category Not Exist";

    public ProductCategoryNotExist(String productName, String brandName) {
        super(message);
        this.productName = productName;
        this.brandName = brandName;
    }

    public ProductCategoryNotExist( Throwable cause,  String productName, String brandName) {
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
