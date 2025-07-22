package com.example.product.service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = ProductMapper.mapRequestToEntity(requestDTO);
        //product.setProductId(UUID.randomUUID().toString());  // generate unique ID
        Product saved = productRepository.save(product);
        System.out.println("Saved productId: " + saved.getProductId());
        return ProductMapper.mapEntityToResponse(saved);
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(String.valueOf(productId))
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return ProductMapper.mapEntityToResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(String.valueOf(productId))
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        product.setProductName(requestDTO.getProductName());
        product.setPrice(requestDTO.getPrice());
        product.setCategory(requestDTO.getCategory());
        product.setAvailable(requestDTO.getAvailable());

        Product updated = productRepository.save(product);
        return ProductMapper.mapEntityToResponse(updated);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(String.valueOf(productId))) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(String.valueOf(productId));
    }
}
