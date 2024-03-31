package com.example.salesmanagementsystem.dto.clients;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int mobile;
    private String email;
    private String username;
    private String address;
}
