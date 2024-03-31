package com.example.salesmanagementsystem.dto.reporting;

import com.example.salesmanagementsystem.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductReport {
    private int totalProducts;
    private int totalAvailableProducts;
    private List<Product> topSellingProducts;
    private int totalSalesPerformance;
    private double averagePrice;
    private List<Product> lowPricedProducts;
}
