package com.natorigatto.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natorigatto.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	

}
