package com.rapidrise.task1_jwt_arithmeticapi.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
