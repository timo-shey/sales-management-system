package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.reporting.ClientReport;
import com.example.salesmanagementsystem.dto.reporting.DateRangeRequest;
import com.example.salesmanagementsystem.dto.reporting.ProductReport;
import com.example.salesmanagementsystem.dto.reporting.SalesReport;
import com.example.salesmanagementsystem.service.ReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "ReportingController", description = "APIs for Generating sales, client and product reports.")
@RequestMapping("api/v1/reporting")
public class ReportingController {

    private final ReportingService reportingService;

    @Operation(summary = "REST API to Generate sales report. Only ADMIN can access this API.", tags = "ReportingController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/sales_report")
    public ResponseEntity<SalesReport> generateSalesReport(@RequestBody DateRangeRequest dateRange) {
        return ResponseEntity.ok(reportingService.generateSalesReport(dateRange));
    }

    @Operation(summary = "REST API to Generate client report. Only ADMIN can access this API.", tags = "ReportingController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/client_report")
    public ResponseEntity<ClientReport> generateClientReport() {
        return ResponseEntity.ok(reportingService.generateClientReport());
    }

    @Operation(summary = "REST API to Generate product report. Only ADMIN can access this API.", tags = "ReportingController")
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/product_report")
    public ResponseEntity<ProductReport> generateProductReport() {
        return ResponseEntity.ok(reportingService.generateProductReport());
    }
}

