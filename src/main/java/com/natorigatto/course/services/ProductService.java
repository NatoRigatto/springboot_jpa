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
		Optional<Product> obj = productRepository.findById(id);
		return obj.get();
	}
	
	public Product insert(Product product, Set<Long> categoriesId) {
		
		// Busca em uma coleção de Categorias e retorna uma excessão se não encontrar o ID
		Set<Category> categories = categoriesId.stream()
				.map(id -> categoryRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(categoriesId)))
				.collect(Collectors.toSet());
					
		product.setCategories(categories);
		
		return productRepository.save(product);
	}

}
