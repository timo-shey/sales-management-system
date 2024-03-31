package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "AppController", description = "API for Invalidating Access Token.")
@RequiredArgsConstructor
public class AppController {
    private final ClientService clientService;

    @Operation(summary = "REST API to Logout a Client from the application.", tags = "AppController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        return ResponseEntity.ok(clientService.logoutUser(request));
    }
}
