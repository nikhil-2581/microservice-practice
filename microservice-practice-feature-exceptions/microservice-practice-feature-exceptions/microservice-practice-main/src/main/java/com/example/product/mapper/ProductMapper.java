package com.example.product.mapper;

import com.example.product.entity.Product;
import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;

public class ProductMapper {

    public static Product mapRequestToEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setAvailable(dto.getAvailable());
        return product;
    }

        public static ProductResponseDTO mapEntityToResponse(Product product) {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setPrice(product.getPrice());
            dto.setCategory(product.getCategory());
            dto.setAvailable(product.getAvailable());
            dto.setCreatedAt(product.getCreatedAt());
            dto.setUpdatedAt(product.getUpdatedAt());
            return dto;
    }
}
