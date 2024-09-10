package com.natorigatto.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natorigatto.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
