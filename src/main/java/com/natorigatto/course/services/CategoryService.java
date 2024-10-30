package com.natorigatto.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.natorigatto.course.entities.Category;
import com.natorigatto.course.repositories.CategoryRepository;
import com.natorigatto.course.services.exceptions.DatabaseException;
import com.natorigatto.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		
		return categoryRepository.findAll();
	}

	public Category findById(Long id) {
		
		Optional<Category> obj = categoryRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Category insert(Category category) {
		
		return categoryRepository.save(category);
	}

	public Category update(Long id, Category updatedCategory) {

		try {

			Category existingCategory = categoryRepository.getReferenceById(id);
			
			existingCategory.setName(updatedCategory.getName());
			
			return categoryRepository.save(existingCategory);

		} catch (EntityNotFoundException e) {
			
			throw new ResourceNotFoundException(id);
		}

	}
	
	public void delete(Long id) {

		categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		try {

			categoryRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			
			throw new DatabaseException(e.getMessage());
		}

	}

}
