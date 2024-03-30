package com.example.salesmanagementsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductRequestDTO {
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NotEmpty(message = "Description may not be empty")
    private String description;
    @NotEmpty(message = "Category may not be empty")
    private String category;
    @NotEmpty(message = "Quantity may not be empty")
    private int quantity;
    @NotEmpty(message = "Price may not be empty")
    private double price;
}