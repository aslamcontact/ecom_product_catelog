package com.ecom.product_catelog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<Object> productNotFound(ProductException e)
    {
        HttpStatus httpStatus=HttpStatus.CONFLICT;
        ExceptionParse exceptionParse=new ExceptionParse(
                e.getMessage(),
                httpStatus);
        return new ResponseEntity<>(exceptionParse,httpStatus);
    }
}
