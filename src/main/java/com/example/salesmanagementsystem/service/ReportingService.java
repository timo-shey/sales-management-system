package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.reporting.ClientReport;
import com.example.salesmanagementsystem.dto.reporting.DateRangeRequest;
import com.example.salesmanagementsystem.dto.reporting.ProductReport;
import com.example.salesmanagementsystem.dto.reporting.SalesReport;

import java.time.LocalDateTime;

public interface ReportingService {
    SalesReport generateSalesReport(DateRangeRequest dateRangeRequest);
    ClientReport generateClientReport();
    ProductReport generateProductReport();
}
