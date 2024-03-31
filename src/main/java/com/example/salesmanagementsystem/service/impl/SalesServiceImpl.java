package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.sales.SalesRequestDTO;
import com.example.salesmanagementsystem.dto.sales.SalesResponseDTO;
import com.example.salesmanagementsystem.dto.sales.TransactionDTO;
import com.example.salesmanagementsystem.exception.ResourceNotFoundException;
import com.example.salesmanagementsystem.model.AuditLog;
import com.example.salesmanagementsystem.model.Sale;
import com.example.salesmanagementsystem.model.Transaction;
import com.example.salesmanagementsystem.repository.AuditLogRepository;
import com.example.salesmanagementsystem.repository.SaleRepository;
import com.example.salesmanagementsystem.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceImpl.class);
    private final SaleRepository saleRepository;
    private final ModelMapper mapper;
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public SalesResponseDTO createSale(SalesRequestDTO saleDTO) {
        Sale sale = mapper.map(saleDTO, Sale.class);
        Sale savedSale = saleRepository.save(sale);

        LOGGER.info(String.format("Sales Operation Created --> ID = %d, Client = %s, Seller = %s, Total = %f, Date = %s", sale.getId(),
            sale.getClient().getUsername(), sale.getSeller().getUsername(), sale.getTotal(), sale.getCreatedAt().toString()));
        logAuditTrail("Sales Operation Created", sale);

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

        LOGGER.info(String.format("Sales Operation Retrieved --> ID = %d, Client = %s, Seller = %s, Total = %f, Date = %s", sale.getId(),
            sale.getClient().getUsername(), sale.getSeller().getUsername(), sale.getTotal(), sale.getCreatedAt().toString()));
        logAuditTrail("Sales Operation Retrieved", sale);

        return mapper.map(sale, SalesResponseDTO.class);
    }

    @Override
    @Transactional
    public SalesResponseDTO editSaleTransactions(Long saleId, List<TransactionDTO> transactionDTOList) {
        Sale sale = saleRepository.findById(saleId)
            .orElseThrow(() -> new ResourceNotFoundException("Sale", "salesId", saleId));

        List<Transaction> transactions = transactionDTOList.stream()
            .map(transactionDTO -> mapper.map(transactionDTO, Transaction.class))
            .collect(Collectors.toList());
        sale.setTransactions(transactions);

        Sale updatedSale = saleRepository.save(sale);

        LOGGER.info(String.format("Edited Sales Transaction --> ID = %d, Client = %s, Seller = %s, Total = %f, Date = %s",
            updatedSale.getId(), updatedSale.getClient().getUsername(), updatedSale.getSeller().getUsername(), updatedSale.getTotal(),
            updatedSale.getCreatedAt().toString()));
        logAuditTrail("Edited Sales Transaction", updatedSale);

        return mapper.map(updatedSale, SalesResponseDTO.class);
    }

    private void logAuditTrail(String action, Sale sale) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityType("Sale");
        auditLog.setEntityId(sale.getId());
        auditLog.setTimestamp(new Date());
        auditLogRepository.save(auditLog);
    }
}
