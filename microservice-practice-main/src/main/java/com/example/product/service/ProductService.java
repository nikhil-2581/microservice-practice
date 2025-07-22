package com.example.product.service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO getProductById(Long productId);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO requestDTO);
    void deleteProduct(Long productId);
}
