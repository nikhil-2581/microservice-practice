package com.example.product.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.product.model.Product;

@Repository
public interface ProductRepository {

	List<Product> findByCategory(String category);
    List<Product> findByAvailable(Boolean available);
    List<Product> findByCategoryAndAvailable(String category, Boolean available);
}
