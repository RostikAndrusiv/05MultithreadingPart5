package org.rostik.andrusiv.exception;

public class AccountModifiedException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "account was modified!!!";

    public AccountModifiedException(){
        super(DEFAULT_MESSAGE);
    }

    public AccountModifiedException(String message){
        super(message);
    }
}
