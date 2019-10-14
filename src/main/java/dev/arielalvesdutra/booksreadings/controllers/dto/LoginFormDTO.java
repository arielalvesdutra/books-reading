package dev.arielalvesdutra.booksreadings.controllers.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginFormDTO {
	
	private String password;
	
	private String email;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(
				this.getEmail(), this.getPassword());
	}
}
