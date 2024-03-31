package com.example.salesmanagementsystem.dto.sales;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private int quantity;
    private double price;
    private Long productId;
}
