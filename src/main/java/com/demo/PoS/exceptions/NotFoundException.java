package com.demo.PoS.exceptions;

public class NotFoundException extends IllegalStateException {
    public NotFoundException(String message) {
        super(message);
    }
}
