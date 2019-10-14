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

import dev.arielalvesdutra.booksreadings.controllers.dto.LoginFormDTO;
import dev.arielalvesdutra.booksreadings.controllers.dto.TokenDTO;
import dev.arielalvesdutra.booksreadings.services.TokenService;

@RequestMapping("auth")
@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDTO> auth(@RequestBody @Valid LoginFormDTO formDto) 
			throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken loginData = 
				formDto.toUsernamePasswordAuthenticationToken();
		Authentication authentication = this.authManager.authenticate(loginData);
		String token = tokenService.generateToken(authentication);

		return ResponseEntity.ok(new TokenDTO(token));
	}
}
