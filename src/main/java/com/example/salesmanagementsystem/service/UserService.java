package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.JWTAuthResponse;
import com.example.salesmanagementsystem.dto.LoginDTO;
import com.example.salesmanagementsystem.dto.RegisterDTO;
import com.example.salesmanagementsystem.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    String registerUser(RegisterDTO registerDto);
    JWTAuthResponse LoginUser(LoginDTO loginDto);
    String logoutUser(HttpServletRequest request);
    User getUserByEmail(String email);
}
