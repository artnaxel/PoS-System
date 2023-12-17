package com.demo.PoS.exceptions;

public class ItemNotFoundException extends IllegalStateException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
