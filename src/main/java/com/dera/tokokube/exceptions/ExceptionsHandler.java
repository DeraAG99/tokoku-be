package com.dera.tokokube.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, null, HttpStatus.NOT_FOUND, request);
    }

    // Handler untuk pengecualian umum
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        String bodyOfResponse = "Internal Server Error";
        return handleExceptionInternal(ex, bodyOfResponse, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
