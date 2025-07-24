package com.example.product.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String>{

	private static final List<String> VALID_CATEGORIES = Arrays.asList(
	        "Electronics", "Books", "Clothing", "Home"
	    );
	    
	    @Override
	    public void initialize(ValidCategory constraintAnnotation) {
	        // No initialization needed
	    }
	    
	    @Override
	    public boolean isValid(String category, ConstraintValidatorContext context) {
	        if (category == null || category.trim().isEmpty()) {
	            return false;
	        }
	        return VALID_CATEGORIES.contains(category.trim());
	    }
	
}
