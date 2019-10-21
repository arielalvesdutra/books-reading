package dev.arielalvesdutra.booksreadings.controllers.dto;

import io.swagger.annotations.ApiModelProperty;

public class CreateBookReadingDTO {
	
	@ApiModelProperty(required = true)
	private Long bookId;

	public CreateBookReadingDTO() {
	}

	public CreateBookReadingDTO(Long bookId) {
		this.bookId = bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getBookId() {
		return this.bookId;
	}
}
