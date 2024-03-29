package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.LoginDto;
import com.example.salesmanagementsystem.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    String registerUser(RegisterDto registerDto);
    JWTAuthResponse LoginUser(LoginDto loginDto);
    String logoutUser(HttpServletRequest request);
}
