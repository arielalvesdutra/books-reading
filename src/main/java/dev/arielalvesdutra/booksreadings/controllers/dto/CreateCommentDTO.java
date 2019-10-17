package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.time.ZonedDateTime;

import dev.arielalvesdutra.booksreadings.entities.Comment;

public class CreateCommentDTO {
	
	private String content;
	
	public CreateCommentDTO() {	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Comment toComment() {
		Comment comment = new Comment(this.getContent(), ZonedDateTime.now());
		
		return comment;
	}
}
