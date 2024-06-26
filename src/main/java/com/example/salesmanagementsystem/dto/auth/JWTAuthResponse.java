package com.example.salesmanagementsystem.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
}
