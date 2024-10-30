package com.natorigatto.course.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.natorigatto.course.entities.Category;
import com.natorigatto.course.entities.Product;
import com.natorigatto.course.repositories.CategoryRepository;
import com.natorigatto.course.repositories.ProductRepository;
import com.natorigatto.course.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Product> findAll() {
		
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		
		Optional<Product> product = productRepository.findById(id);
		return product.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Product insert(Product product) {

		// Busca uma(ou mais) Categoria(s) e retorna uma excessão se não encontrá-la(s)
		Set<Category> categories = product.getCategories().stream()
				.map(category -> categoryRepository
						.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException(category.getId())))
				.collect(Collectors.toSet());

		// Associa a(s) Categoria(s) encontrada(s)
		product.setCategories(categories);

		return productRepository.save(product);

	}

	public Product update(Long id, Product updatedProduct) {

		Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setPrice(updatedProduct.getPrice());
		existingProduct.setImgUrl(updatedProduct.getImgUrl());

		Set<Category> categories = updatedProduct.getCategories().stream()
				.map(category -> categoryRepository
						.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException(id)))
				.collect(Collectors.toSet());

		existingProduct.setCategories(categories);

		return productRepository.save(existingProduct);
	}

}
