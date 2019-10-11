package dev.arielalvesdutra.booksreadings.error_handlers.dto;

import java.time.Instant;

public class ResponseErrorDTO {
	
	private final String error;
	private final String message;
	private final String path;
	private final Integer status;
	private final Instant timestamp;
	
	public ResponseErrorDTO(
			String error, String message, Integer status, 
			Instant timestamp, String path) {
		
		this.error = error;
		this.message = message;
		this.path = path;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public String getError() {
		return this.error;
	}

	public String getMessage() {
		return message;
	}
	
	public String getPath() {
		return this.path;
	}

	public Integer getStatus() {
		return status;
	}		
	
	public Instant getTimestamp() {
		return this.timestamp;
	}
}
