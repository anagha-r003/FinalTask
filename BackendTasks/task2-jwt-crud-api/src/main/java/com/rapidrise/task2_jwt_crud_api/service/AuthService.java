package com.rapidrise.task2_jwt_crud_api.service;

import com.rapidrise.task2_jwt_crud_api.dto.*;
import com.rapidrise.task2_jwt_crud_api.entity.BlackListedToken;
import com.rapidrise.task2_jwt_crud_api.entity.RefreshToken;
import com.rapidrise.task2_jwt_crud_api.entity.User;
import com.rapidrise.task2_jwt_crud_api.exception.InvalidCredentialsException;
import com.rapidrise.task2_jwt_crud_api.exception.UserAlreadyExistsException;
import com.rapidrise.task2_jwt_crud_api.repository.BlackListedTokenRepository;
import com.rapidrise.task2_jwt_crud_api.repository.RefreshTokenRepository;
import com.rapidrise.task2_jwt_crud_api.repository.UserRepository;
import com.rapidrise.task2_jwt_crud_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final BlackListedTokenRepository blacklistedTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public void register(RegisterRequest request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("Username already exists");
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidCredentialsException("Passwords do not match");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid password");
        }

        refreshTokenRepository.deleteByUser(user);

        String accessToken = jwtUtil.generateAccessToken(user.getUsername());

        String refreshTokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpiration));

        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken, refreshTokenValue);

    }

    public AuthResponse refreshToken(RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }

        String newAccessToken =
                jwtUtil.generateAccessToken(refreshToken.getUser().getUsername());

        return new AuthResponse(
                newAccessToken,
                refreshToken.getToken()
        );
    }

    public void logout(String accessToken, String refreshToken){

        if(!blacklistedTokenRepository.findByToken(accessToken).isPresent()){
            BlackListedToken token = new BlackListedToken();
            token.setToken(accessToken);
            token.setBlacklistedAt(Instant.now());

            blacklistedTokenRepository.save(token);
        }

        RefreshToken refreshTokenEntity = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenRepository.delete(refreshTokenEntity);
    }
}
