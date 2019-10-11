package dev.arielalvesdutra.booksreadings.error_handlers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.arielalvesdutra.booksreadings.error_handlers.dto.ResponseErrorDTO;
import dev.arielalvesdutra.booksreadings.exceptions.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class EntityNotFoundHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResponseErrorDTO> 
			notFoundHanler(HttpServletRequest request, EntityNotFoundException exception) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ResponseErrorDTO error = new ResponseErrorDTO(
				status.name(), 
				exception.getMessage(), 
				status.value(),
				Instant.now(),
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
}
