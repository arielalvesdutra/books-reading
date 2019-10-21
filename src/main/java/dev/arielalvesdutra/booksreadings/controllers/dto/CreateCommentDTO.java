package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.time.OffsetDateTime;

import dev.arielalvesdutra.booksreadings.entities.Comment;
import io.swagger.annotations.ApiModelProperty;

public class CreateCommentDTO {
	
	@ApiModelProperty(required = true)
	private String content;
	
	public CreateCommentDTO() {	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Comment toComment() {
		Comment comment = new Comment(this.getContent(), OffsetDateTime.now());
		
		return comment;
	}
}
