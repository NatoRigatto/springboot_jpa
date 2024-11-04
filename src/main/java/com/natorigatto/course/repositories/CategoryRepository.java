package com.natorigatto.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natorigatto.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
