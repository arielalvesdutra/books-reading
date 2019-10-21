package dev.arielalvesdutra.booksreadings.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dev.arielalvesdutra.booksreadings.entities.Author;
import io.swagger.annotations.ApiModelProperty;


public class UpdateAuthorDTO {
	
	@ApiModelProperty(example = "Yuval Harari", required = true, position = 1)
	@NotEmpty
	@Size(min = 5)
	private String name;
	
	@ApiModelProperty(example = "exemplo@exemplo.com", required = true, position = 2)
	@NotBlank
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	private String email;
	
	public UpdateAuthorDTO() {}
	
	public UpdateAuthorDTO(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Author toAuthor() {
		Author author = new Author(this.getName(), this.getEmail());
		
		return author;	
	}
}