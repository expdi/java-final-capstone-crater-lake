package com.expeditors.tracksartists.exceptionHandlers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WrongRequestException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final Object data;

    public WrongRequestException(String msg, HttpStatus httpStatus, Object data){
        super(msg);
        this.httpStatus = httpStatus;
        this.data = data;
    }
}
