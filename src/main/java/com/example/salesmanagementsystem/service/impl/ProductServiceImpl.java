package com.example.salesmanagementsystem.service.impl;

import com.example.salesmanagementsystem.dto.ProductRequestDTO;
import com.example.salesmanagementsystem.dto.ProductResponseDTO;
import com.example.salesmanagementsystem.exception.ResourceNotFoundException;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.repository.ProductRepository;
import com.example.salesmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO product) {
        Product newProduct = mapToEntity(product);
        productRepository.save(newProduct);
        return mapToDto(newProduct);
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
        return mapToDto(product);
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO product, Long id) {
        Product getProduct = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product", "id", id)
        );

        getProduct.setName(product.getName());
        getProduct.setDescription(product.getDescription());
        getProduct.setCategory(product.getCategory());
        getProduct.setQuantity(product.getQuantity());
        getProduct.setPrice(product.getPrice());

        Product updatedProduct = productRepository.save(getProduct);
        return mapToDto(updatedProduct);
    }

    @Override
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product", "id", id)
        );
        productRepository.delete(product);
        return "Product deleted successfully.";
    }

    private Product mapToEntity(ProductRequestDTO productDTO) {
        return mapper.map(productDTO, Product.class);
    }

    private ProductResponseDTO mapToDto(Product product) {
        return mapper.map(product, ProductResponseDTO.class);
    }
}
