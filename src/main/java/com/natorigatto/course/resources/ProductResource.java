package com.natorigatto.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natorigatto.course.entities.Product;
import com.natorigatto.course.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {

		List<Product> products = productService.findAll();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		
		Product product = productService.findById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody Product product) {

		Product savedProduct = productService.insert(product);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updatedProduct) {

		Product existingProduct = productService.update(id, updatedProduct);
		return new ResponseEntity<>(existingProduct, HttpStatus.OK);
	}

}
