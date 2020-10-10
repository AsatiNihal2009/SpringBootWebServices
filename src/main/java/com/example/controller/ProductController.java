package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.exception.ProductNotFound;
import com.example.repository.ProductRepository;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductRepository repo;

	@GetMapping
	public List<Product> getAllProducts() {
		return this.repo.findAll();
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable(value = "id") long id) {
		return this.repo.findById(id).orElseThrow(() -> new ProductNotFound("Product Not found With ID" + id));
	}

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return this.repo.save(product);
	}

	@PutMapping("{id}")
	public Product updateProduct(@RequestBody Product product, @PathVariable long id) {
		/*
		 *  1. Find the product
		 *  2.Set New Values
		 *  3.Save the product
		 */
		Product fetchedProduct = this.repo.findById(id)
				.orElseThrow(() -> new ProductNotFound("Product Not found With ID" + id));
		fetchedProduct.setName(product.getName());
		fetchedProduct.setDescription(product.getDescription());
		return this.repo.save(fetchedProduct);
	}
	
	@DeleteMapping("{id}")
	public void deleteProduct(@PathVariable(value = "id") long id) {
		Product fetchedProduct = this.repo.findById(id)
				.orElseThrow(() -> new ProductNotFound("Product Not found With ID" + id));
		this.repo.delete(fetchedProduct);
	}
}
