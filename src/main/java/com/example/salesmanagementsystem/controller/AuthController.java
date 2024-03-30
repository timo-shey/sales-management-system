package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.LoginDTO;
import com.example.salesmanagementsystem.dto.RefreshTokenRequestDto;
import com.example.salesmanagementsystem.dto.ClientRequestDTO;
import com.example.salesmanagementsystem.service.ClientService;
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

    private final ClientService clientService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "REST API to Signup Client on the application.", tags = "AuthController")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody ClientRequestDTO clientRequestDto) {
        return new ResponseEntity<>(clientService.registerClient(clientRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "REST API to Login Client to the application.", tags = "AuthController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> loginUser(@Valid @RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(clientService.LoginClient(loginDto));
    }

    @Operation(summary = "REST API to get a refresh token for Client to refresh their access token.", tags = "AuthController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping("/refreshToken")
    public ResponseEntity<JWTAuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(refreshTokenRequestDto));
    }
}
