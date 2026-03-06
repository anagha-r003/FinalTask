package com.rapidrise.task2_jwt_crud_api.controller;

import com.rapidrise.task2_jwt_crud_api.dto.*;
import com.rapidrise.task2_jwt_crud_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<String>> register(
            @Valid @RequestBody RegisterRequest request){

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseStructure<>(201,"User Registered","Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<AuthResponse>> login(
           @Valid @RequestBody LoginRequest request){

        AuthResponse response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseStructure<>(200,"Login Successful",response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseStructure<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request){

        AuthResponse response = authService.refreshToken(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseStructure<>(200,"Access token refreshed",response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseStructure<String>> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody LogoutRequest request){

        String accessToken = authHeader.substring(7);

        authService.logout(accessToken, request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseStructure<>(200,"Logout successful",null));
    }
}
