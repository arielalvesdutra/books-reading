package dev.arielalvesdutra.booksreadings.unit.entities;

import dev.arielalvesdutra.booksreadings.controllers.dto.CreateAuthorDTO;

public class CreateAuthorDTOBuilder {
	
	public CreateAuthorDTO createAuthorDto = new CreateAuthorDTO();
	
	public CreateAuthorDTOBuilder withName(String name) {
		this.createAuthorDto.setName(name);
		return this;
	}
	
	public CreateAuthorDTOBuilder withEmail(String email) {
		this.createAuthorDto.setEmail(email);
		return this;
	}
	
	public CreateAuthorDTO build() {
		try {
			return this.createAuthorDto.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

