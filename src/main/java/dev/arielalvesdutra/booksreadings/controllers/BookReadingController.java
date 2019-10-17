package dev.arielalvesdutra.booksreadings.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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

import dev.arielalvesdutra.booksreadings.controllers.dto.CreateCommentDTO;
import dev.arielalvesdutra.booksreadings.entities.BookReading;
import dev.arielalvesdutra.booksreadings.entities.Comment;
import dev.arielalvesdutra.booksreadings.services.BookReadingService;

@RequestMapping("/books-readings")
@RestController
public class BookReadingController {
	
	@Autowired
	private BookReadingService bookReadingService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<BookReading>> list(
					@PageableDefault(sort="id", page = 0, size = 10) Pageable pagination) {

		Page<BookReading> booksReadingPage = this.bookReadingService.findAll(pagination);
		
		return  ResponseEntity.ok().body(booksReadingPage);		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{bookReadingId}/comments")
	public ResponseEntity<Page<Comment>> listComments(
			@PathVariable Long bookReadingId,
			@PageableDefault(sort="id", page = 0, size = 10) Pageable pagination) {
		
		Page<Comment> comments = this.bookReadingService.findComments(bookReadingId, pagination);
		
		return ResponseEntity.ok().body(comments);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<BookReading> retrieveById(@PathVariable Long id) {
		
		BookReading booksReadingPage = this.bookReadingService.find(id);
		
		return ResponseEntity.ok().body(booksReadingPage);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{id}/comments")
	public ResponseEntity<Comment> addComment(
			@PathVariable Long id, 
			@RequestBody CreateCommentDTO createCommentDto,
			UriComponentsBuilder uriBuilder) {
	
		Comment createdComment = this.bookReadingService.addComment(id, createCommentDto.toComment());
		
		Map<String, Long> pathParams = new HashMap<>();
		pathParams.put("bookReadingId", id);
		pathParams.put("commentId", createdComment.getId());
		
		URI uri = uriBuilder.path("/books-readings/{bookReadingId}/comments/{commentId}")
				.buildAndExpand(pathParams)
				.toUri();
		
		return ResponseEntity.created(uri).body(createdComment);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{bookReadingId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(	
			@PathVariable Long bookReadingId,
			@PathVariable Long commentId) {
		
		this.bookReadingService.deleteComment(bookReadingId, commentId);
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{bookReadingId}/comments/{commentId}")
	public ResponseEntity<Comment> retrieveCommentById(
			@PathVariable Long bookReadingId,
			@PathVariable Long commentId){
		
		Comment comment = this.bookReadingService.findComment(bookReadingId, commentId);
		
		return ResponseEntity.ok().body(comment);
	}
	
}
