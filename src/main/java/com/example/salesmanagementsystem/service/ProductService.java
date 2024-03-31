package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.products.ProductRequestDTO;
import com.example.salesmanagementsystem.dto.products.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO product);
    List<ProductResponseDTO> getProducts();
    ProductResponseDTO getProduct(Long id);
    ProductResponseDTO updateProduct(ProductRequestDTO product, Long id);
    String deleteProduct(Long id);
}
