package com.example.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @NotBlank(message = "Product ID is mandatory")
    private String id;

    @NotBlank(message = "Product name must not be empty")
    private String name;

    @Min(value = 1, message = "Price must be greater than zero")
    private double price;

    @NotBlank(message = "Category must not be empty")
    @Pattern(regexp = "Electronics|Books|Clothing|Home", message = "Category must be one of Electronics, Books, Clothing, Home")
    private String category;

    private boolean available;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
    
}
