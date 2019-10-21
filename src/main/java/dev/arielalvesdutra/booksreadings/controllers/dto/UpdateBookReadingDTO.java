package dev.arielalvesdutra.booksreadings.controllers.dto;

import dev.arielalvesdutra.booksreadings.entities.enums.ReadingStatus;
import io.swagger.annotations.ApiModelProperty;

public class UpdateBookReadingDTO {
  
	@ApiModelProperty(required = true)
	private ReadingStatus readingStatus;
	
	public UpdateBookReadingDTO() {}
	
	public UpdateBookReadingDTO(ReadingStatus readingStatus) {
		this.readingStatus = readingStatus;
	}

	public ReadingStatus getReadingStatus() {
		return readingStatus;
	}

	public void setReadingStatus(ReadingStatus readingStatus) {
		this.readingStatus = readingStatus;
	}
	
	@Override
	public String toString() {
		return "[" + this.getReadingStatus() + "]";
	}
}
