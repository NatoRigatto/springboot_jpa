package com.natorigatto.course.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natorigatto.course.entities.AuthenticationDTO;
import com.natorigatto.course.entities.LoginResponseDTO;
import com.natorigatto.course.entities.RegisterDTO;
import com.natorigatto.course.entities.User;
import com.natorigatto.course.infra.security.TokenService;
import com.natorigatto.course.repositories.UserRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationResource {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
		
		if(this.userRepository.findByLogin(data.login()) != null) {
			
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(null, data.name(), data.email(), data.phone(), data.login(), encryptedPassword, data.userRole());
		
		this.userRepository.save(newUser);
		
		return ResponseEntity.ok().build();
	}
}
