package dev.arielalvesdutra.booksreadings.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dev.arielalvesdutra.booksreadings.entities.User;
import io.swagger.annotations.ApiModelProperty;

public class CreateUserDTO {

	@ApiModelProperty(example = "Geralt de Rivia", position = 1, required = true)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "email@exemplo.com", position = 2, required = true)
	@NotBlank
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	private String email;
	
	@ApiModelProperty(example = "password", position = 3, required = true)
	@NotBlank
	@Size(min = 6, max = 60)
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User toUser() {
		User user = new User();
		user.setName(this.getName());
		user.setEmail(this.getEmail());
		user.setPassword(this.getPassword());
		
		return user;
	}
}
