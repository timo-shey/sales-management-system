package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.auth.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.auth.RefreshTokenRequestDto;
import com.example.salesmanagementsystem.exception.AppException;
import com.example.salesmanagementsystem.model.RefreshToken;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.repository.RefreshTokenRepository;
import com.example.salesmanagementsystem.repository.ClientRepository;
import com.example.salesmanagementsystem.security.JwtTokenProvider;
import com.example.salesmanagementsystem.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final ClientRepository clientRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public RefreshToken createRefreshToken(String username) {
        Client client = clientRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Client does not exist"));

        Optional<RefreshToken> existingTokenOptional = refreshTokenRepository.findByClient(client);

        RefreshToken refreshToken;
        if (existingTokenOptional.isPresent()) {
            refreshToken = existingTokenOptional.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(600));
        } else {
            refreshToken = RefreshToken.builder()
                .client(client)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusSeconds(600))
                .build();
        }

        return refreshTokenRepository.save(refreshToken);
    }


    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new AppException(HttpStatus.BAD_REQUEST, "Expired Refresh Token. Please Login.");
        }
        return token;
    }

    @Override
    public JWTAuthResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        return findByToken(refreshTokenRequestDto.getToken())
            .map(this::verifyExpiration)
            .map(RefreshToken::getClient)
            .map(user -> {
                String accessToken = jwtTokenProvider.generateToken(user.getUsername());
                return JWTAuthResponse.builder()
                    .accessToken(accessToken)
                    .tokenType("Bearer")
                    .refreshToken(refreshTokenRequestDto.getToken())
                    .build();
            }).orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Refresh Token is not in the database."));
    }
}
