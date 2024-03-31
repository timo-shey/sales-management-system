package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.reporting.SalesReport;
import com.example.salesmanagementsystem.service.ReportingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "ReportingController", description = "APIs for Generating sales, client and product reports.")
@RequestMapping("api/v1/reporting")
public class ReportingController {

    private final ReportingService reportingService;

    @PostMapping("/sales_report")
    public ResponseEntity<SalesReport> generateSalesReport(@Re)
}
