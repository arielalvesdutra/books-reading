package dev.arielalvesdutra.booksreadings.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.repositories.UserRepository;
import dev.arielalvesdutra.booksreadings.services.TokenService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	private UserRepository userRepository;
	
	public TokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
		
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}	

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = this.retrieveToken(request);
		
		boolean isValidToken = this.tokenService.isValidToken(token);
		
		if (isValidToken) {
			this.authenticateUser(token);
		}
		
		filterChain.doFilter(request, response);		
	}
	
	public void authenticateUser(String token) {
		
		Long userId = tokenService.getUserId(token);
		User user = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication =
				 new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (!this.isValidRequestTokenHeaderFormat(token)) {
			return null;
		}
		
		return token.substring(7, token.length());
	}
	
	private Boolean isValidRequestTokenHeaderFormat(String token) {
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return false;
		}
		return true;
	}

}
