package com.expeditors.tracksartists.exceptionHandlers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class WrongRequestException extends RuntimeException{

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private Object data;

    public WrongRequestException(String msg, HttpStatus httpStatus, Object data){
        super(msg);
        this.httpStatus = httpStatus;
        this.data = data;
    }
}
