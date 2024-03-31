package com.example.salesmanagementsystem.dto.sales;

import com.example.salesmanagementsystem.dto.clients.ClientResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SalesResponseDTO {
    private Long id;
    private ClientResponseDTO client;
    private ClientResponseDTO seller;
    private double total;
    private LocalDateTime creationDate;
    private List<TransactionDTO> transactions;
}
