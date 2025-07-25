package com.example.product.service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRecoveryService {

    private final Logger logger = LoggerFactory.getLogger(ProductRecoveryService.class);

    public ProductResponseDTO recoverCreateProduct(TransientDataAccessException e, ProductRequestDTO requestDTO) {
        logger.error("All retry attempts failed for saving product '{}'. Reason: {}", requestDTO.getProductName(), e.getMessage());

        ProductResponseDTO fallback = new ProductResponseDTO();
        fallback.setProductName(requestDTO.getProductName());
        fallback.setAvailable(false);
        fallback.setPrice(BigDecimal.ZERO);
        fallback.setCategory("Unavailable");
        fallback.setCreatedAt(null);
        fallback.setUpdatedAt(null);

        logger.info("Returning fallback response for product '{}'", requestDTO.getProductName());
        return fallback;
    }

    public ProductResponseDTO recoverGetProductById(TransientDataAccessException e, Long productId) {
        logger.error("All retry attempts failed for getProductById with ID {}. Reason: {}", productId, e.getMessage());

        // Return a fallback DTO indicating failure
        ProductResponseDTO fallback = new ProductResponseDTO();
        fallback.setProductId(productId);
        fallback.setCategory("Unavailable");
        fallback.setAvailable(false);
        fallback.setProductName("Unknown");
        fallback.setPrice(BigDecimal.ZERO);
        return fallback;
    }

    public
    List<ProductResponseDTO> recoverGetAllProducts(TransientDataAccessException e) {
        logger.error("All retry attempts failed for getAllProducts. Reason: {}", e.getMessage());
        // Return empty list as fallback
        return Collections.emptyList();
    }

    public ProductResponseDTO recoverUpdateProduct(TransientDataAccessException e, Long productId, ProductRequestDTO requestDTO) {
        logger.error("All retry attempts failed for updateProduct with ID {}. Reason: {}", productId, e.getMessage());

        ProductResponseDTO fallback = new ProductResponseDTO();
        fallback.setProductId(productId);
        fallback.setCategory("Unavailable");
        fallback.setAvailable(false);
        fallback.setProductName(requestDTO.getProductName());
        fallback.setPrice(BigDecimal.ZERO);
        return fallback;
    }

    public void recoverDeleteProduct(TransientDataAccessException e, Long productId) {
        logger.error("All retry attempts failed for deleteProduct with ID {}. Reason: {}", productId, e.getMessage());
    }
}

