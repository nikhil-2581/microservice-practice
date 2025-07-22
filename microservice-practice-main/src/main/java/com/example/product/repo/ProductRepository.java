package com.example.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	List<Product> findByCategory(String category);
    List<Product> findByAvailable(Boolean available);
    List<Product> findByCategoryAndAvailable(String category, Boolean available);
}
