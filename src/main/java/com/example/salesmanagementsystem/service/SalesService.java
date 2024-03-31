package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.sales.SalesRequestDTO;
import com.example.salesmanagementsystem.dto.sales.SalesResponseDTO;
import com.example.salesmanagementsystem.dto.sales.TransactionDTO;

import java.util.List;

public interface SalesService {
    SalesResponseDTO createSale(SalesRequestDTO sale);
    List<SalesResponseDTO> getAllSales();
    SalesResponseDTO getSalesById(Long id);
    SalesResponseDTO editSaleTransactions(Long saleId, List<TransactionDTO> transactions);

}
