package com.demo.PoS.exceptions;

public class UserNotFoundException extends IllegalStateException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
