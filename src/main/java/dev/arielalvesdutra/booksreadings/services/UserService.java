package dev.arielalvesdutra.booksreadings.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.entities.BookRead;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.BookReadRepository;
import dev.arielalvesdutra.booksreadings.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookReadRepository bookReadRepository;
	
	public User create(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return this.userRepository.save(user);
	}

	public User find(Long id) {
		return this.userRepository
				.findById(id).
				orElseThrow(() -> 
				new EntityNotFoundException("Usuário com ID "+ id +" não encontrado"));
	}
	
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public Page<User> findAll(Pageable pagination) {
		return this.userRepository.findAll(pagination);
	}

	public User addBookRead(Long id, BookRead bookRead) {
		User user = this.find(id);
		
		user.addBookRead(bookRead);
		this.userRepository.save(user);
		
		return user;
	}

	public User addBookRead(Long id, Long bookId) {
		Book book = this.bookService.find(bookId);
		BookRead bookRead = new BookRead(book);

		User user = this.addBookRead(id, bookRead);

		return user;
	}

	public User removeBookRead(Long id, BookRead bookRead) {
		User user = this.find(id);
		
		user.removeBookRead(bookRead);
		this.userRepository.save(user);
		
		return user;
	}

	public User removeBookRead(Long userId, Long bookReadId) {
		BookRead bookRead = this.bookReadRepository
							.findById(bookReadId)
							.orElseThrow(() -> 	
							new EntityNotFoundException("Leitura com ID "+ bookReadId +" não encontrada"));
		
		User user = this.removeBookRead(userId, bookRead);

		return user;
	}
}
