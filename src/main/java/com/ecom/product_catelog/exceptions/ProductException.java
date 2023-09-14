package com.ecom.product_catelog.exceptions;

public class ProductException extends RuntimeException{

    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
