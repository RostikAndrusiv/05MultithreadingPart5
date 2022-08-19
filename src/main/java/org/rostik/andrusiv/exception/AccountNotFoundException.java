package org.rostik.andrusiv.exception;

public class AccountNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Account not found!";

    public AccountNotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    public AccountNotFoundException(String message){
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
