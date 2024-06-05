package com.expeditors.tracksartists.exceptionHandlers.handlers;

import com.expeditors.tracksartists.exceptionHandlers.exceptionModels.GenericExceptionModel;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.InvalidBusinessLogicFieldException;
import com.expeditors.tracksartists.exceptionHandlers.exceptions.WrongRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvalidBusinessLogicFieldHandlerTest {

    @Test
    void invalidBusinessLogicField() {
        InvalidBusinessLogicFieldHandler handler = new InvalidBusinessLogicFieldHandler();

        InvalidBusinessLogicFieldException mockException = mock(InvalidBusinessLogicFieldException.class);
        when(mockException.getMessage()).thenReturn("Invalid business logic field encountered");

        GenericExceptionModel response = handler.InvalidBusinessLogicField(mockException);

        assertNotNull(response);
        assertEquals(InvalidBusinessLogicFieldException.class.getSimpleName(), response.getErrorType());
        assertEquals("This error occurs when you try to set a field with some bussines logic", response.getErrorTypeDescription());
        assertEquals("Invalid business logic field encountered", response.getErrorDescription());
        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
    }

    @Test
    void wrongRequest() {
        InvalidBusinessLogicFieldHandler handler = new InvalidBusinessLogicFieldHandler();

        WrongRequestException mockException = mock(WrongRequestException.class);
        when(mockException.getMessage()).thenReturn("Invalid request detected");
        when(mockException.getHttpStatus()).thenReturn(HttpStatus.UNPROCESSABLE_ENTITY);
        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        when(mockException.getData()).thenReturn(data);

        GenericExceptionModel response = handler.WrongRequest(mockException);

        assertNotNull(response);
        assertEquals(WrongRequestException.class.getSimpleName(), response.getErrorType());
        assertEquals("Something you are trying to do, can not be.", response.getErrorTypeDescription());
        assertEquals("Invalid request detected", response.getErrorDescription());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getHttpStatus());
        assertEquals(data, response.getData());
    }

    @Test
    public void testEntityError_WithFieldErrors() {

    }
}