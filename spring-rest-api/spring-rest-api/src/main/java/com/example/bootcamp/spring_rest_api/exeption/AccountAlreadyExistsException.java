package com.example.bootcamp.spring_rest_api.exeption;

public class AccountAlreadyExistsException extends RuntimeException{
    private String message;
    
    public AccountAlreadyExistsException() {
        super();
    }
    
    public AccountAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public AccountAlreadyExistsException(final String message) {
        super(message);
        this.message = message;
    }

    public AccountAlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
