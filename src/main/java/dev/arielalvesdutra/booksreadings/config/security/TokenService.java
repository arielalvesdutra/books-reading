package dev.arielalvesdutra.booksreadings.config.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.arielalvesdutra.booksreadings.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	private final Long expiration = 86400000L;
	
	private final String secret = "1q234256p78s";
	
	public String generateToken(Authentication authentication) {
		
		User loggedUser =  (User) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + this.expiration);
		
		return Jwts.builder()
			.setIssuer("API de Livros e Autores")
			.setSubject(loggedUser.getId().toString())
			.setIssuedAt(today)
			.setExpiration(expirationDate)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
	}
	
	public boolean isValidToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
	
	public Long getUserId(String token) {
		
		Claims claims = 
				Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		
		return Long.parseLong(claims.getSubject());
	}
}
