package com.example.salesmanagementsystem.dto.reporting;

import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SalesReport {
    private int totalSales;
    private double totalRevenue;
    private List<Product> topSellingProducts;
    private List<Client> topPerformingSellers;

}
