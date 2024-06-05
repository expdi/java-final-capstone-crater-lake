package com.expeditors.tracksartists.exceptionHandlers.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidBusinessLogicFieldExceptionTest {
    @Test
    public void testInvalidBusinessLogicFieldException() {
        String expectedMessage = "This is an invalid business logic field error";

        InvalidBusinessLogicFieldException exception = new InvalidBusinessLogicFieldException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());

        assertInstanceOf(RuntimeException.class, exception);
        assertInstanceOf(InvalidBusinessLogicFieldException.class, exception);
    }
}