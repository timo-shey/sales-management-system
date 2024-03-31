package com.example.salesmanagementsystem.dto.sales;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SalesRequestDTO {
    @NotEmpty(message = "Client ID may not be empty")
    private Long clientId;
    @NotEmpty(message = "Seller ID may not be empty")
    private Long sellerId;
    private List<TransactionDTO> transactions;
}
