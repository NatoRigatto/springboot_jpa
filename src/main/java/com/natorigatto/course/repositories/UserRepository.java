package com.natorigatto.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.natorigatto.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	UserDetails findByLogin(String login);
}
