

package com.example.product.service;

import com.example.product.service.Exception.ProductNotFoundException;
import com.example.product.service.Model.Product;
import com.example.product.service.Repo.ProductRepository;
import com.example.product.service.Service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.TransientDataAccessException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = new Product();
        sampleProduct.setId("101");
        sampleProduct.setName("iPhone");
        sampleProduct.setCategory("Electronics");
        sampleProduct.setPrice(799.99);
        sampleProduct.setAvailability(true);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);
        Product created = productService.createProduct(sampleProduct);
        assertEquals("iPhone", created.getName());
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    void testGetProductByIdFound() {
        when(productRepository.findById("101")).thenReturn(Optional.of(sampleProduct));
        Product result = productService.getProductById("101");
        assertEquals("iPhone", result.getName());
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById("999"));
    }

    @Test
    void testGetAllProducts() {
        List<Product> list = Arrays.asList(sampleProduct);
        when(productRepository.findAll()).thenReturn(list);
        List<Product> result = productService.getAllProducts();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateProductSuccess() {
        Product updated = new Product();
        updated.setName("iPhone Pro");
        updated.setCategory("Electronics");
        updated.setPrice(999.99);
        updated.setAvailability(false);

        when(productRepository.findById("101")).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product result = productService.updateProduct("101", updated);
        assertEquals("iPhone Pro", result.getName());
        assertFalse(result.isAvailability());
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById("404")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                     () -> productService.updateProduct("404", sampleProduct));
    }

    @Test
    void testDeleteProductSuccess() {
        when(productRepository.existsById("101")).thenReturn(true);
        doNothing().when(productRepository).deleteById("101");
        productService.deleteProduct("101");
        verify(productRepository).deleteById("101");
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.existsById("404")).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("404"));
    }

    @Test
    void testRecoverFromFailure() {
        TransientDataAccessException ex = new TransientDataAccessException("Retry failed") {};
        Product result = productService.recoverFromFailure(ex, sampleProduct);
        assertNull(result);
    }
}
