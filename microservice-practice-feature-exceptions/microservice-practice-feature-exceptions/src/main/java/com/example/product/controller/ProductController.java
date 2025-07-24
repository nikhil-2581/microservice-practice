package com.example.product.controller;

import com.example.product.model.Product;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        log.info("Received request to create product: {}", product);
        Product created = productService.createProduct(product);
        log.info("Product created successfully: {}", created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ✅ Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        log.info("Fetching product with ID: {}", id);
        Product product = productService.getProductById(id);
        log.info("Product retrieved: {}", product);
        return ResponseEntity.ok(product);
    }

    // ✅ Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productService.getAllProducts();
        log.info("Total products found: {}", products.size());
        return ResponseEntity.ok(products);
    }

    // ✅ Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
        log.info("Received request to update product with ID: {}", id);
        Product updated = productService.updateProduct(id, product);
        log.info("Product updated successfully: {}", updated);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        log.info("Received request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        log.info("Product deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
