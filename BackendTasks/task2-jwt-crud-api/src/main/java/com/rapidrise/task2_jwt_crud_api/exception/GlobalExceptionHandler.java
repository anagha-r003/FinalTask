package com.rapidrise.task2_jwt_crud_api.exception;

import com.rapidrise.task2_jwt_crud_api.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserNotFound(
            UserNotFoundException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ResponseStructure<Map<String, String>> res = new ResponseStructure<>();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage("Validation Failed");
        res.setData(errors);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
