package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.io.Serializable;
import java.util.List;

public class UpdateBookAuthorsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Long> authorsIds;
	
	public UpdateBookAuthorsDTO() {}

	public List<Long> getAuthorsIds() {
		return authorsIds;
	}

	public void setAuthorsIds(List<Long> authorsIds) {
		this.authorsIds = authorsIds;
	}
}
