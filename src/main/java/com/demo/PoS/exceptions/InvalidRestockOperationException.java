package com.demo.PoS.exceptions;

public class InvalidRestockOperationException extends RuntimeException {
    public InvalidRestockOperationException(String message) {
        super(message);
    }
}
