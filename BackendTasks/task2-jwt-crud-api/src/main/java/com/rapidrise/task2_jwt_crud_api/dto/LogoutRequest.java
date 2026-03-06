package com.rapidrise.task2_jwt_crud_api.dto;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
