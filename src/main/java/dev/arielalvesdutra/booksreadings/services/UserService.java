package dev.arielalvesdutra.booksreadings.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User create(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return this.userRepository.save(user);
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public Page<User> findAll(Pageable pagination) {
		return this.userRepository.findAll(pagination);
	}
}
