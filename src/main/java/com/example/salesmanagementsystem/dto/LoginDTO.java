package com.example.salesmanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {
    @NotEmpty(message = "Username may not be empty")
    private String username;
    @NotEmpty(message = "Password may not be empty")
    private String password;
}
