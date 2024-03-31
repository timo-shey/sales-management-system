package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.sales.SalesRequestDTO;
import com.example.salesmanagementsystem.dto.sales.SalesResponseDTO;
import com.example.salesmanagementsystem.dto.sales.TransactionDTO;
import com.example.salesmanagementsystem.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "SaleController", description = "APIs for Creating, Fetching and Editing Sales.")
@RequestMapping("/api/v1/sale")
public class SaleController {

    private final SalesService salesService;

    @Operation(summary = "REST API to Create a new sale. Both ADMIN and USERS can access this API.", tags = "SaleController")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(@RequestBody SalesRequestDTO sale) {
        return new ResponseEntity<>(salesService.createSale(sale), HttpStatus.CREATED);
    }

    @Operation(summary = "REST API to fetch all sales operation. Both ADMIN and USERS can access this API.", tags = "SaleController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    @Operation(summary = "REST API to fetch a sale operation. Both ADMIN and USERS can access this API.", tags = "SaleController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(salesService.getSalesById(id));
    }

    @Operation(summary = "REST API to edit quantities and proces of the sale. Both ADMIN and USERS can access this API.", tags = "SaleController")
    @ApiResponse(responseCode = "204", description = "Http Status 200 OK")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{saleId}/transactions")
    public ResponseEntity<SalesResponseDTO> editSaleTransactions(@PathVariable Long saleId, @RequestBody List<TransactionDTO> transactions) {
        return ResponseEntity.ok(salesService.editSaleTransactions(saleId, transactions));
    }


}
