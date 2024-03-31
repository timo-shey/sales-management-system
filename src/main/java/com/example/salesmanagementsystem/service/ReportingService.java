package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.reporting.ClientReport;
import com.example.salesmanagementsystem.dto.reporting.ProductReport;
import com.example.salesmanagementsystem.dto.reporting.SalesReport;

import java.time.LocalDateTime;

public interface ReportingService {
    SalesReport generateSalesReport(LocalDateTime startDate, LocalDateTime endDate);
    ClientReport generateClientReport();
    ProductReport generateProductReport();
}
