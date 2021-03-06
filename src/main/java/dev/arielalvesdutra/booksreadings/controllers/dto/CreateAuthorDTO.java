package dev.arielalvesdutra.booksreadings.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dev.arielalvesdutra.booksreadings.entities.Author;
import io.swagger.annotations.ApiModelProperty;


public class CreateAuthorDTO implements Cloneable {
	
	@ApiModelProperty(example = "Martin Fowler", required = true, position = 1)
	@NotEmpty
	@Size(min = 5)
	private String name;
	
	@ApiModelProperty(example = "exemplo@exemplo.com", required = true, position = 2)
	@NotBlank
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	private String email;
	
	public CreateAuthorDTO() {}
	
	public CreateAuthorDTO(@NotEmpty @Size(min = 5) String name,
			@NotBlank @Pattern(regexp = ".+@.+\\.[a-z]+") String email) {
		super();
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
		Author author = new Author(this.name, this.email);
		
		return author;	
	}
	
	@Override
	public CreateAuthorDTO clone() throws CloneNotSupportedException {
		return (CreateAuthorDTO) super.clone();
	}
}