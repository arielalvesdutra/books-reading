package dev.arielalvesdutra.booksreadings.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Book> create(@RequestBody Book book, UriComponentsBuilder uriBuilder) {
		
		Book createdBook = this.bookService.create(book);
		URI uri = uriBuilder.path("/books/{id}")
				.buildAndExpand(createdBook.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(createdBook);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		this.bookService.deleteById(id);

		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Book>> list() {
		List<Book> booksList = this.bookService.findAll();
		
		return  ResponseEntity.ok().body(booksList);		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Book> retrieveById(@PathVariable Long id) {
		
		Book book = this.bookService.find(id);
		
		return ResponseEntity.ok().body(book);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
		
		Book updatedBook = this.bookService.update(id, book);
		
		return ResponseEntity.ok().body(updatedBook);
	}
}
