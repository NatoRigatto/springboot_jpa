package com.natorigatto.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.natorigatto.course.entities.User;
import com.natorigatto.course.repositories.UserRepository;
import com.natorigatto.course.services.exceptions.DatabaseException;
import com.natorigatto.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		
		return userRepository.findAll();
	}

	public User findById(Long id) {
		
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User user) {
		
		return userRepository.save(user);
	}

	public User update(Long id, User updatedUser) {

		try {

			User existingUser = userRepository.getReferenceById(id);
			
			existingUser.setName(updatedUser.getName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPhone(updatedUser.getPhone());
			
			return userRepository.save(existingUser);

		} catch (EntityNotFoundException e) {
			
			throw new ResourceNotFoundException(id);
		}

	}
	
	public void delete(Long id) {

		userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		try {

			userRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			
			throw new DatabaseException(e.getMessage());
		}
	}

}
