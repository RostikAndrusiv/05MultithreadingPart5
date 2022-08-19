package org.rostik.andrusiv.exception;

public class AccountExistsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "account exists!";

    public AccountExistsException(){
        super(DEFAULT_MESSAGE);
    }

    public AccountExistsException(String message){
        super(message);
    }

    public AccountExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
