package dev.arielalvesdutra.booksreadings.controllers.dto;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.User;

public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	
	public UserDTO(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.id = user.getId();
	}	
	
	public String getEmail() {
		return this.email;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static Page<UserDTO> fromUserPageToUserDTOPage(Page<User> userPage) {
		return userPage.map(UserDTO::new);
	}
}
