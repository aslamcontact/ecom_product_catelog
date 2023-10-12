package com.ecom.product_catelog.exceptions;

import com.ecom.product_catelog.exceptions.images.ImageExceptionParser;
import com.ecom.product_catelog.exceptions.images.ImageExistException;
import com.ecom.product_catelog.exceptions.images.ImageNotExistException;
import com.ecom.product_catelog.exceptions.images.ProductCategoryNotExist;
import com.ecom.product_catelog.exceptions.product.ProductExistException;
import com.ecom.product_catelog.exceptions.product.ProductExceptionParser;
import com.ecom.product_catelog.exceptions.product.ProductNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<Object> productAlreadyExist(ProductExistException exception) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ProductExceptionParser productExceptionParser = new ProductExceptionParser(
                exception.getMessage(),
                exception.getProductName(),
                exception.getBrandName(),
                httpStatus);
        return new ResponseEntity<>(productExceptionParser, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<Object> productNotExist(ProductNotExistException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProductExceptionParser productExceptionParser = new ProductExceptionParser(
                exception.getMessage(),
                exception.getProductName(),
                exception.getBrandName(),
                httpStatus);
        return new ResponseEntity<>(productExceptionParser, httpStatus);
    }


    @ExceptionHandler
    public ResponseEntity<Object> productCategoryNotExist(ProductCategoryNotExist exception) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ProductExceptionParser productExceptionParser = new ProductExceptionParser(
                exception.getMessage(),
                exception.getProductName(),
                exception.getBrandName(),
                httpStatus);
        return new ResponseEntity<>(productExceptionParser, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<Object> imageNotExist(ImageNotExistException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ImageExceptionParser imageExceptionParser = new ImageExceptionParser(exception.getMessage());
        return new ResponseEntity<>(imageExceptionParser, httpStatus);
    }
    @ExceptionHandler
    public ResponseEntity<Object> imageExist(ImageExistException exception) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ImageExceptionParser imageExceptionParser = new ImageExceptionParser(exception.getMessage());
        return new ResponseEntity<>(imageExceptionParser, httpStatus);
    }

}