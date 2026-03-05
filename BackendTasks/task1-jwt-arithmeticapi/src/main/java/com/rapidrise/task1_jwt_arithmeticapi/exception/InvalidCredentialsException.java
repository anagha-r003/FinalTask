package com.rapidrise.task1_jwt_arithmeticapi.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
