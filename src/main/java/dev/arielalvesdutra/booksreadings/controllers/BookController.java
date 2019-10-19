package dev.arielalvesdutra.booksreadings.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.arielalvesdutra.booksreadings.controllers.dto.CreateBookDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateBookAuthorsDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Book> create(
			@Valid @RequestBody CreateBookDTO createBookDto, 
			UriComponentsBuilder uriBuilder) {
		
		Book createdBook = this.bookService.create(createBookDto.toBook());
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
	public ResponseEntity<Page<Book>> list(@RequestParam(required = false) String name,
					@PageableDefault(sort="name", page = 0, size = 10) Pageable pagination) {

		Page<Book> booksList = this.bookService.findAll(name, pagination);
		
		return  ResponseEntity.ok().body(booksList);		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Book> retrieveById(@PathVariable Long id) {
		
		Book book = this.bookService.find(id);
		
		return ResponseEntity.ok().body(book);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody Book book) {
		
		Book updatedBook = this.bookService.update(id, book);
		
		return ResponseEntity.ok().body(updatedBook);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/authors")
	public ResponseEntity<List<Author>> retrieveBookAuthors(@PathVariable Long id) {

		Book book = this.bookService.find(id);

		List<Author> bookAuthors = new ArrayList<>(book.getAuthors());

		return ResponseEntity.ok().body(bookAuthors);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/authors")
	public ResponseEntity<?> 
			updateBookAuthors(@PathVariable Long id, @RequestBody UpdateBookAuthorsDTO updateBookAuthorsDto) {

		this.bookService.updateBookAuthors(id, updateBookAuthorsDto);
		
		return ResponseEntity.ok().build();
	}
}
