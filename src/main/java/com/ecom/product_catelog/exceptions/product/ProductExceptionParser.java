package com.ecom.product_catelog.exceptions.product;

import org.springframework.http.HttpStatus;

public class ProductExceptionParser {
     private final String message;
     private final String productName;
     private final String productBrand;
     private final HttpStatus httpStatus;


    public ProductExceptionParser(String message,
                                  String productName,
                                  String productBrand,
                                  HttpStatus httpStatus
    ) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.productBrand=productBrand;
        this.productName=productName;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }
}
