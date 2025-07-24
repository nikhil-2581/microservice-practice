package com.example.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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

    @Autowired
    ProductRecoveryService recoveryService;


    @Retryable(
            value = {org.springframework.dao.TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
    	logger.info("Creating product with ID: {}", requestDTO.getProductName());
    	
    	// Check if product already exists
        if (productRepository.existsByProductName(requestDTO.getProductName())) {
            throw new DuplicateProductException("Product with name '" + requestDTO.getProductName() + "' already exists.");
        }
        
        // Validate that product is marked as available
        if (!requestDTO.getAvailable()) {
            throw new IllegalArgumentException("Product must be marked as available to be saved");
        }

        //Checking the retryable function!
       //logger.info("Trying to save product...");
       // Simulate DB failure
        //throw new TransientDataAccessException("Simulated failure") {};
        
        
        Product product = ProductMapper.mapRequestToEntity(requestDTO);
        Product savedProduct = productRepository.save(product);
        logger.info("Product created successfully with ID: {}", savedProduct.getProductId());
        return ProductMapper.mapEntityToResponse(savedProduct);
    }

    @Recover
    public ProductResponseDTO recoverCreateProduct(TransientDataAccessException e, ProductRequestDTO requestDTO) {
        return recoveryService.recoverCreateProduct(e, requestDTO);
    }

    @Retryable(
            value = {TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public ProductResponseDTO getProductById(Long productId) {
        logger.info("Simulating failure for retry test in getProductById...");
        throw new TransientDataAccessException("Simulated DB failure") {};
//    	logger.info("Retrieving product with ID: {}", productId);
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
//        return ProductMapper.mapEntityToResponse(product);
    }

    @Recover
    public ProductResponseDTO recoverGetProductById(TransientDataAccessException e, Long productId) {
        return recoveryService.recoverGetProductById(e, productId);
    }

    @Retryable(
            value = {TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        logger.info("Simulating failure for retry test in getAllProducts...");
        throw new TransientDataAccessException("Simulated DB error") {};
//    	logger.info("Retrieving all products");
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(ProductMapper::mapEntityToResponse)
//                .collect(Collectors.toList());
   }

    @Recover
    public List<ProductResponseDTO> recoverGetAllProducts(TransientDataAccessException e) {
        return recoveryService.recoverGetAllProducts(e);
    }

    @Retryable(
            value = {TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO requestDTO) {
        logger.info("Simulating failure for updateProduct retry test...");
        throw new TransientDataAccessException("Simulated update error") {};
 //   	logger.info("Updating product with ID: {}", productId);
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
//
//        product.setProductName(requestDTO.getProductName());
//        product.setPrice(requestDTO.getPrice());
//        product.setCategory(requestDTO.getCategory());
//        product.setAvailable(requestDTO.getAvailable());
//
//        Product updatedProduct = productRepository.save(product);
//        logger.info("Product updated successfully with ID: {}", updatedProduct.getProductId());
//
//        return ProductMapper.mapEntityToResponse(updatedProduct);
  }

    @Recover
    public ProductResponseDTO recoverUpdateProduct(TransientDataAccessException e, Long productId, ProductRequestDTO requestDTO) {
        return recoveryService.recoverUpdateProduct(e, productId, requestDTO);
    }

    @Retryable(
            value = {TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public void deleteProduct(Long productId) {
        logger.info("Simulating failure for deleteProduct retry test...");
        throw new TransientDataAccessException("Simulated delete failure") {};
//    	logger.info("Deleting product with ID: {}", productId);
//
//        if (!productRepository.existsById(productId)) {
//            throw new ProductNotFoundException("Product not found with ID: " + productId);
//        }
//        productRepository.deleteById(productId);
//        logger.info("Product deleted successfully with ID: {}", productId);
    }

    @Recover
    public void recoverDeleteProduct(TransientDataAccessException e, Long productId) {
        recoveryService.recoverDeleteProduct(e, productId);
    }
}
