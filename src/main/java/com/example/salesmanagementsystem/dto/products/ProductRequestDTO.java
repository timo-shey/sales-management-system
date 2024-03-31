package com.example.salesmanagementsystem.dto.products;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    @NotNull
    private int quantity;
    @NotNull
    private BigDecimal price;
}
