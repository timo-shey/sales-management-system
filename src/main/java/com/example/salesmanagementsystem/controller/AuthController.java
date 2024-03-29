package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.LoginDto;
import com.example.salesmanagementsystem.dto.RefreshTokenRequestDto;
import com.example.salesmanagementsystem.dto.RegisterDto;
import com.example.salesmanagementsystem.service.AuthService;
import com.example.salesmanagementsystem.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "AuthController", description = "APIs for the Authentication and Authorization of users.")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "REST API to Signup User on the application.", tags = "AuthController")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.registerUser(registerDto), HttpStatus.CREATED);
    }

    @Operation(summary = "REST API to Login User to the application.", tags = "AuthController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> loginUser(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.LoginUser(loginDto));
    }

    @Operation(summary = "REST API to get a refresh token for User to refresh their access token.", tags = "AuthController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(refreshTokenRequestDto));
    }
}
