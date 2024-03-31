package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.clients.ClientResponseDTO;
import com.example.salesmanagementsystem.dto.reporting.ClientReport;
import com.example.salesmanagementsystem.dto.reporting.DateRangeRequest;
import com.example.salesmanagementsystem.dto.reporting.ProductReport;
import com.example.salesmanagementsystem.dto.reporting.SalesReport;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.model.Sale;
import com.example.salesmanagementsystem.model.Transaction;
import com.example.salesmanagementsystem.repository.ClientRepository;
import com.example.salesmanagementsystem.repository.ProductRepository;
import com.example.salesmanagementsystem.repository.SaleRepository;
import com.example.salesmanagementsystem.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public SalesReport generateSalesReport(DateRangeRequest dateRangeRequest) {
        List<Sale> sales = saleRepository.findByCreatedAtBetween(dateRangeRequest.getStartDate(), dateRangeRequest.getEndDate());

        int totalSales = sales.size();
        BigDecimal totalRevenue = sales.stream()
            .map(Sale::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Product> topSellingProducts = sales.stream()
            .flatMap(sale -> sale.getTransactions().stream())
            .collect(Collectors.groupingBy(Transaction::getProduct, Collectors.summingInt(Transaction::getQuantity)))
            .entrySet().stream()
            .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        List<Client> topPerformingSellers = sales.stream()
            .collect(Collectors.groupingBy(Sale::getClient, Collectors.summingDouble(sale -> sale.getTotal().doubleValue())))
            .entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        return SalesReport.builder()
            .totalSales(totalSales)
            .totalRevenue(totalRevenue)
            .topSellingProducts(topSellingProducts)
            .topPerformingSellers(topPerformingSellers)
            .build();
    }

    @Override
    public ClientReport generateClientReport() {
        List<Client> clients = clientRepository.findAll();

        int totalClients = clients.size();

        List<ClientResponseDTO> topSpendingClients = clients.stream()
            .sorted(Comparator.comparing(client -> client.getTotalSpent(), Comparator.reverseOrder()))
            .limit(5)
            .map(client -> mapToClientResponseDTO(client)) // Assuming you have a method to map Client to ClientResponseDTO
            .collect(Collectors.toList());


        int totalClientActivity = calculateTotalClientActivity(clients);

        String topClientLocation = determineTopClientLocation(clients);

        return ClientReport.builder()
            .totalClients(totalClients)
            .topSpendingClients(topSpendingClients)
            .totalClientActivity(totalClientActivity)
            .topClientLocation(topClientLocation)
            .build();
    }

    @Override
    public ProductReport generateProductReport() {
        List<Product> products = productRepository.findAll();

        int totalProducts = products.size();

        long totalAvailableProducts = products.stream()
            .filter(product -> product.getQuantity() > 0)
            .count();

        Map<Product, Long> productSalesMap = products.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                product -> (long) product.getSales().size()
            ));

        List<Product> topSellingProducts = productSalesMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        int totalSalesPerformance = productSalesMap.values().stream()
            .mapToInt(Long::intValue)
            .sum();

        BigDecimal averagePrice = products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(products.size()), 2, RoundingMode.HALF_UP);

        List<Product> lowPricedProducts = products.stream()
            .filter(product -> product.getPrice().compareTo(averagePrice) < 0)
            .collect(Collectors.toList());

        return ProductReport.builder()
            .totalProducts(totalProducts)
            .totalAvailableProducts((int) totalAvailableProducts)
            .topSellingProducts(topSellingProducts)
            .totalSalesPerformance(totalSalesPerformance)
            .averagePrice(averagePrice)
            .lowPricedProducts(lowPricedProducts)
            .build();
    }

    private int calculateTotalClientActivity(List<Client> clients) {
        return clients.stream()
            .mapToInt(Client::getClientActivity)
            .sum();
    }

    private String determineTopClientLocation(List<Client> clients) {
        Map<String, Long> locationCount = clients.stream()
            .collect(Collectors.groupingBy(Client::getAddress, Collectors.counting()));

        return locationCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Unknown");
    }

    private ClientResponseDTO mapToClientResponseDTO(Client client) {
        return mapper.map(client, ClientResponseDTO.class);
    }
}
