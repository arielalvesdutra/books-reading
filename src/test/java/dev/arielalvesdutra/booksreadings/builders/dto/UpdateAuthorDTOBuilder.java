package dev.arielalvesdutra.booksreadings.builders.dto;

import dev.arielalvesdutra.booksreadings.controllers.dto.UpdateAuthorDTO;

public class UpdateAuthorDTOBuilder {
	
	private UpdateAuthorDTO updateAuthorDto = new UpdateAuthorDTO();
	
	public UpdateAuthorDTOBuilder withName(String name) {
		updateAuthorDto.setName(name);
		return this;
	}
	
	public UpdateAuthorDTOBuilder withEmail(String email) {
		updateAuthorDto.setEmail(email);
		return this;
	}
	
	public UpdateAuthorDTO build() {
		return updateAuthorDto;
	}
}
