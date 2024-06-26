package com.example.salesmanagementsystem.dto.clients;

import com.example.salesmanagementsystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRequestDTO {

    @NotEmpty(message = "Firstname may not be empty")
    private String firstName;
    @NotEmpty(message = "Lastname may not be empty")
    private String lastName;
    @NotEmpty(message = "Email may not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Username may not be empty")
    private String username;
    @NotNull
    private int mobile;
    @NotEmpty(message = "Address may not be empty")
    private String address;
    @NotEmpty(message = "Password may not be empty")
    private String password;
    private Role role;
}
