package dev.arielalvesdutra.booksreadings.unit.entities;

import dev.arielalvesdutra.booksreadings.entities.User;

public class UserBuilder {
	
	private User user = new User();
	
	public UserBuilder withName(String name) {
		this.user.setName(name);
		return this;
	}
	
	public UserBuilder withEmail(String email) {
		this.user.setEmail(email);
		return this;
	}
	
	public UserBuilder withPassword(String password) {
		this.user.setPassword(password);
		return this;
	}
	
	public UserBuilder withId(Long id) {
		this.user.setId(id);
		return this;
	}
	
	public User build() {
		return this.user;
	}	
}
