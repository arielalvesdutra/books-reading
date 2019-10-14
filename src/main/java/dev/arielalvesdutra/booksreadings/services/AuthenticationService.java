package dev.arielalvesdutra.booksreadings.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
				
		Optional<User> user = this.userRepository.findByEmail(userEmail); 

		if (user.isPresent()) {
			return user.get();
		}
		
		throw new UsernameNotFoundException("Dado de autenticação inválidos!");
	}

}
