package com.natorigatto.course.resources;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natorigatto.course.dto.ProductDTO;
import com.natorigatto.course.entities.Product;
import com.natorigatto.course.services.ProductService;
import com.natorigatto.course.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {

		List<Product> productList = productService.findAll();

		return ResponseEntity.ok().body(productList);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = productService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody ProductDTO productDTO) {

		try {

			Product product = new Product(null, productDTO.getName(), productDTO.getDescription(),
					productDTO.getPrice(), productDTO.getImgUrl());

			Product savedProduct = productService.insert(product, productDTO.getCategoriesId());

			return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(productDTO.getCategoriesId());
		}
	}

}
