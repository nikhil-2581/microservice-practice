package com.example.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.product.model.Product;
import com.example.product.repo.ProductRepository;
import com.example.product.validation.ProductNotFoundException;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.TransientDataAccessException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct_Success() {
        Product product = new Product();

        when(productRepository.save(product)).thenReturn(product);
        Product saved = productService.createProduct(product);

        assertEquals(product.getName(), saved.getName());
    }

    @Test
    void testCreateProduct_NotAvailable_ThrowsException() {
        Product product = new Product();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(product);
        });

        assertEquals("Product must be marked as available.", exception.getMessage());
    }

    @Test
    void testGetProductById_NotFound_ThrowsException() {
        when(productRepository.findById("invalid")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById("invalid");
        });
    }

    @Test
    void testDeleteProduct_Success() {
        Product product = new Product();
        when(productRepository.findById("prod-3")).thenReturn(Optional.of(product));

        productService.deleteProduct("prod-3");
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testRecoverMethodThrowsRuntimeException() {
        Product product = new Product();
        TransientDataAccessException ex = new TransientDataAccessException("DB down") {};

        Exception thrown = assertThrows(RuntimeException.class, () -> {
            productService.recover(ex, product);
        });

        assertEquals("Database unavailable. Retry failed.", thrown.getMessage());
    }
}

