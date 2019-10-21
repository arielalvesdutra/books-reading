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
import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveAuthorDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateAuthorDTO;
import dev.arielalvesdutra.booksreadings.entities.Author;
import dev.arielalvesdutra.booksreadings.services.AuthorService;
import io.swagger.annotations.Api;

@Api(tags = "Author", description = "Author Resource")
@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<RetrieveAuthorDTO> create(
			@Valid @RequestBody CreateAuthorDTO createAuthorDto, 
			UriComponentsBuilder uriBuilder) {
		
		Author createdAuthor = this.authorService.create(createAuthorDto.toAuthor());
		
		URI uri = uriBuilder.path("/authors/{id}")
							.buildAndExpand(createdAuthor.getId())
							.toUri();
		
		return ResponseEntity.created(uri).body(new RetrieveAuthorDTO(createdAuthor));		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{authorId}")
	public ResponseEntity<?> deleteById(@PathVariable Long authorId) {
		
		this.authorService.deleteById(authorId);
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<RetrieveAuthorDTO>> list(
			@RequestParam(required = false) String name,
			@PageableDefault(sort="name", page = 0, size = 10) Pageable pagination) {
		
		Page<Author> authorsPage = this.authorService.findAll(name, pagination);		
		
		return ResponseEntity.ok().body(
				RetrieveAuthorDTO.fromAuthorPageToRetrieveAuthorDTOPage(authorsPage));				
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{authorId}")
	public ResponseEntity<RetrieveAuthorDTO> retrieveById(@PathVariable Long authorId) {
		
		Author author = this.authorService.find(authorId);
		
		return ResponseEntity.ok().body(new RetrieveAuthorDTO(author));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{authorId}")
	public ResponseEntity<RetrieveAuthorDTO> update(
			@PathVariable Long authorId, 
			@Valid @RequestBody UpdateAuthorDTO updateAuthorDto) {

		Author updatedAuthor = this.authorService.update(authorId, updateAuthorDto.toAuthor());
		
		return ResponseEntity.ok().body(new RetrieveAuthorDTO(updatedAuthor));
	}
}
