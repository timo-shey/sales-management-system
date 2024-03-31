package com.example.salesmanagementsystem.dto.products;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private int quantity;
    private double price;
    private LocalDateTime createdAt;
}
