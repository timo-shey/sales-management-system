package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.products.ProductRequestDTO;
import com.example.salesmanagementsystem.dto.products.ProductResponseDTO;
import com.example.salesmanagementsystem.exception.ResourceNotFoundException;
import com.example.salesmanagementsystem.model.AuditLog;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.repository.AuditLogRepository;
import com.example.salesmanagementsystem.repository.ProductRepository;
import com.example.salesmanagementsystem.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO product) {
        Product newProduct = mapToEntity(product);
        Product savedProduct = productRepository.save(newProduct);
        LOGGER.info(String.format("Product created --> ID = %d, Name = %s, Description = %s, Category = %s, Quantity = %d, Price = %f",
            savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(),
            savedProduct.getCategory(), savedProduct.getQuantity(), savedProduct.getPrice()));
        logAuditTrail("Product created", savedProduct);
        return mapToDto(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product", "id", id)
        );

        LOGGER.info(String.format("Product retrieved --> ID = %d, Name = %s, Description = %s, Category = %s, Quantity = %d, Price = %f",
            product.getId(), product.getName(), product.getDescription(),
            product.getCategory(), product.getQuantity(), product.getPrice()));
        logAuditTrail("Product retrieved", product);

        return mapToDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(ProductRequestDTO product, Long id) {
        Product getProduct = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product", "id", id)
        );

        Product oldProduct = getProduct.clone();

        getProduct.setName(product.getName());
        getProduct.setDescription(product.getDescription());
        getProduct.setCategory(product.getCategory());
        getProduct.setQuantity(product.getQuantity());
        getProduct.setPrice(product.getPrice());

        Product updatedProduct = productRepository.save(getProduct);

        LOGGER.info(String.format("Product updated --> ID = %d, Name = %s, Description = %s, Category = %s, Quantity = %d, Price = %f",
            updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(),
            updatedProduct.getCategory(), updatedProduct.getQuantity(), updatedProduct.getPrice()));
        logAuditTrail(oldProduct, updatedProduct);

        return mapToDto(updatedProduct);
    }

    @Override
    @Transactional
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product", "id", id)
        );
        productRepository.delete(product);
        logAuditTrail("Product deleted", product);
        return "Product deleted successfully.";
    }

    private Product mapToEntity(ProductRequestDTO productDTO) {
        return mapper.map(productDTO, Product.class);
    }

    private ProductResponseDTO mapToDto(Product product) {
        return mapper.map(product, ProductResponseDTO.class);
    }
    private void logAuditTrail(Product oldProduct, Product newProduct) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction("Product updated");
        auditLog.setEntityType("Product");
        auditLog.setEntityId(newProduct.getId());
        auditLog.setOldValue(oldProduct.toString());
        auditLog.setNewValue(newProduct.toString());
        auditLog.setTimestamp(new Date());
        auditLogRepository.save(auditLog);
    }

    private void logAuditTrail(String action, Product product) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityType("Product");
        auditLog.setEntityId(product.getId());
        auditLog.setNewValue(product.toString());
        auditLog.setTimestamp(new Date());
        auditLogRepository.save(auditLog);
    }
}
