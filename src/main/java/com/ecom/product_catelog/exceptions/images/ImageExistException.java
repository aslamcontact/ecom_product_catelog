package com.ecom.product_catelog.exceptions.images;

import org.springframework.http.HttpStatusCode;

public class ImageExistException extends RuntimeException{

    private static final String errorMessage="Image Already Exist";
    private final String  errorApiMessage;
    private final HttpStatusCode httpStatusCode;

    public ImageExistException( HttpStatusCode httpStatusCode,
                                   String errorApiMessage) {
        super(errorMessage);
        this.errorApiMessage=errorApiMessage;
        this.httpStatusCode=httpStatusCode;
    }

    public ImageExistException(Throwable cause,
                                  String apiErrorMessage,
                                  String errorApiMessage,
                                  HttpStatusCode httpStatusCode) {
        super(apiErrorMessage, cause);
        this.httpStatusCode=httpStatusCode;
        this.errorApiMessage=errorApiMessage;
    }


}
