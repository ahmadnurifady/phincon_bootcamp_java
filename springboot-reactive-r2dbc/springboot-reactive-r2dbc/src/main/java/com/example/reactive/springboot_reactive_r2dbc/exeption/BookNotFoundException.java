package com.example.reactive.springboot_reactive_r2dbc.exeption;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(Throwable cause) {
        super(cause);
    }
}
