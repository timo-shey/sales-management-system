package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.RefreshTokenRequestDto;
import com.example.salesmanagementsystem.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    JWTAuthResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
