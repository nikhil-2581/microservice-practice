package com.example.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;
import com.example.product.entity.Product;
import com.example.product.exceptions.DuplicateProductException;
import com.example.product.exceptions.ProductNotFoundException;
import com.example.product.mapper.ProductMapper;
import com.example.product.repo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
    	logger.info("Creating product with ID: {}", requestDTO.getProductName());
    	
    	// Check if product already exists
        if (productRepository.existsById(requestDTO.getProductName())) {
            throw new DuplicateProductException("Product with name " + requestDTO.getProductName() + " already exists");
        }
        
        // Validate that product is marked as available
        if (!requestDTO.getAvailable()) {
            throw new IllegalArgumentException("Product must be marked as available to be saved");
        }
        
        
        Product product = ProductMapper.mapRequestToEntity(requestDTO);
        //product.setProductId(UUID.randomUUID().toString());  // generate unique ID
        Product savedProduct = productRepository.save(product);
        logger.info("Product created successfully with ID: {}", savedProduct.getProductId());
        return ProductMapper.mapEntityToResponse(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(String productId) {
    	logger.info("Retrieving product with ID: {}", productId);
        Product product = productRepository.findById((productId))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        return ProductMapper.mapEntityToResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
    	logger.info("Retrieving all products");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(String productId, ProductRequestDTO requestDTO) {
    	logger.info("Updating product with ID: {}", productId);
        Product product = productRepository.findById((productId))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        product.setProductName(requestDTO.getProductName());
        product.setPrice(requestDTO.getPrice());
        product.setCategory(requestDTO.getCategory());
        product.setAvailable(requestDTO.getAvailable());

        Product updatedProduct = productRepository.save(product);
        logger.info("Product updated successfully with ID: {}", updatedProduct.getProductId());

        return ProductMapper.mapEntityToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
    	logger.info("Deleting product with ID: {}", productId);
    	
        if (!productRepository.existsById((productId))) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById((productId));
        logger.info("Product deleted successfully with ID: {}", productId);
    }
}
