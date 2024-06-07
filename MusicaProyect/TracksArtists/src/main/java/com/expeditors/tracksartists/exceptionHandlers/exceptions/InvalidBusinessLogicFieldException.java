package com.expeditors.tracksartists.exceptionHandlers.exceptions;

public class InvalidBusinessLogicFieldException extends RuntimeException{

    public InvalidBusinessLogicFieldException(String msg){
        super(msg);
    }
}
