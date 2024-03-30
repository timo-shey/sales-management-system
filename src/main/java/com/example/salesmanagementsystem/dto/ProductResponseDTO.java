package com.example.salesmanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private int quantity;
    private double price;
    private LocalDateTime createdAt;
}
