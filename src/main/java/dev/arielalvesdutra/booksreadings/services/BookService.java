package dev.arielalvesdutra.booksreadings.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateBookAuthorsDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookRepository bookRepository;	
	
	public Book create(Book book) {
		return this.bookRepository.save(book);
	}
	
	public void deleteById(Long id) {
		this.bookRepository.deleteById(id);
	}

	public Book find(Long id) {
		return this.bookRepository.findById(id).get();
	}

	public List<Book> findAll() {
		return this.bookRepository.findAll();
	}

	public Book update(Long id, Book parameterBook) {
		Book existingBook = this.find(id);
		
		existingBook.setName(parameterBook.getName());
		existingBook.setPublicationYear(parameterBook.getPublicationYear());
		this.bookRepository.save(existingBook);
		
		return existingBook;
	}

	public void updateBookAuthors(Long id, UpdateBookAuthorsDTO updateBookAuthorsDto) {
		Book book = this.find(id);
	
		List<Author> authors = 
				this.authorService.findAllByIds(updateBookAuthorsDto.getAuthorsIds());
		
		book.setAuthors(new HashSet<>(authors));
		
		this.bookRepository.save(book);
	}
}
