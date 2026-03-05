package com.rapidrise.task1_jwt_arithmeticapi.controller;

import com.rapidrise.task1_jwt_arithmeticapi.dto.ResponseStructure;
import com.rapidrise.task1_jwt_arithmeticapi.service.ArithmeticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArithmeticController {

    private final ArithmeticService arithmeticService;

    public ArithmeticController(ArithmeticService arithmeticService) {
        this.arithmeticService = arithmeticService;
    }

    @GetMapping("/add")
    public ResponseEntity<ResponseStructure<Double>> add(@RequestParam double a, @RequestParam double b) {
        return ResponseEntity.ok(new ResponseStructure<>(200, "Addition Successful", arithmeticService.add(a, b)));
    }

    @GetMapping("/subtract")
    public ResponseEntity<ResponseStructure<Double>> subtract(@RequestParam double a, @RequestParam double b) {
        return ResponseEntity.ok(new ResponseStructure<>(200, "Subtraction Successful", arithmeticService.subtract(a, b)));
    }

    @GetMapping("/multiply")
    public ResponseEntity<ResponseStructure<Double>> multiply(@RequestParam double a, @RequestParam double b) {
        return ResponseEntity.ok(new ResponseStructure<>(200, "Multiplication Successful", arithmeticService.multiply(a, b)));
    }

    @GetMapping("/divide")
    public ResponseEntity<ResponseStructure<Double>> divide(@RequestParam double a, @RequestParam double b) {
        return ResponseEntity.ok(new ResponseStructure<>(200, "Division Successful", arithmeticService.divide(a, b)));
    }
}
