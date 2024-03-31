package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.sales.SalesRequestDTO;
import com.example.salesmanagementsystem.dto.sales.SalesResponseDTO;
import com.example.salesmanagementsystem.dto.sales.TransactionDTO;
import com.example.salesmanagementsystem.exception.ResourceNotFoundException;
import com.example.salesmanagementsystem.model.Sale;
import com.example.salesmanagementsystem.model.Transaction;
import com.example.salesmanagementsystem.repository.SaleRepository;
import com.example.salesmanagementsystem.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SaleRepository saleRepository;
    private final ModelMapper mapper;

    @Override
    public SalesResponseDTO createSale(SalesRequestDTO saleDTO) {
        Sale sale = mapper.map(saleDTO, Sale.class);
        Sale savedSale = saleRepository.save(sale);
        return mapper.map(savedSale, SalesResponseDTO.class);
    }

    @Override
    public List<SalesResponseDTO> getAllSales() {
        List<Sale> sales = saleRepository.findAll();

        return sales.stream()
            .map(sale -> mapper.map(sale, SalesResponseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public SalesResponseDTO getSalesById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Sale", "salesId", id)
        );

        return mapper.map(sale, SalesResponseDTO.class);
    }

    @Override
    public SalesResponseDTO editSaleTransactions(Long saleId, List<TransactionDTO> transactionDTOList) {
        Sale sale = saleRepository.findById(saleId)
            .orElseThrow(() -> new ResourceNotFoundException("Sale", "salesId", saleId));

        List<Transaction> transactions = transactionDTOList.stream()
            .map(transactionDTO -> mapper.map(transactionDTO, Transaction.class))
            .collect(Collectors.toList());
        sale.setTransactions(transactions);

        Sale updatedSale = saleRepository.save(sale);

        return mapper.map(updatedSale, SalesResponseDTO.class);
    }
}
