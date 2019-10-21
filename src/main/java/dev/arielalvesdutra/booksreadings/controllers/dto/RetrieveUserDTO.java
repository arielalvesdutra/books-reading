package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.BookReading;
import dev.arielalvesdutra.booksreadings.entities.User;
import io.swagger.annotations.ApiModelProperty;

public class RetrieveUserDTO {
	
	@ApiModelProperty(example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(example = "Geralt de Rivia", position = 2)
	private String name;
	
	@ApiModelProperty(example = "email@exemplo.com", position = 3)
	private String email;
	
	@ApiModelProperty(position = 4)
	private Set<BookReading> booksReadings = new HashSet<BookReading>();
	
	public RetrieveUserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.booksReadings = user.getBooksReadings();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<BookReading> getBooksReadings() {
		return booksReadings;
	}

	public void setBooksReadings(Set<BookReading> booksReadings) {
		this.booksReadings = booksReadings;
	}
	
	public static Page<RetrieveUserDTO> fromUserPageToRetrieveUserDTOPage(
			Page<User> userPage) {
		
		return userPage.map(RetrieveUserDTO::new);
	}
}
