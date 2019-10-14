package dev.arielalvesdutra.booksreadings.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.arielalvesdutra.booksreadings.controllers.dto.UserDTO;
import dev.arielalvesdutra.booksreadings.entities.User;
import dev.arielalvesdutra.booksreadings.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserDTO> create(@Valid @RequestBody User user, UriComponentsBuilder uriBuilder) {
		
		User createduser = this.userService.create(user);
		URI uri = uriBuilder.path("/users/{id}")
				.buildAndExpand(createduser.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new UserDTO(createduser));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<UserDTO> list(
			@PageableDefault(sort = "name", page = 0, size = 10) Pageable pagination) {
		
		Page<User> users = this.userService.findAll(pagination);
		
		return UserDTO.fromUserPageToUserDTOPage(users);
	}
}
