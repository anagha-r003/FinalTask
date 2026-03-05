package com.rapidrise.task1_jwt_arithmeticapi.exception;

import com.rapidrise.task1_jwt_arithmeticapi.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ResponseStructure<String>> buildResponse(
            String message, HttpStatus status) {

        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatus(status.value());
        res.setMessage("Failure");
        res.setData(message);

        return new ResponseEntity<>(res, status);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidToken(
            InvalidTokenException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(DivisionByZeroException.class)
    public ResponseEntity<ResponseStructure<String>> handleDivisionByZero(
            DivisionByZeroException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
