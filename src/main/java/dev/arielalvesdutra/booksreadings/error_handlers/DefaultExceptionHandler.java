package dev.arielalvesdutra.booksreadings.error_handlers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.arielalvesdutra.booksreadings.error_handlers.dto.ResponseErrorDTO;

@ControllerAdvice
public class DefaultExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)	
	public ResponseEntity<ResponseErrorDTO> 
			notFoundHanler(HttpServletRequest request, Exception exception) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ResponseErrorDTO error = new ResponseErrorDTO(
				status.name(), 
				exception.getMessage(), 
				status.value(),
				Instant.now(),
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
}