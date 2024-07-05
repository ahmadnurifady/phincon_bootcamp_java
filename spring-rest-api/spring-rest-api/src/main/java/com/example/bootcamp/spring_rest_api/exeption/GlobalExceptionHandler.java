package com.example.bootcamp.spring_rest_api.exeption;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.bootcamp.spring_rest_api.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
     Logger myLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler({NoSuchAccountExistsException.class, NoSuchElementException.class})    
    public ResponseEntity<Object> handleNoSuchAccountExistsException(final Exception ex, final WebRequest request) {
        myLogger.error("handle NoSuchAccountExistsException, NoSuchElementException", ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return createResponseEntity(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    
    }

    @ExceptionHandler({AccountAlreadyExistsException.class})    
    public ResponseEntity<Object> handleCustomerAlreadyExistsException(final Exception ex, final WebRequest request) {
        myLogger.error("handle AccountAlreadyExistsException", ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        myLogger.error("handle Exception", ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
