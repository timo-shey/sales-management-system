package com.example.salesmanagementsystem.dto.clients;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int mobile;
    private String email;
    private String address;
}
