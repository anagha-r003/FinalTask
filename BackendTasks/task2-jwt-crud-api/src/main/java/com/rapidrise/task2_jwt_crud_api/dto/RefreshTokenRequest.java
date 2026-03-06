package com.rapidrise.task2_jwt_crud_api.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
