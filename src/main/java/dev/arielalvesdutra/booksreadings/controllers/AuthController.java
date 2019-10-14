package dev.arielalvesdutra.booksreadings.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.arielalvesdutra.booksreadings.config.security.TokenService;
import dev.arielalvesdutra.booksreadings.controllers.dto.LoginFormDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.TokenDTO;

@RequestMapping("auth")
@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> auth(@RequestBody @Valid LoginFormDTO formDto) {
		UsernamePasswordAuthenticationToken loginData = 
				formDto.toUsernamePasswordAuthenticationToken();
		try {
			Authentication authentication = this.authManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);
			
			return ResponseEntity.ok(new TokenDTO(token));
			
		} catch(AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
