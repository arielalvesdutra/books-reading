package dev.arielalvesdutra.booksreadings.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.arielalvesdutra.booksreadings.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
}
