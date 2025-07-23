package com.example.product.exceptions;

public class ProductPersistenceException extends RuntimeException {

    public ProductPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
