package com.rapidrise.task1_jwt_arithmeticapi.service;

import com.rapidrise.task1_jwt_arithmeticapi.dto.AuthRequest;
import com.rapidrise.task1_jwt_arithmeticapi.dto.AuthResponse;
import com.rapidrise.task1_jwt_arithmeticapi.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(AuthRequest request){

        if ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return new AuthResponse(token);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }
}
