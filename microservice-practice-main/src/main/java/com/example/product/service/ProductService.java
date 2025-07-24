package com.example.product.service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO getProductById(Long productId);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO requestDTO);
	void deleteProduct(String productId);


}
