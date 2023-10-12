package com.ecom.product_catelog.exceptions.images;

import org.springframework.http.HttpStatusCode;

public class ImageNotExistException extends RuntimeException{

    private static final String errorMessage="Image Not Exist";
    private final String  errorApiMessage;
    private final HttpStatusCode httpStatusCode;

    public ImageNotExistException( HttpStatusCode httpStatusCode,
                                   String errorApiMessage) {
        super(errorMessage);
        this.errorApiMessage=errorApiMessage;
        this.httpStatusCode=httpStatusCode;
    }

    public ImageNotExistException(   Throwable cause,
                                     String errorApiMessage,
                                     HttpStatusCode httpStatusCode) {
        super(errorApiMessage, cause);
        this.httpStatusCode=httpStatusCode;
        this.errorApiMessage=errorApiMessage;
    }


}
