package com.example.salesmanagementsystem.dto.sales;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class SalesRequestDTO {
    @NotEmpty(message = "Seller ID may not be empty")
    private Long sellerId;
    @NotNull
    private BigDecimal total;
    private List<TransactionDTO> transactions;
}
