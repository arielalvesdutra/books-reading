package dev.arielalvesdutra.booksreadings.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.arielalvesdutra.booksreadings.controllers.dto.CreateBookReadingDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.CreateUserDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveBookReadingDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.RetrieveUserDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateBookReadingDTO;
import dev.arielalvesdutra.booksreadings.entities.BookReading;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.services.BookReadingService;
import dev.arielalvesdutra.booksreadings.services.UserService;
import io.swagger.annotations.Api;

@RequestMapping("/users")
@RestController
@Api(tags = "User", description = "User Resource")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookReadingService bookReadingService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<RetrieveUserDTO> create(@Valid @RequestBody CreateUserDTO parameterUser, UriComponentsBuilder uriBuilder) {
		
		User createduser = this.userService.create(parameterUser.toUser());
		URI uri = uriBuilder.path("/users/{id}")
				.buildAndExpand(createduser.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new RetrieveUserDTO(createduser));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
	public ResponseEntity<?> deleteById(@PathVariable Long userId) {
		
		this.userService.deleteById(userId);		
		
		return ResponseEntity.ok().build();		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	public ResponseEntity<RetrieveUserDTO> retrieveById(@PathVariable Long userId) {
		
		User user = this.userService.find(userId);
		
		return ResponseEntity.ok().body(new RetrieveUserDTO(user));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{userId}/books-readings")
	public ResponseEntity<RetrieveBookReadingDTO> addBookReading(
			@PathVariable Long userId, 
			@RequestBody CreateBookReadingDTO bookReadDTO,
			UriComponentsBuilder uriBuilder
	) {

		BookReading createdBookReading = this.userService.addBookReading(userId, bookReadDTO.getBookId());
		
		Map<String, Long> pathParams = new HashMap<>();
		pathParams.put("userId", userId);
		pathParams.put("bookReadingId", createdBookReading.getId());
		
		URI uri = uriBuilder.path("/users/{userId}/books-readings/{bookReadingId}")
				.buildAndExpand(pathParams)
				.toUri();
		
		return ResponseEntity.created(uri).body(
				new RetrieveBookReadingDTO(createdBookReading));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}/books-readings/{bookReadingId}")
	public ResponseEntity<?> deleteBookReadingById(
		    @PathVariable Long userId, @PathVariable Long bookReadingId) {

		this.userService.removeBookReading(userId, bookReadingId);

		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}/books-readings")
	public ResponseEntity<Page<RetrieveBookReadingDTO>> listBooksReadingsByUserId(
					@PathVariable Long userId,
					@PageableDefault(sort = "id", page = 0, size = 10) Pageable pagination) {

		User user = this.userService.find(userId);
		
		Page<BookReading> bookReadingPage = 
				this.bookReadingService.findAllByUserId(user.getId(), pagination);

		return ResponseEntity.ok().body(
				RetrieveBookReadingDTO.
				fromBookReadingPageToRetriveBookReadingDTOPage(bookReadingPage));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<RetrieveUserDTO>> list(
			@PageableDefault(sort = "name", page = 0, size = 10) Pageable pagination) {
		
		Page<User> users = this.userService.findAll(pagination);
		
		return ResponseEntity.ok().body(
				RetrieveUserDTO.fromUserPageToRetrieveUserDTOPage(users));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userId}/books-readings/{bookReadingId}")
	public ResponseEntity<RetrieveBookReadingDTO> retrieveBookReadingByBookReadingId(
			@PathVariable Long userId, 
			@PathVariable Long bookReadingId) {
		
		
		BookReading bookReading = this.bookReadingService.findByIdAndUserId(bookReadingId, userId);
		
		return ResponseEntity.ok()
				.body(new RetrieveBookReadingDTO(bookReading));
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{userId}/books-readings/{bookReadingId}")
	public ResponseEntity<RetrieveBookReadingDTO> updateBookReadingStatus(
			@PathVariable Long userId, 
			@PathVariable Long bookReadingId, 
			@RequestBody UpdateBookReadingDTO updateBookReadingDto
	) {

		BookReading bookReading = this.userService.updateBookReadingStatus(
				userId, bookReadingId, updateBookReadingDto.getReadingStatus());
		
		return ResponseEntity.ok(new RetrieveBookReadingDTO(bookReading));
	}
}
