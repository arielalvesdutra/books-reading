package dev.arielalvesdutra.booksreadings.controllers.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import dev.arielalvesdutra.booksreadings.entities.BookRead;
import dev.arielalvesdutra.booksreadings.entities.User;

public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private Set<BookRead> booksReadings = new HashSet<BookRead>();
	
	public UserDTO(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.id = user.getId();
		this.booksReadings = user.getBooksReadings();
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

	public Set<BookRead> getBooksReadings() {
		return this.booksReadings;
	}
	
	public static Page<UserDTO> fromUserPageToUserDTOPage(Page<User> userPage) {
		return userPage.map(UserDTO::new);
	}
}
