package org.koreait.rest;

import org.springframework.http.HttpStatus;

public class RestCommonException extends RuntimeException{

    private HttpStatus status;

    public RestCommonException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }


}
