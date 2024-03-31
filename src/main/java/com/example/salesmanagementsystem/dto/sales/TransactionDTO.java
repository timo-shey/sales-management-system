package com.example.salesmanagementsystem.dto.sales;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionDTO {
    private int quantity;
    private double price;
    private Long productId;
}
