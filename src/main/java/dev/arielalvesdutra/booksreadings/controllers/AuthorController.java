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

import dev.arielalvesdutra.booksreadings.controllers.dto.CreateAuthorDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.services.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Author> create(
			@Valid @RequestBody CreateAuthorDTO createAuthorDto, 
			UriComponentsBuilder uriBuilder) {
		
		Author createdAuthor = this.authorService.create(createAuthorDto.toAuthor());
		
		URI uri = uriBuilder.path("/authors/{id}")
							.buildAndExpand(createdAuthor.getId())
							.toUri();
		
		return ResponseEntity.created(uri).body(createdAuthor);		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		
		this.authorService.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Author>> list(@RequestParam(required = false) String name,
					@PageableDefault(sort="name", page = 0, size = 10) Pageable pagination) {
		
		Page<Author> authorsList = this.authorService.findAll(name, pagination);
		
		return ResponseEntity.ok().body(authorsList);		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Author> retrieveById(@PathVariable Long id) {
		
		Author author = this.authorService.find(id);
		
		return ResponseEntity.ok().body(author);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Author> update(@PathVariable Long id, @Valid @RequestBody Author author) {

		Author updatedAuthor = this.authorService.update(id, author);
		
		return ResponseEntity.ok().body(updatedAuthor);
	}
}
