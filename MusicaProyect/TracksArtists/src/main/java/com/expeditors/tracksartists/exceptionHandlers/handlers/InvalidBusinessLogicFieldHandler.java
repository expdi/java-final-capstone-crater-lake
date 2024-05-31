package com.expeditors.tracksartists.exceptionHandlers.handlers;

import com.expeditors.tracksartists.exceptionHandlers.exceptionModels.GenericExceptionModel;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.InvalidBusinessLogicFieldException;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBusinessLogicFieldHandler {

    @ExceptionHandler(value = {InvalidBusinessLogicFieldException.class})
    protected GenericExceptionModel InvalidBusinessLogicField(Exception ex) {
        return GenericExceptionModel.builder().errorType(InvalidBusinessLogicFieldException.class.getSimpleName())
                .errorTypeDescription("This error occurs when you try to set a field with some bussines logic")
                .errorDescription(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(value = {WrongRequestException.class})
    protected GenericExceptionModel WrongRequest(WrongRequestException ex) {
        return GenericExceptionModel.builder().errorType(WrongRequestException.class.getSimpleName())
                .errorTypeDescription("Something you are trying to do, can not be.")
                .errorDescription(ex.getMessage())
                .httpStatus(ex.getHttpStatus())
                .data(ex.getData())
                .build();
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected GenericExceptionModel EntityError(MethodArgumentNotValidException ex) {
        List<String> errorsList = new ArrayList<>();
        for (FieldError fieldError: ex.getFieldErrors()) {
            errorsList.add(new StringBuilder().append("Field: ").append(fieldError.getField())
                    .append(", Error: ") .append(fieldError.getDefaultMessage())
                    .append(", Supplied Value: ").append("<").append(fieldError.getRejectedValue()).append(">")
                    .toString());
        }

        return GenericExceptionModel.builder().errorType(MethodArgumentNotValidException.class.getSimpleName())
                .errorTypeDescription("This error occurs when some fields do not contain specific details.")
                .errorDescription("Please follow the next list of errors from the <" + ex.getParameter().getParameterType().getSimpleName() + "> entity")
                .data(errorsList)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
