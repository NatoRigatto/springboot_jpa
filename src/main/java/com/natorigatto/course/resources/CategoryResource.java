package com.natorigatto.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.natorigatto.course.entities.Category;
import com.natorigatto.course.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {

		List<Category> categories = categoryService.findAll();
		return ResponseEntity.ok().body(categories);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		
		Category category = categoryService.findById(id);
		return ResponseEntity.ok().body(category);
	}

	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category) {

		Category savedCategory = categoryService.insert(category);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("{id}")
				.buildAndExpand(category.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(savedCategory);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category updatedCategory) {

		Category existingCategory = categoryService.update(id, updatedCategory);
		return ResponseEntity.ok().body(existingCategory);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
