package dev.arielalvesdutra.booksreadings.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateBookAuthorsDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;
import dev.arielalvesdutra.booksreadings.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookRepository bookRepository;	
	
	public Book create(Book book) {
		
		if (book.hasAuthors()) {
			book = this.bookWithValidatedAuthors(book);
		}
		
		return this.bookRepository.save(book);
	}
	
	public void deleteById(Long id) {
		Book book = this.find(id);
		
		this.bookRepository.delete(book);
	}

	public Book find(Long id) {
		return this.bookRepository
						.findById(id)
						.orElseThrow(() -> 
						new EntityNotFoundException("Livro com ID "+ id +" não encontrado"));
	}

	public List<Book> findAll() {
		return this.bookRepository.findAll();
	}

	public List<Book> findAll(String name) {
		Example<Book> bookExample = this.getExampleToFindContaingNameCaseInsensitive(name);

		return this.bookRepository.findAll(bookExample);
	}

	public Page<Book> findAll(String name, Pageable pagination) {
		Example<Book> bookExample = this.getExampleToFindContaingNameCaseInsensitive(name);

		Page<Book> bookPage = this.bookRepository.findAll(bookExample, pagination);

		return bookPage;
	}
	

	public Page<Author> findAllAuthorsByBookId(Long bookId, Pageable pagination) {
		Book book = this.find(bookId);
		
		Page<Author> authorsPage = this.authorService.findAllByBookId(book.getId(), pagination);
		
		return authorsPage;
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
	
	private Book bookWithValidatedAuthors(Book parameterBook) throws EntityNotFoundException {
		try {
			Set<Author> validatedAuthors = new HashSet<Author>();
			Book book = parameterBook.clone();
			
			for (Author author: book.getAuthors()) {
				validatedAuthors.add(this.authorService.find(author.getId()));
			}
			
			book.setAuthors(validatedAuthors);
			
			return book;
			
		} catch (CloneNotSupportedException e) {		
			throw new RuntimeException(e.getMessage());
		}
	}

	private Example<Book> getExampleToFindContaingNameCaseInsensitive(String name) {
		Book book = new Book();
		book.setName(name);
		Example<Book> bookExample = Example.of(book, ExampleMatcher.matchingAny()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

		return bookExample;
	}
}
