package com.expeditors.tracksartists.exceptionHandlers.exceptionModels;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class GenericExceptionModel {
    private String errorType;
    private String errorTypeDescription;
    private String errorDescription;
    private HttpStatus httpStatus;
    private Object data;

    public int getErrorNumber(){
        return this.httpStatus.value();
    }
}
