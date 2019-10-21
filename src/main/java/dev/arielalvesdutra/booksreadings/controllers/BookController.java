package dev.arielalvesdutra.booksreadings.controllers;

import java.net.URI;

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
import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveAuthorDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveBookDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateBookAuthorsDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateBookDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.entities.Book;
import dev.arielalvesdutra.booksreadings.services.BookService;
import io.swagger.annotations.Api;

@Api(tags = "Book", description = "Book Resource")
@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<RetrieveBookDTO> create(
			@Valid @RequestBody CreateBookDTO createBookDto, 
			UriComponentsBuilder uriBuilder) {
		
		Book createdBook = this.bookService.create(createBookDto.toBook());
		URI uri = uriBuilder.path("/books/{id}")
				.buildAndExpand(createdBook.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new RetrieveBookDTO(createdBook));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
	public ResponseEntity<?> deleteById(@PathVariable Long bookId) {

		this.bookService.deleteById(bookId);

		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<RetrieveBookDTO>> list(@RequestParam(required = false) String name,
					@PageableDefault(sort="name", page = 0, size = 10) Pageable pagination) {

		Page<Book> booksPage = this.bookService.findAll(name, pagination);
		
		return  ResponseEntity.ok()
				.body(RetrieveBookDTO.fromBookPageToRetrieveBookDTOPage(booksPage));		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
	public ResponseEntity<RetrieveBookDTO> retrieveById(@PathVariable Long bookId) {
		
		Book book = this.bookService.find(bookId);
		
		return ResponseEntity.ok().body(new RetrieveBookDTO(book));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{bookId}")
	public ResponseEntity<RetrieveBookDTO> update(
			@PathVariable Long bookId, 
			@Valid @RequestBody UpdateBookDTO UpdateBookDto) {
		
		Book updatedBook = this.bookService.update(bookId, UpdateBookDto.toBook());
		
		return ResponseEntity.ok().body(new RetrieveBookDTO(updatedBook));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{bookId}/authors")
	public ResponseEntity<Page<RetrieveAuthorDTO>> retrieveBookAuthors(
			@PathVariable Long bookId,
			@PageableDefault(sort="name", page = 0, size = 10) Pageable pagination) {
		
		Page<Author> authorsPage = this.bookService.findAllAuthorsByBookId(bookId, pagination);

		return ResponseEntity.ok().body(
			RetrieveAuthorDTO.fromAuthorPageToRetrieveAuthorDTOPage(authorsPage));
		
		
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{bookId}/authors")
	public ResponseEntity<?> 
			updateBookAuthors(
					@PathVariable Long bookId, 
					@RequestBody UpdateBookAuthorsDTO updateBookAuthorsDto) {

		this.bookService.updateBookAuthors(bookId, updateBookAuthorsDto);
		
		return ResponseEntity.ok().build();
	}
}
