package dev.arielalvesdutra.booksreadings.controllers.dto;

public class TokenDTO {
	
	private String token;
	
	public TokenDTO(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
}
