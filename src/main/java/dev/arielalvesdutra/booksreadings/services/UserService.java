package dev.arielalvesdutra.booksreadings.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.entities.BookReading;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.entities.enums.ReadingStatus;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.BookReadingRepository;
import dev.arielalvesdutra.booksreadings.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookReadingRepository bookReadingRepository;
	
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


	public User addBookReading(Long id, BookReading bookReading) {
		User user = this.find(id);
		
		user.addBookReading(bookReading);
		this.userRepository.save(user);
		
		return user;
	}

	public User addBookReading(Long id, Long bookId) {
		Book book = this.bookService.find(bookId);
		BookReading bookReading = new BookReading(book);

		User user = this.addBookReading(id, bookReading);

		return user;
	}

	public User removeBookReading(Long id, BookReading bookReading) {
		User user = this.find(id);
		
		user.removeBookReading(bookReading);
		this.userRepository.save(user);
		
		return user;
	}

	public User removeBookReading(Long userId, Long bookReadingId) {
		BookReading bookReading = this.bookReadingRepository
							.findById(bookReadingId)
							.orElseThrow(() -> 	
							new EntityNotFoundException("Leitura com ID "+ bookReadingId +" não encontrada"));
		
		User user = this.removeBookReading(userId, bookReading);

		return user;
	}

	public BookReading updateBookReadingStatus(Long userId, Long bookReadingId, ReadingStatus readingStatus) {
		BookReading bookReading = this.findBookReading(userId, bookReadingId);

		bookReading.setReadingStatus(readingStatus);
		this.bookReadingRepository.save(bookReading);
		
		return bookReading;
	}
	
	private BookReading findBookReading(Long userId, Long bookReadingId) {
		BookReading bookReading = 
			this.bookReadingRepository.findByIdAndUser_Id(bookReadingId, userId);

		if (bookReading == null) {
			throw new EntityNotFoundException("Leitura de ID " 
							+ bookReadingId + " do usuário de ID "
							+ userId + " Não encontrada");
		}

		return bookReading;
	}
}
