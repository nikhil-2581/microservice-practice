package com.example.product.exceptions;

public class ProductNotFoundException extends RuntimeException{
	public ProductNotFoundException(String message) {
        super(message);
    }
}
