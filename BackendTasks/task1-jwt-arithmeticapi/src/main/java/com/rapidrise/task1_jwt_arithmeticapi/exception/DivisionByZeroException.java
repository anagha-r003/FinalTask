package com.rapidrise.task1_jwt_arithmeticapi.exception;

public class DivisionByZeroException extends RuntimeException{
    public DivisionByZeroException(String message) {
        super(message);
    }
}
