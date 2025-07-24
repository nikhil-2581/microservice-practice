package com.example.product.service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO getProductById(String productId);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(String productId, ProductRequestDTO requestDTO);
	void deleteProduct(String productId);
}
