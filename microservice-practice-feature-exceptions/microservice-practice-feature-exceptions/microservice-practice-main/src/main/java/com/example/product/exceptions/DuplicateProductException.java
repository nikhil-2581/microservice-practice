package com.example.product.exceptions;

public class DuplicateProductException extends RuntimeException{

	public DuplicateProductException(String message) {
        super(message);
    }
	
}
