package com.ecom.product_catelog.exceptions.product;

import org.springframework.http.HttpStatus;

public class ProductExceptionParser {
     private final String errorMessage;
     private final String productName;
     private final String productBrand;
     private final HttpStatus httpStatus;


    public ProductExceptionParser( String errorMessage,
                                  String productName,
                                  String productBrand,
                                  HttpStatus httpStatus
    ) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.productBrand=productBrand;
        this.productName=productName;
    }

    public String getErrorMessage() {
        return errorMessage;
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
