package com.example.salesmanagementsystem.dto.sales;

import com.example.salesmanagementsystem.dto.clients.ClientResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponseDTO {
    private Long id;
    private ClientResponseDTO client;
    private ClientResponseDTO seller;
    private double total;
    private LocalDateTime creationDate;
    private List<TransactionDTO> transactions;
}
