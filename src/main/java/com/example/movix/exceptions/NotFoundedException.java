package com.example.movix.exceptions;

public class NotFoundedException extends RuntimeException {
    public NotFoundedException(String message) {
        super(message);
    }
}
