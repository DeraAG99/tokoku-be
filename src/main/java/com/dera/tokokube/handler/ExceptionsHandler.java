package com.dera.tokokube.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dera.tokokube.exceptions.BadRequestException;
import com.dera.tokokube.exceptions.ResourceNotFoundException;
import com.dera.tokokube.model.ApiResponse;
import com.dera.tokokube.model.ExceptionResponse;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    // Inject exception model (Exception Response)
    @Autowired
    ExceptionResponse exceptionResponse;

    @Autowired
    ApiResponse apiResponse;

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(), ex.getErrors());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // Handler untuk pengecualian umum
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
