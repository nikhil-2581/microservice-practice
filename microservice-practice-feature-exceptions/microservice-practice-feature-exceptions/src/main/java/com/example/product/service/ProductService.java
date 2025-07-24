package com.example.product.service;

import com.example.product.model.Product;
import com.example.product.repo.ProductRepository;
import com.example.product.validation.ProductNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private  ProductRepository productRepository;

    @Retryable(
        value = {TransientDataAccessException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    public Product createProduct(@Valid Product product) {
        log.info("Attempting to create product: {}", product);

        if (!product.isAvailable()) {
            log.warn("Product creation failed: product is not available");
            throw new IllegalArgumentException("Product must be marked as available.");
        }

        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product getProductById(String id) {
        log.debug("Fetching product with ID: {}", id);
        return productRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Product not found with ID: {}", id);
                return new ProductNotFoundException("Product not found with ID: " + id);
            });
    }

    public List<Product> getAllProducts() {
        log.debug("Fetching all products");
        return productRepository.findAll();
    }

    public Product updateProduct(String id, @Valid Product updatedProduct) {
        log.info("Updating product with ID: {}", id);
        Product existing = getProductById(id);
        updatedProduct.setId(existing.getId());

        Product updated = productRepository.save(updatedProduct);
        log.info("Product updated successfully: {}", updated);
        return updated;
    }

    public void deleteProduct(String id) {
        log.info("Deleting product with ID: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Product deleted successfully");
    }

    @Recover
    public Product recover(TransientDataAccessException e, Product product) {
        log.error("Database unavailable after retries. Failing operation for product: {}", product, e);
        throw new RuntimeException("Database unavailable. Retry failed.", e);
    }
}
