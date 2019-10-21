package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.Comment;
import io.swagger.annotations.ApiModelProperty;

public class RetrieveCommentDTO {

	@ApiModelProperty(example = "1", position = 1)
	private Long id;

	@ApiModelProperty(example = "Iniciando a leitura.", position = 2)
	private String content;
	
	@ApiModelProperty(position = 3)
	private OffsetDateTime timestamp;

	public RetrieveCommentDTO(Comment comment) {
		this.id = comment.getId();
		this.timestamp = comment.getTimestamp();
		this.content = comment.getContent();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public static Page<RetrieveCommentDTO> fromCommentPageToRetrieveCommentDTOPage(
			Page<Comment> commentPage) {
		
		return commentPage.map(RetrieveCommentDTO::new);
	}
}
