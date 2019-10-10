package dev.arielalvesdutra.booksreadings.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRespository;
	
	public Book create(Book book) {
		return this.bookRespository.create(book);
	}
	
	public void deleteById(Long id) {
		this.bookRespository.deleteById(id);
	}

	public Book find(Long id) {
		return this.bookRespository.find(id);
	}

	public List<Book> findAll() {
		return this.bookRespository.findAll();
	}

	public Book update(Long id, Book book) {
		return this.bookRespository.update(id, book);
	}

	public void updateBookAuthors(Long id, Set<Author> authors) {

		
		this.bookRespository.updateBookAuthors(id, authors);
	}
}
